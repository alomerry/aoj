#include "logger.h"

#include <stdarg.h>
#include <string>
#include <stdio.h>
#include <iostream>
#include <stdlib.h>


using namespace std;

void write_log(const char *fmt, ...)
{
    va_list ap;
    char buffer[4096];
    sprintf(buffer, "/oj-home/log/log.txt");
    FILE *fp = fopen(buffer, "a+");
    if (fp == NULL)
    {
        fprintf(stderr, "open file error!\n");
        system("pwd");
    }
    va_start(ap, fmt);
    //l =
    vsprintf(buffer, fmt, ap);
    fprintf(fp, "%s\n", buffer);
    // if (DEBUG)
    // printf("%s\n", buffer);
    va_end(ap);
    fclose(fp);
}

/**
 * 日志级别
 * 日志方法
 * 日志内容
 */
void show_log(char level, char *methodName, const char *fmt, ...)
{
    char message[4096];
    va_list ap;
    va_start(ap, fmt);
    vsprintf(message, fmt, ap);

    switch (level)
    {
    case 'd': //debug
        printf("\033[%dm[%s] | [%s] | %s\n", 32, "DEBUG", methodName, message);
        break;
    case 'i': //info
        printf("\033[%dm[%s] | [%s] | %s\n", 34, "INFO", methodName, message);
        break;
    case 'w': //warning
        printf("\033[%dm[%s] | [%s] | %s\n", 33, "WARN", methodName, message);
        break;
    case 'e': //error
        printf("\033[%dm[%s] | [%s] | %s\n", 31, "ERROR", methodName, message);
        break;
    case 'v': //verb
        printf("\033[%dm[%s] | [%s] | %s\n", 37, "VERB", methodName, message);
        break;
    }
    va_end(ap);
}
void printer(int backcolor, int frountcolor, char *str)
{
    printf("\x1b[%d;%dm%s\x1b[%dm\n", backcolor, frountcolor, str);
}
