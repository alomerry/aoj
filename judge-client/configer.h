#ifndef _CONFIGER_H_
#define _CONFIGER_H_

#define configer_buffer_size 1024

bool read_buf(char *buf, const char *key, char *value);

void trim(char *str);

int after_equal(char *str);

void read_int(char *buf, const char *key, int *value);

int execute_cmd(const char *fmt, ...);

bool check_file_type(char *file_name, char *extension);

char *getNowTime();
#endif