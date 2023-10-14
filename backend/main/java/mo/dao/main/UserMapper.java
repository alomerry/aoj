package mo.dao.main;

import mo.entity.po.main.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查找所有用户
     *
     * @return 返回用户集
     */
    @Select(value = "select * from users")
    List<User> findAll();

    /**
     * 根据用户名查找指定用户名数量
     *
     * @param username 用户名
     * @return 返回该用户名数量
     */
    @Select(value = "select count(*) from users where username = #{username}")
    int GetNumByUserName(@Param("username") String username);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param passwd   用户密码
     * @return 返回用户实体
     */
    @Select(value = "select * from users where username = #{username} and passwd = #{passwd}")
    User findUserByUserNameAndUserPwd(@Param("username") String username, @Param("passwd") String passwd);


    /**
     * 按指定序列查询指定页数用户
     *
     * @param start    起始数
     * @param per_page 每页数量
     * @param orderBy  排序列
     * @param type     升降序
     * @return 返回用户集
     */
    @Select("select * from users order by ${orderBy} ${type} limit #{start},#{per_page}")
    List<User> findUsersByPageAndOrderBy(@Param("start") int start, @Param("per_page") int per_page, @Param("orderBy") String orderBy, @Param("type") String type);

    /**
     * 根据用户Id查找用户
     *
     * @param user_id 用户Id
     * @return 返回用户实体
     */
    @Select("select * from users where user_id = #{user_id}")
    User findUserByUserId(@Param("user_id") Integer user_id);

    /**
     * 根据用户昵称相似度查找用户
     *
     * @param nickname 用户昵称
     * @return 返回用户集
     */
    @Select("select * from users where nickname like '%${nickname}%'")
    List<User> findUserBySimplyNickName(@Param("nickname") String nickname);

    /**
     * 根据关键词查询用户昵称用户名相似度用户
     *
     * @param keycode 关键词
     * @return 返回用户集
     */
    @Select("select * from users where nickname like '%${keycode}%' or username like '%${keycode}%'")
    List<User> findUserBySimilarUserNameAndNickName(@Param("keycode") String keycode);

    /**
     * 更新用户的sessionId
     *
     * @param user_id    用户Id
     * @param session_id sessionId
     * @return 返回影响行数
     */
    @Update("update users set session_id = #{session_id},last_login=#{last_login} where user_id = #{user_id}")
    int updateSessionIdByUserId(@Param("user_id") Integer user_id, @Param("session_id") String session_id, @Param("last_login") Timestamp now);

    /**
     * 查询指定页数用户
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return 用户集
     */
    @Select("select * from users limit #{start},#{per_page}")
    List<User> findUsersByPage(@Param("start") int start, @Param("per_page") int per_page);

    /**
     * 查询指定页数用户
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return 用户集
     */
    @Select("select * from users where disabled = #{disabled} limit #{start},#{per_page}")
    List<User> findUsersByDisabledPageAndPerPage(@Param("disabled") int disabled, @Param("start") int start, @Param("per_page") int per_page);


    /**
     * 根据用户Id查询用户的用户名昵称
     *
     * @param user_id 用户Id
     * @return 用户实体
     */
    @Select("select user_id,username,nickname from users where user_id = #{user_id}")
    User findUserIdUserNameUserNickNameByUserId(@Param("user_id") Integer user_id);

    /**
     * 根据用户Id删除用户
     *
     * @param user_id 用户Id
     * @return 影响行数
     */
    @Delete("delete from users where user_id = #{user_id}")
    int deleteUserByUserId(@Param("user_id") Integer user_id);

    /**
     * 禁用用户
     *
     * @param user_id      用户Id
     * @param disableState 状态
     * @return影响行数
     */
    @Update("update users set disabled = #{disabled} where user_id = #{user_id}")
    int updateUserDisabled(@Param("user_id") Integer user_id, @Param("disabled") Integer disableState);

    /**
     * 修改用户信息
     *
     * @param user 用户实体
     * @return 影响行数
     */
    @Update("update users set nickname = #{user.nickname},email = #{user.email},passwd=#{user.passwd},disabled=#{user.disabled} where user_id =#{user.user_id}")
    int updateUserNickNameEmailPasswdDisAbled(@Param("user") User user);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return
     */
    @Select("select * from users where username =#{username}")
    User findUserByUsername(@Param("username") String username);

    /**
     * 查询指定竞赛中的用户（已加入）
     *
     * @param contest_id 竞赛Id
     * @param start      起始
     * @param per_page   每页数量
     * @return
     */
    @Select("select user_id from contest_apply where contest_id = #{contest_id} and status = '1' limit #{start} ,#{per_page}")
    int[] findContestsUsers(@Param("contest_id") int contest_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 新建用户
     *
     * @param user 用户实体
     * @return
     */
    @Insert("insert into users (username,nickname,passwd,email) values (#{user.username},#{user.username},#{user.passwd},#{user.email})")
    int insertNewUser(@Param("user") User user);

    /**
     * 查询上个插入的主键
     *
     * @return
     */
    @Select("select LAST_INSERT_ID()")
    Integer findLastInsertId();

    /**
     * 查询用户数量
     *
     * @return
     */
    @Select("select count(user_id) from users")
    int getUserTotalNumber();

    /**
     * 查询用户数量
     *
     * @return
     */
    @Select("select count(user_id) from users where disabled = #{disabled}")
    int getUserTotalNumerByIsDisabled(@Param("disabled") int disabled);

    /**
     * 更新头像
     * @param path 头像路径
     * @param userId 用户Id
     * @return
     */
    @Update("UPDATE users SET head_img = #{path} WHERE user_id = #{userId}")
    int updateUserHeaderImage(@Param("path") String path,@Param("userId") Integer userId);

    /**
     * 更新用户配置
     * @param user 用户
     * @param user_id 用户标号
     * @return
     */
    @Update("update users set nickname=#{user.nickname},school=#{user.school},github_url=#{user.github_url}," +
            "blog_url=#{user.blog_url},own_words=#{user.own_words} where user_id = #{user_id}")
    int updateUserProfile(@Param("user")User user,@Param("user_id")Integer user_id);

    /**
     * 更新用户密码和邮箱
     * @param pwd 密码
     * @param email 邮箱
     * @param user_id 用户编号
     * @return
     */
    @Update("update users set passwd = #{pwd} ,email = #{email} where user_id = #{user_id}")
    int updateUserPwdAndEmail(@Param("pwd") String pwd,@Param("email") String email,@Param("user_id") Integer user_id);

    /**
     * 更新指定属性
     * @param col 属性
     * @param val 值
     * @param user_id 用户编号
     * @return
     */
    @Update("update users set ${col} = #{val} where user_id = #{user_id}")
    int updateUserCol(@Param("col")String col,@Param("val") String val,@Param("user_id") Integer user_id);
}

