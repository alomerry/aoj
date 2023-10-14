#ifndef _CDBC_H_
#define _CDBC_H_

#define BUFF_SIZE 512
#define STD_MB 1048576

static const char lang_txt_cdbc[4][8] = {"java", "c", "cpp", "py"};

int init_mysql_conn(MYSQL *&conn, char *host_name, char *user_name, char *password, char *db_name, int port_number);

void get_code_mysql(MYSQL *&conn, char *solution_id, int lang, char *work_dir);

void add_ce_info(char *solution_id, MYSQL *&conn, char *work_dir);

void update_solution_info(MYSQL *&conn, char *solution_id, int result, int time, int memory);

void update_user_submition(MYSQL *&conn, int user_id, bool successed_flag);

void update_problem_submition(MYSQL *&conn, int problem_id, bool successed_flag);

void get_solution_info_mysql(MYSQL *&conn, char *solution_id, int &problem_id, int &user_id, int &lang);
#endif