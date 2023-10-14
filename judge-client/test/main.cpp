#include <iostream>
#include <algorithm>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
using namespace std;
extern int errno;
void printf_wrongMessage(int status)
{
  printf("错误代码=%d\n", status);
  char *mesg = strerror(status);
  printf("错误原因:%s\n", mesg);
}
void printf_wrongMessage()
{
  printf("错误代码=%d\n", errno);
  char *mesg = strerror(errno);
  printf("错误原因:%s\n", mesg);
}
void run_client()
{
  if (execl("/home/mo/Desktop/project/JudgeClient/client", "asdfasfasf", "1", NULL) == -1)
  {
    cout << "调用失败" << endl;
    printf_wrongMessage();
  }
}
void run_test()
{
  if (execl("/home/mo/Desktop/project/JudgeClient/test/alarm", "/home/mo/Desktop/project/JudgeClient/test/alarm", "1", "2", "3", (char *)NULL) == -1)
  {
    cout << "调用失败" << endl;
    printf_wrongMessage();
  }
}
int main(int argc, char **argv)
{

  int id = 0, tmp = -1;
  id = fork();
  if (id == 0)
  {
    tmp = atoi(argv[1]);
    if (tmp == 1)
    {
      run_test();
    }
    else
    {
      run_client();
    }
    cout << "children" << endl;
  }
  return 0;
}