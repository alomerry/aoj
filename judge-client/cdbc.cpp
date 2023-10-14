#include <mysql/mysql.h>
#include <string.h>
#include <stdio.h>

#include "logger.h"
#include "cdbc.h"
#include "configer.h"

/**
 * 读取源码生成main.*
 * 数据库连接指针
 * 提交Id
 * 代码语言
 * 工作空间
 */
void get_code_mysql(MYSQL *&conn, char *solution_id, int lang, char *work_dir)
{
    MYSQL_RES *res;
    MYSQL_ROW row;

    char sql[BUFF_SIZE], code_path[BUFF_SIZE];

    sprintf(sql, "select source from source_code where solution_id='%s'", solution_id);

    show_log('v', "client-get_code_mysql", "查询[source]表的用户源代码，查询代码[%s]", sql);

    mysql_real_query(conn, sql, strlen(sql));
    res = mysql_store_result(conn);
    row = mysql_fetch_row(res);
    if (mysql_num_rows(res) == 0)
    {
        show_log('v', "client-get_code_mysql", "查询完毕，source不存在");
        exit(1);
    }

    sprintf(code_path, "%s/code/main.%s", work_dir, lang_txt_cdbc[lang]);

    FILE *fp_code = fopen(code_path, "w");
    fprintf(fp_code, "%s", row[0]);

    mysql_free_result(res);

    show_log('v', "client-get_code_mysql", "source代码已查询到，已保存成代码即将编译。");
    fclose(fp_code);
}

/**
 * 初始化mysql连接
 * 数据库连接指针
 * 数据库连接地址
 * 数据库用户名
 * 数据库密码
 * 数据库名称
 * 数据库端口
 */
int init_mysql_conn(MYSQL *&conn, char *host_name, char *user_name, char *password, char *db_name, int port_number)
{
    conn = mysql_init(NULL);
    const char timeout = 30;
    //配置连接时间
    mysql_options(conn, MYSQL_OPT_CONNECT_TIMEOUT, &timeout);

    show_log('i', "client-init_mysql_conn", "初始化数据库连接中......");

    if (!mysql_real_connect(conn, host_name, user_name, password, db_name, port_number, NULL, 0))
    {
        show_log('i', "client-init_mysql_conn", "初始化失败。");
        return 0;
    }
    show_log('i', "client-init_mysql_conn", "初始化成功。");
    return 1;
}

/**
 * 上传编译错误信息
 * 提交Id
 * 数据库连接指针
 * 工作空间
 */
void add_ce_info(char *solution_id, MYSQL *&conn, char *work_dir)
{
    char sql[BUFF_SIZE], buf[BUFF_SIZE], buffer[BUFF_SIZE], *error = NULL;
    char result[BUFF_SIZE * 10];

    snprintf(sql, sizeof(sql), "delete from compile_info where solution_id='%s'", solution_id);
    sprintf(buffer, "%s/log/ce.txt", work_dir);
    show_log('v', "client-add_ce_info", "删除提交Id的其他编译错误信息，即将添加新的编译信息,sql代码[%s]", sql);

    if (mysql_real_query(conn, sql, strlen(sql)))
    {
        show_log('e', "client-add_ce_info", "删除失败，错误原因[%s]", mysql_error(conn));
    }

    FILE *fp = fopen(buffer, "r");
    while (fgets(buf, BUFF_SIZE, fp) != NULL)
    {
        strcat(result, buf);
    }
    snprintf(sql, sizeof(sql), "insert into compile_info (solution_id,error) values ('%s','%s')", solution_id, result);
    show_log('v', "client-add_ce_info", "编译源代码发生错误，添加[编译错误]信息,插入[compile_info]表,sql代码[%s]", sql);

    if (mysql_real_query(conn, sql, strlen(sql)))
    {
        show_log('e', "client-add_ce_info", "插入失败，错误原因[%s]", mysql_error(conn));
    }

    fclose(fp);
}

/**
 * 更新solution信息
 * 数据库连接指针
 * 提交Id
 * 结果
 * 代码用时
 * 代码消耗内存
 */
void update_solution_info(MYSQL *&conn, char *solution_id, int result, int time, int memory)
{
    char sql[BUFF_SIZE];
    sprintf(sql, "update solution set result=%d,time=%d,memory=%d,judgetime=%s where solution_id='%s'", result, time, memory, getNowTime(), solution_id);
    show_log('v', "client-update_solution_info", "修改[solution]表数据,sql代码[%s]", sql);
    if (mysql_real_query(conn, sql, strlen(sql)))
    {
        show_log('e', "client-update_solution_info", "更新失败，错误原因[%s]", mysql_error(conn));
    }
}

/**
 * 更新用户提交数
 * 数据库连接指针
 * 用户Id
 * 是否成功完成题目
 */
void update_user_submition(MYSQL *&conn, int user_id, bool successed_flag)
{
    char sql[BUFF_SIZE];

    if (successed_flag)
    {
        sprintf(sql, "update users set submit=submit+1,solved=solved+1 where user_id = %d", user_id);
        show_log('v', "client-update_user_submition", "更新[users]表数据,sql代码[%s]", sql);

        if (mysql_real_query(conn, sql, strlen(sql)))
        {
            show_log('e', "client-update_user_submition", "更新失败，错误原因[%s]", mysql_error(conn));
        }
    }
    else
    {
        sprintf(sql, "update users set submit=submit+1 where user_id = %d", user_id);
        show_log('v', "client-update_user_submition", "更新[users]表数据,sql代码[%s]", sql);

        if (mysql_real_query(conn, sql, strlen(sql)))
        {
            show_log('e', "client-update_user_submition", "更新失败，错误原因[%s]", mysql_error(conn));
        }
    }
}

/**
 * 更新题目提交数
 * 数据库连接指针
 * 题目Id
 */
void update_problem_submition(MYSQL *&conn, int problem_id, bool successed_flag)
{
    char sql[BUFF_SIZE];
    if (successed_flag)
    {
        sprintf(sql, "update problems set submit=submit+1,accepted=accepted+1 where problem_id = %d", problem_id);

        show_log('v', "client-update_problem_submition", "更新[problems]表数据,sql代码[%s]", sql);

        if (mysql_real_query(conn, sql, strlen(sql)))
        {
            show_log('e', "client-update_problem_submition", "更新失败，错误原因[%s]", mysql_error(conn));
        }
    }
    else
    {
        sprintf(sql, "update problems set submit=submit+1 where problem_id = %d", problem_id);

        show_log('v', "client-update_problem_submition", "更新[problems]表数据,sql代码[%s]", sql);

        if (mysql_real_query(conn, sql, strlen(sql)))
        {
            show_log('e', "client-update_problem_submition", "更新失败，错误原因[%s]", mysql_error(conn));
        }
    }
}

/**
 * 获取solution的信息
 * 数据库连接指针
 * 提交Id
 * 题目Id
 * 用户Id
 * 语言
 */
void get_solution_info_mysql(MYSQL *&conn, char *solution_id, int &problem_id, int &user_id, int &lang)
{
    MYSQL_RES *res;
    MYSQL_ROW row;

    char sql[BUFF_SIZE];

    sprintf(sql, "select problem_id, user_id, language from solution where solution_id='%s'", solution_id);
    show_log('v', "client-get_solution_info_mysql", "查询[solution]表信息，查询代码[%s]", sql);

    mysql_real_query(conn, sql, strlen(sql));
    res = mysql_store_result(conn);
    row = mysql_fetch_row(res);
    if (mysql_num_rows(res) == 0)
    {
        show_log('v', "client-get_solution_info_mysql", "根据提交Id查询，不存在该提交");
        exit(1);
    }
    problem_id = atoi(row[0]);
    user_id = atoi(row[1]);
    lang = atoi(row[2]);
    show_log('v', "client-get_solution_info_mysql", "查询成功，problem_id(%d)，user_id(%d)，lang(%d)", problem_id, user_id, lang);
    mysql_free_result(res);
}