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
using namespace std;
static char oj_home[BUFF_SIZE];
int main()
{
    strcpy(oj_home, "/home/mo/Desktop/project/JudgeClient");
    chdir(oj_home); // change the dir

    FILE *fp = NULL;
    char buf[BUFF_SIZE];
    fp = fopen("./etc/judge.conf", "r");
    if (fp != NULL)
    {
        cout << "666" << endl;
        fclose(fp);
    }
    return 0;
}