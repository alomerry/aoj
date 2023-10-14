#include <string.h>
#include <ctype.h>
#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <time.h>

#include "configer.h"
#include "logger.h"

/**
 * 清除字符串前后的空白
 * str 待操作字符串
 */
void trim(char *c)
{
    char buf[configer_buffer_size];
    char *start, *end;
    strcpy(buf, c);
    start = buf;
    while (isspace(*start))
        start++;
    end = start;
    while (!isspace(*end))
        end++;
    *end = '\0';
    strcpy(c, start);
}
/**
 * 定位到 ‘=’ 后面
 * str 待定位字符串 
 */
int after_equal(char *c)
{
    int i = 0;
    for (; c[i] != '\0' && c[i] != '='; i++)
        ;
    return ++i;
}
/**
 * 读取string
 */
bool read_buf(char *buf, const char *key, char *value)
{
    if (strncmp(buf, key, strlen(key)) == 0)
    {
        strcpy(value, buf + after_equal(buf));
        trim(value);
        return 1;
    }
    return 0;
}
/**
 * 读取config中指定key的(Int)value
 */
void read_int(char *buf, const char *key, int *value)
{
    char buf2[configer_buffer_size];
    if (read_buf(buf, key, buf2))
    {
        sscanf(buf2, "%d", value);
    }
}

/**
 * 执行cmd命令
 */
int execute_cmd(const char *fmt, ...)
{
    char cmd[4090];
    int ret = 0;
    va_list ap;

    va_start(ap, fmt);
    vsprintf(cmd, fmt, ap);
    show_log('v', "execute_cmd", "执行了cmd[%s]", cmd);
    ret = system(cmd);

    va_end(ap);
    return ret;
}

void print_runtimeerror(char *err)
{
    FILE *ferr = fopen("log/error.txt", "a+");
    fprintf(ferr, "Runtime Error:%s\n", err);
    fclose(ferr);
}

/**
 * 获取文件后缀名
 * filename 文件名
 * extension 后缀名
 */
bool check_file_type(char *file_name, char *extension)
{
    show_log('v', "check_file_type", "#-判断文件后缀名-%s(%s) = %d", strrchr(file_name, '.'), extension, strcasecmp(extension, strrchr(file_name, '.')) == 0);
    return strcasecmp(extension, strrchr(file_name, '.')) == 0;
}

char *getNowTime()
{
    char buf[512];
    time_t t;
    struct tm *lt;
    time(&t);                                                                                                            //获取Unix时间戳。
    lt = localtime(&t);                                                                                                  //转为时间结构。
    sprintf(buf, "%d-%d-%d %d:%d:%d", lt->tm_year + 1900, lt->tm_mon, lt->tm_mday, lt->tm_hour, lt->tm_min, lt->tm_sec); //输出结果
    show_log('i', "getNowTime", "当前时间[%s]", buf);
    return buf;
}