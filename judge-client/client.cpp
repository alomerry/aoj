#include <stdio.h>
#include <string.h>
#include <wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/resource.h>
#include <sys/ptrace.h>
#include <sys/stat.h>

#include "logger.h"
#include "configer.h"
#include "client.h"

//编译
int compile(int lang, char *work_dir)
{
    int pid;
    char buffer[BUFF_SIZE], out[BUFF_SIZE];
    sprintf(buffer, "%s/code/main.%s", work_dir, lang_txt[lang]);
    sprintf(out, "%s/code/main", work_dir);
    char *compire_type = compire_txt[lang];
    char *compile_cpp[] = {compire_type, buffer, "-o", out, "-w", NULL};

    pid = fork();
    if (pid == 0) //子进程
    {
        struct rlimit LIM;
        LIM.rlim_max = 60;
        LIM.rlim_cur = 60;
        setrlimit(RLIMIT_CPU, &LIM);
        alarm(60);
        LIM.rlim_max = 100 * STD_MB;
        LIM.rlim_cur = 100 * STD_MB;
        setrlimit(RLIMIT_FSIZE, &LIM);
        LIM.rlim_max = STD_MB << 10;
        LIM.rlim_cur = STD_MB << 10;
        setrlimit(RLIMIT_AS, &LIM);

        show_log('v', "client-compile", "编译中.....");
        char err[BUFF_SIZE];
        sprintf(err, "%s/log/ce.txt", work_dir);
        freopen(err, "w", stderr);
        if (execvp(compire_type, compile_cpp) == -1)
        {
            show_log('v', "client-compile", "调用%s编译器错误", compire_type);
            exit(1);
        }
    }
    else //父进程
    {
        int status = 0;
        waitpid(pid, &status, 0);
        if (WIFEXITED(status) != 0)
        {
            char err[BUFF_SIZE];
            sprintf(err, "%s/log/ce.txt", work_dir);
            status = get_file_size(err);
            if (status == 0)
            {
                show_log('v', "client-compile", "编译成功。");
            }
            else
            {
                show_log('v', "client-compile", "编译错误。结束运行。");
                //todo 修改oj flag
                // exit(0);
            }
            return status;
        }
        else
        {
            show_log('v', "client-compile", "编译错误。结束运行。");
            return -1;
        }
    }
}

// 获取制定文件的大小
long get_file_size(const char *filename)
{
    struct stat f_stat;

    if (stat(filename, &f_stat) == -1)
    {
        return 0;
    }
    return (long)f_stat.st_size;
}

/**
 * 创建工作空间
 * 工作空间
 */
void mk_work_dir(char *work_dir)
{
    execute_cmd("/bin/mkdir -p %s", work_dir);
    execute_cmd("/bin/mkdir -p %s/code", work_dir);
    execute_cmd("/bin/mkdir -p %s/log", work_dir);
    execute_cmd("/bin/mkdir -p %s/data", work_dir);
    execute_cmd("/bin/touch  %s/data/user.out", work_dir);
    execute_cmd("/bin/touch  %s/log/log.txt", work_dir);
    // execute_cmd("/bin/ln -s %s %s/", shm_path, oj_home);
    // execute_cmd("/bin/chown judge %s ", shm_path);
    // execute_cmd("chmod 755 %s ", shm_path);
    //sim need a soft link in shm_dir to work correctly
    // sprintf(shm_path, "/dev/shm/hustoj/%s/", oj_home);
    // execute_cmd("/bin/ln -s %s/data %s", oj_home, shm_path);
    show_log('v', "client-mk_work_dir", "创建工作空间OK。");
}

/**
 * 执行编译结果
 * 代码语言
 * 时间限制
 * 内存限制
 * 工作空间
 */
void run_solution(int lang, int time_limit, int memery_limit, char *work_dir)
{
    char buffer[BUFF_SIZE];
    sprintf(buffer, "%s/log/error.txt", work_dir);
    freopen(buffer, "a+", stderr);

    sprintf(buffer, "%s/data/data.in", work_dir);
    freopen(buffer, "r", stdin);

    sprintf(buffer, "%s/data/user.out", work_dir);
    freopen(buffer, "w", stdout);

    //请求父进程追踪
    ptrace(PTRACE_TRACEME, 0, NULL, NULL);

    //设置进程时钟片使用时间
    struct rlimit LIM;
    LIM.rlim_max = time_limit + 1;
    LIM.rlim_cur = LIM.rlim_max;
    setrlimit(RLIMIT_CPU, &LIM);

    //设置进程运行时间
    alarm(0); //清除定时
    alarm(time_limit * 10);

    LIM.rlim_max = STD_MB; //设置文件大小限制
    LIM.rlim_cur = STD_MB;
    setrlimit(RLIMIT_FSIZE, &LIM);

    LIM.rlim_max = STD_MB << 10;
    LIM.rlim_cur = STD_MB << 10;
    setrlimit(RLIMIT_AS, &LIM);

    sprintf(buffer, "%s/code/main", work_dir);
    if (execl(buffer, buffer, (char *)NULL) == -1)
    {
        exit(1);
    }
    exit(0);
}

//读取进程调度信息
int get_proc_status(pid_t pid, const char *mark)
{
    FILE *file;
    char fileName[BUFF_SIZE], buf[BUFF_SIZE];

    sprintf(fileName, "/proc/%d/status", pid);
    file = fopen(fileName, "r");
    int len = strlen(mark), res = 0;
    while (file && fgets(buf, BUFF_SIZE - 1, file))
    {
        if (strncmp(mark, buf, len) == 0)
        {
            // printf("%s", buf);
            sscanf(buf + len, "%d", &res);
            // cout << (res << 10) << endl;
        }
    }
    if (file)
        fclose(file);
    return res;
}

/**
 * 监控运行结果
 * pid
 * judge_result
 * user_time
 * 工作空间
 */
void watch_solution(pid_t pidApp, int &Judge_Result, int &usedtime, char *work_dir)
{
    int status, memory_usage = 0;
    struct rusage ruse;
    while (true)
    {
        wait4(pidApp, &status, 0, &ruse);
        memory_usage = get_proc_status(pidApp, "VmPeak:");
        if (memory_usage > 100000)
        {
            Judge_Result = OJ_ML;
            show_log('w', "watch_solution", "内存超限(%d)", memory_usage);
            ptrace(PTRACE_KILL, pidApp, NULL, NULL);
            break;
        }
        if (WIFEXITED(status))
        {
            show_log('i', "watch_solution", "子进程正常运行结束");
            // cout << "\t" << endl;
            break;
        }
        char buffer[BUFF_SIZE];
        sprintf(buffer, "%s/log/error.txt", work_dir);
        if (get_file_size(buffer))
        {
            Judge_Result = OJ_RE;
            //addreinfo(solution_id);
            ptrace(PTRACE_KILL, pidApp, NULL, NULL);
            break;
        }
        int exitcode = WEXITSTATUS(status);
        if (!(exitcode == 0 || exitcode == 0x05 || exitcode == 17))
        {
            if (Judge_Result == OJ_AC)
            {
                switch (exitcode)
                {
                case SIGCHLD:
                case SIGALRM:
                    alarm(0);
                case SIGKILL:
                case SIGXCPU:
                    Judge_Result = OJ_TL;
                    show_log('i', "watch_solution", "时间超限");
                    break;
                case SIGXFSZ:
                    Judge_Result = OJ_OL;
                    break;
                default:
                    Judge_Result = OJ_RE;
                }
                // print_runtimeerror(strsignal(exitcode));
            }
            ptrace(PTRACE_KILL, pidApp, NULL, NULL);
            break;
        }
        if (WIFSIGNALED(status))
        {
            int sig = WTERMSIG(status);
            if (Judge_Result == OJ_AC)
            {
                switch (sig)
                {
                case SIGCHLD:
                case SIGALRM:
                    alarm(0);
                case SIGKILL:
                case SIGXCPU:
                    show_log('i', "watch_solution", "时间超限");
                    Judge_Result = OJ_TL;
                    break;
                case SIGXFSZ:
                    Judge_Result = OJ_OL;
                    break;

                default:
                    Judge_Result = OJ_RE;
                }
                // print_runtimeerror(strsignal(sig));
            }
            break;
        }
        ptrace(PTRACE_SYSCALL, pidApp, NULL, NULL);
    }
    usedtime += (ruse.ru_utime.tv_sec * 1000 + ruse.ru_utime.tv_usec / 1000);
    usedtime += (ruse.ru_stime.tv_sec * 1000 + ruse.ru_stime.tv_usec / 1000);
}

/**
 * 准备测试用例
 * 题目Id
 * 测试用例 
 * 测试用例结果
 * 工作空间
 * oj-home
 */
void prepare_file_to_run(int problem_id, char *input_file, char *input_result, char *work_dir, char *oj_home)
{
    show_log('v', "prepare_file_to_run", "正在准备文件......\n\t输入文件(%s)  输出文件(%s)", input_file, input_result);
    char fullname[BUFF_SIZE];
    sprintf(fullname, "%s/problem_cases/%d/%s", oj_home, problem_id, input_file); //ojhome中的文件
    execute_cmd("/bin/cp -f %s %s/data/data.in", fullname, work_dir);             //workdir

    sprintf(fullname, "%s/problem_cases/%d/%s", oj_home, problem_id, input_result); //ojhome中的文件
    execute_cmd("/bin/cp  -f %s %s/data/data.out", fullname, work_dir);             //workdir
}

void compare(const char *correct_result, const char *user_file, int &Judge_Result)
{
    FILE *res = fopen(correct_result, "r"), *user = fopen(user_file, "r");
    char *res_buf = new char[BUFF_SIZE * 10], *user_buf = new char[BUFF_SIZE * 10];

    while (Judge_Result == OJ_AC && fgets(res_buf, BUFF_SIZE, res) != NULL)
    {

        // printf("[%s", res_buf);
        if (fgets(user_buf, BUFF_SIZE, user) != NULL)
        {
            // printf("{%s", user_buf);
            //判断两个缓冲区是否相同
            for (int i = 0; user_buf[i] != '\0' && i <= BUFF_SIZE - 1; i++)
            {
                // printf("\t\t user[%d] res[%d]\t", user_buf[i], res_buf[i]);
                if (user_buf[i] != res_buf[i])
                {
                    if ((res_buf[i] == 10 || res_buf[i] == 13) && user_buf[i] == 10 || user_buf[i] == 13)
                    {
                        continue;
                    }
                    Judge_Result = OJ_WA;
                    {
                        show_log('v', "compare", "用户答案与标准答案不一致，答案错误");
                    }
                    break;
                }
            }
        }
        else
        {
            show_log('v', "compare", "用户答案与标准答案不一致，答案错误");
            Judge_Result = OJ_WA;
            break;
        }
    }
    show_log('v', "compare", "Judge_Result[%d]", Judge_Result);

    fclose(user);
    fclose(res);
    delete[] res_buf;
    delete[] user_buf;
}

/**
 * 判题
 * judge_result
 * 工作空间
 */
void judge_solution(int &Judge_Result, char *work_dir)
{
    char buf1[BUFF_SIZE], buf2[BUFF_SIZE];
    sprintf(buf1, "%s/data/data.out", work_dir);
    sprintf(buf2, "%s/data/user.out", work_dir);
    compare(buf1, buf2, Judge_Result);
    if (Judge_Result != OJ_AC)
    {
        show_log('i', "judge_solution", "答案错误");
    }
    else
    {
        show_log('i', "judge_solution", "答案正确");
    }
}