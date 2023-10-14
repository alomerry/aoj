#include <unistd.h>
#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
void write_log(char *arg)
{
    char buffer[4096];
    sprintf(buffer, "./log/log.txt");
    FILE *fp = fopen(buffer, "a+");
    if (fp == NULL)
    {
        fprintf(stderr, "openfile error!\n");
        system("pwd");
    }
    sprintf(buffer, arg);
    fprintf(fp, "%s\n", buffer);
    fclose(fp);
}
void init_param(int argc, char **argv)
{
    for (int i = 0; i < argc; i++)
    {
        // write_log(argv[i]);
        printf("%s\n", argv[i]);
    }
}
int main(int argc, char **argv)
{
    init_param(argc, argv);
    printf("alarm:hello！\n");
    return 666;
}