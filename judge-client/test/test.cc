#include <iostream>
#include <stdio.h>
#include <dirent.h>
#include <string.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/resource.h>
#include <sys/types.h>
#include <sys/user.h>
#include <sys/ptrace.h>
#include <mysql/mysql.h>
#include <dirent.h>
#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <string>
#include <string.h>
#define BUFF_SIZE 512
using namespace std;
//执行cmd命令
int execute_cmd(const char *fmt, ...)
{
    char cmd[BUFF_SIZE];
    int ret = 0;
    va_list ap;

    va_start(ap, fmt);
    vsprintf(cmd, fmt, ap);

    ret = system(cmd);

    va_end(ap);
    return ret;
}
int main()
{

    char *res_buf = new char[4096], *fullname;
    sprintf(fullname, "./input/%s", "1.out");                //from
    execute_cmd("/bin/cp  -f %s ./data/data.out", fullname); //to

    FILE *res = fopen("./data/data.out", "r");
    while (fgets(res_buf, 4096, res) != NULL)
    {
        printf("%s", res_buf);
    }
    fclose(res);

    // open DIRs
    // DIR *dp;
    // dirent *dirp;
    // char tmp[4096] = {'\0'};
    // if ((dp = opendir("./input")) == NULL)
    // {
    //     printf("文件夹打开失败\n");
    // }
    // for (; (dirp = readdir(dp)) != NULL;)
    // {
    //     strcpy(tmp, dirp->d_name);
    //     strtok(tmp, ".");
    //     printf("%s\t", strcat(tmp,".out"));
    //     // printf("%s\n", dirp->d_name);
    // }
    return 666;
}