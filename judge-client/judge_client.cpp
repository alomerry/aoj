
#define DEBUG 1
#define BUILD 0

#include <sys/types.h>
#include <sys/types.h>
#include <sys/user.h>
#include <mysql/mysql.h>
#include <dirent.h>
#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <string>
#include <string.h>
#include <wait.h>

#include "configer.h"
#include "client.h"
#include "logger.h"
#include "cdbc.h"
using namespace std;

MYSQL *conn;
static int Mode = 0;
static char host_name[BUFF_SIZE];
static char user_name[BUFF_SIZE];
static char password[BUFF_SIZE];
static char db_name[BUFF_SIZE];
static char oj_home[BUFF_SIZE];
static int port_number;
int Judge_Result = OJ_AC;

static char work_dir[BUFF_SIZE];

/**
 * 初始化mysql配置
 */
void init_mysql_conf()
{

    show_log('i', "client-init_mysql_conf", "初始化数据库中...");
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
            read_int(buf, "OJ_PORT_NUMBER", &port_number);
        }
        show_log('v', "client-init_mysql_conf", "host_name(%s),user_name(%s),db_name(%s)", host_name, user_name, db_name);
        fclose(fp);
    }

    show_log('i', "client-init_mysql_conf", "初始化数据库中完毕。");
}

/**
 * 初始化参数
 * argv[0]: judge_client
 * argv[1]: solution_id
 * argv[2]: Mode(DEBUG/BUILD)
 */
void init_parameters(int argc, char **argv, char *&solution_id)
{
    show_log('v', "client-init_parameters", "初始化参数中....");
    for (int i = 0; i < argc; i++)
    {
        write_log("arg[%d]:{%s}", i + 1, argv[i]);
        show_log('v', "\tclient-init_parameters", "arg[%d]:{%s}", i + 1, argv[i]);
    }

    if (argc < 3)
    {
        show_log('v', "client-init_parameters", "参数错误，程序退出！");
        exit(1);
    }
    solution_id = argv[1];
    Mode = atoi(argv[2]);
    show_log('v', "client-init_parameters", "参数初始化完毕。");
}

int main(int argc, char **argv)
{
    int problem_id = 0,
        usedtime = 0,
        usedmemory = 0,
        user_id = 0,
        lang = 0;

    char *solution_id;

    char buffer[BUFF_SIZE];

    strcpy(oj_home, "/oj-home");
    // chdir(oj_home);

    DIR *dp;
    dirent *dirp;

    execute_cmd("/bin/mkdir -p /oj-home/log/");
    execute_cmd("/usr/bin/touch  /oj-home/log/log.txt");

    init_parameters(argc, argv, solution_id);

    init_mysql_conf();

    if (!init_mysql_conn(conn, host_name, user_name, password, db_name, port_number))
    {
        show_log('e', "client-main", "数据库连接失败!");
        exit(0);
    }

    show_log('v', "client-main", "数据库连接成功!");
    sprintf(work_dir, "%s/judge/%s", oj_home, solution_id);

    mk_work_dir(work_dir);

    // chdir(work_dir);

    get_solution_info_mysql(conn, solution_id, problem_id, user_id, lang);

    get_code_mysql(conn, solution_id, lang, work_dir);

    int compile_flag = compile(lang, work_dir);
    if (compile_flag != 0)
    {
        show_log('e', "client-main", "编译错误,结束本次评判，更新数据库！");
        Judge_Result = OJ_CE;
        add_ce_info(solution_id, conn, work_dir);
        update_solution_info(conn, solution_id, OJ_CE, 0, 0);
        update_user_submition(conn, user_id, false);
        update_problem_submition(conn, problem_id, false);
        show_log('e', "client-main", "数据库更新完毕，结束子进程！");
        return 0;
    }
    else
    {
        update_solution_info(conn, solution_id, OJ_JI, 0, 0);
    }
    /*
        是否可运行
        pid_t pidApp = fork();
        if (pidApp == 0) //子进程
        {
            run_solution(lang, 100, 100);
        }
        else
        {
            printf("父进程:\n\t开始检查子进程运行之后的结果(子进程Id:%d)\n", (int)pidApp);
            watch_solution(pidApp, Judge_Result, usedtime);
        }
    */
    sprintf(buffer, "%s/problem_cases/%d", oj_home, problem_id);
    //读取目录文件失败则判题子程序退出，-1
    if ((dp = opendir(buffer)) == NULL)
    {
        show_log('v', "client-main", "不存在测试文件目录!");
        mysql_close(conn);
        exit(-1);
    }

    show_log('v', "client-main", "开始轮训样例.....Judge_Result [%s]", Judge_Result == OJ_AC ? "AC" : "WA");
    for (; (Judge_Result == OJ_AC) && (dirp = readdir(dp)) != NULL;)
    {
        if (!check_file_type(dirp->d_name, ".in") && !check_file_type(dirp->d_name, ".out"))
        {
            //资源有误
            show_log('v', "client-main", "读取文件:%s,非测试用例", dirp->d_name);
            continue;
        }
        else
        {
            /*
            1.拷贝测试用例
            2.生成输出文件
            */
            /*
            3.子进程运行题目
            4.父进程监视是否可完整运行
            5.
            */
            if (check_file_type(dirp->d_name, ".in") == 1)
            {
                char tmp[BUFF_SIZE] = {'\0'};
                strcpy(tmp, dirp->d_name);
                strtok(tmp, ".");
                prepare_file_to_run(problem_id, dirp->d_name, strcat(tmp, ".out"), work_dir, oj_home);

                pid_t pidApp = fork();
                if (pidApp == 0)
                {
                    run_solution(lang, 100, 100, work_dir);
                }
                else
                {
                    watch_solution(pidApp, Judge_Result, usedtime, work_dir);
                    judge_solution(Judge_Result, work_dir);
                }
            }
        }
    }

    show_log('v', "client-main", "整个程序结束，最终结果 [%s]", Judge_Result == OJ_AC ? "正确" : "失败");
    show_log('v', "client-main", "用户使用时间[%d],消耗内存[%d]", usedtime, usedmemory);
    update_solution_info(conn, solution_id, Judge_Result, Judge_Result == OJ_AC ? usedtime : 0, Judge_Result == OJ_AC ? usedmemory : 0);
    update_user_submition(conn, user_id, Judge_Result == OJ_AC);
    update_problem_submition(conn, problem_id, Judge_Result == OJ_AC);

    mysql_close(conn);
    show_log('v', "client-main", "|************************** client结束 ***********************|");
    exit(0);
}
