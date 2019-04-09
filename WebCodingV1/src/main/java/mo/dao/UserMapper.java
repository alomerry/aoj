package mo.dao;

import mo.entity.po.User;
import org.apache.ibatis.annotations.*;

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
     * 插入新用户
     *
     * @param user 用户数据
     * @return 返回影响行数
     */
    @Insert("insert into users (username,passwd,nickname,school,email) values (#{username},#{passwd},#{nickname},#{school},#{email})")
    int insertUser(User user);


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
     * 根据用户名相似度查找用户
     *
     * @param nickname 用户名
     * @return 返回用户集
     */
    @Select("select * from users where nickname like #{nickname}")
    List<User> findUserBySimplyNickName(@Param("nickname") String nickname);

    /**
     * 更新用户的sessionId
     *
     * @param user_id    用户Id
     * @param session_id sessionId
     * @return 返回影响行数
     */
    @Update("update users set session_id = #{session_id} where user_id = #{user_id}")
    int updateSessionIdByUserId(@Param("user_id") Integer user_id, @Param("session_id") String session_id);

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

}

