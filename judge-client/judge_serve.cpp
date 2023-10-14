#define BUFF_SIZE 512
#define DEBUG 1
#include <mysql/mysql.h>
#include <sys/stat.h>
#include <iostream>
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <wait.h>

#include "configer.h"
#include "logger.h"
using namespace std;

//MySQL连接
MYSQL *conn;
//调试/运行 模式
static int Mode = 0;
static bool STOP = false;
//数据库连接名称
static char host_name[BUFF_SIZE];
//数据库用户名
static char user_name[BUFF_SIZE];
//数据库密码
static char password[BUFF_SIZE];
//数据库名称
static char db_name[BUFF_SIZE];
//运行时目录
static char oj_home[BUFF_SIZE];
static char lang_set[BUFF_SIZE];
//数据库连接端口
static int port_number;
//轮训数据库间隔时间
static int sleep_time;
//最多判题进程数
static int max_running;
void call_for_exit(int s)
{
    STOP = true;
    printf("Stopping judged...\n");
}

/**
 * 初始化数据库
 */
void init_mysql_conf()
{
    show_log('i', "serve-init_mysql_conf", "初始化数据库中...");
    FILE *fp = NULL;
    char buf[BUFF_SIZE];
    host_name[0] = 0;
    user_name[0] = 0;
    password[0] = 0;
    db_name[0] = 0;
    port_number = 3306;
    sprintf(buf, "%s/etc/judge.conf", oj_home);
    fp = fopen(buf, "r");
    if (fp != NULL)
    {
        while (fgets(buf, BUFF_SIZE - 1, fp))
        {
            read_buf(buf, "OJ_HOST_NAME", host_name);
            read_buf(buf, "OJ_USER_NAME", user_name);
            read_buf(buf, "OJ_PASSWORD", password);
            read_buf(buf, "OJ_DB_NAME", db_name);
            read_buf(buf, "OJ_LANG_SET", lang_set);
            read_int(buf, "OJ_PORT_NUMBER", &port_number);
            read_int(buf, "OJ_SLEEP_TIME", &sleep_time);
            read_int(buf, "OJ_RUNNING", &max_running);
        }
        show_log('v', "serve-init_mysql_conf", "host_name(%s),user_name(%s),db_name(%s)", host_name, user_name, db_name);
        fclose(fp);
    }
    show_log('i', "serve-init_mysql_conf", "初始化数据库中完毕。");
}
//初始化mysql连接
int init_mysql_conn()
{
    conn = mysql_init(NULL);
    const char timeout = 30;
    //配置连接时间
    mysql_options(conn, MYSQL_OPT_CONNECT_TIMEOUT, &timeout);
    show_log('i', "serve-init_mysql_conn", "初始化数据库连接中......");

    if (!mysql_real_connect(conn, host_name, user_name, password, db_name, port_number, NULL, 0))
    {
        show_log('i', "serve-init_mysql_conn", "初始化失败。");
        return 0;
    }
    show_log('i', "serve-init_mysql_conn", "初始化成功。");
    return 1;
}
int daemon_init(void)
{
    pid_t pid;
    if ((pid = fork()) < 0)
        return (-1);
    else if (pid != 0)
        exit(0); /* parent exit */

    /* child continues */
    setsid();       /* become session leader */
    chdir(oj_home); /* change working directory */
    umask(0);       /* clear file mode creation mask */
    close(0);       /* close stdin */
    close(1);       /* close stdout */
    close(2);       /* close stderr */
    return (0);
}

int lockfile(int fd)
{
    struct flock fl;
    fl.l_type = F_WRLCK;
    fl.l_start = 0;
    fl.l_whence = SEEK_SET;
    fl.l_len = 0;
    return (fcntl(fd, F_SETLK, &fl));
}
int already_running()
{
    int fd;
    char buf[16];
    fd = open("", O_RDWR | O_CREAT, (S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH));
    if (fd < 0)
    {
        strerror(errno);
        exit(1);
    }
    if (lockfile(fd) < 0)
    {
        if (errno == EACCES || errno == EAGAIN)
        {
            close(fd);
            return 1;
        }
        exit(1);
    }
    ftruncate(fd, 0);
    sprintf(buf, "%d", getpid());
    write(fd, buf, strlen(buf) + 1);
    return (0);
}
int get_unjudged_solutions(string *solutions)
{
    MYSQL_RES *res;
    MYSQL_ROW row;

    char sql[BUFF_SIZE];

    sprintf(sql, "select solution_id from solution where language in (0,1,2,3) and result = 0 limit %d", max_running * 2);
    show_log('v', "serve-get_unjudged_solutions", "查询未评判的提交，查询代码[%s]", sql);

    mysql_real_query(conn, sql, strlen(sql));
    res = mysql_store_result(conn);
    if (mysql_num_rows(res) == 0)
    {
        show_log('v', "serve-get_unjudged_solutions", "当前不存在未评判的提交");
        int i = 0;
        while (i <= max_running * 2) //将提交Id数组设置为0
            solutions[i++] = "0";
        return 0;
    }
    show_log('v', "serve-get_unjudged_solutions", "查询到%d条未评判的提交", mysql_num_rows(res));

    int i = 0, ret = 0;
    while ((row = mysql_fetch_row(res)) != NULL)
    {
        solutions[i++] = row[0];
        show_log('v', "serve-get_unjudged_solutions", "\tSolutionId[%s]", row[0]);
    }
    ret = i;
    while (i <= max_running * 2)
        solutions[i++] = "0";
    mysql_free_result(res);
    return ret;
}
void run_client(string solution)
{
    if (execl("./judge_client", "./judge_client", solution.c_str(), "1", (char *)NULL) == -1)
    {
        show_log('v', "serve-run_client", "\tSolutionId[%s]", "调用失败!");
    }
}
int work()
{
    static int ret_cnt = 0; //统计 已经 完成评测次数
    int i = 0;
    static pid_t ID[100];                  //short类型的宏定义，进程表中的索引项，进程号；保存正在执行的子进程pid
    static int working_cnt = 0;            //统计 现用 judge_client进程数量
    string solution_id;                    //solution_id，测试运行编号
    string solutions[max_running * 2 + 1]; //max_running 从judge.conf获取，一般为4，这里设置为工作目录：9
    pid_t tmp_pid = 0;

    if (!get_unjudged_solutions(solutions)) //如果读取失败或者要评测题目数量为0，jobs[]被置为：0，0，0，...0；默认9位
        ret_cnt = 0;

    //修改待评测Id jobs[]被置为：1001，1002，0，...0；默认9位
    for (int j = 0; solutions[j].compare("0") != 0; j++)
    {
        solution_id = solutions[j];
        if (working_cnt >= max_running)
        {
            tmp_pid = waitpid(-1, NULL, 0);
            --working_cnt;
            ++ret_cnt;
            for (i = 0; i < max_running; i++) // get the client id
                if (ID[i] == tmp_pid)
                    break; // got the client id
            ID[i] = 0;
        }
        else
        {
            for (i = 0; i < max_running; i++) // find the client id
                if (ID[i] == 0)
                    break; // got the client id
        }
        ID[i] = fork();
        if (ID[i] == 0)
        {
            show_log('v', "serve-work", "子进程启动");
            run_client(solution_id);
            exit(0);
        }
        else
        {
        }
    }

    while ((tmp_pid = waitpid(-1, NULL, WNOHANG)) > 0)
    {
        working_cnt--;
        ret_cnt++;
        for (i = 0; i < max_running; i++) // get the client id
            if (ID[i] == tmp_pid)
                break; // got the client id
        ID[i] = 0;
        show_log('v', "serve-work", "tmp_pid = %d", tmp_pid);
    }

    return ret_cnt;
}
int main(int argc, char **argv)
{

    strcpy(oj_home, "/oj-home");
    // chdir(oj_home);

    // if (!DEBUG)
    //     daemon_init(); //创建一个daemon守护进程
    // if (strcmp(oj_home, "/home/judge") == 0 && already_running())
    // {
    //     printf("%s\n", "This daemon program is already running!\n");
    //     return 1;
    // }
    //	struct timespec final_sleep;
    //	final_sleep.tv_sec=0;
    //	final_sleep.tv_nsec=500000000;

    init_mysql_conf(); // set the database info
    // signal(SIGQUIT, call_for_exit);
    // signal(SIGKILL, call_for_exit);
    // signal(SIGTERM, call_for_exit);
    int j = 1;
    while (1)
    { // start to run
        while (j && init_mysql_conn())
        {

            printf("%s%d%s\n", "//\0");
            show_log('d', "serve-main", "|************************** work 工作前 result [%d] ***********************|", j);
            j = work(); //如果读取失败或者没有要评测的数据，那么返回0，否则利用那么有限的几个进程来评测无限的任务量
            show_log('d', "serve-main", "|************************** work 工作后 result [%d] ***********************|", j);
        }
        show_log('d', "serve-main", "|************************** 本次工作结束，休息[%d]秒钟 ***********************|", sleep_time % 1000 * 3);
        sleep(sleep_time * 3);
        j = 1;
    }
    show_log('w', "serve-main", "|************************** serve结束 ***********************|");
    return 0;
}