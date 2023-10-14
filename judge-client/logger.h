#ifndef _LOGGER_H_
#define _LOGGER_H_

void write_log(const char *fmt, ...);
void show_log(char level, char *methodName, const char *fmt, ...);

#endif