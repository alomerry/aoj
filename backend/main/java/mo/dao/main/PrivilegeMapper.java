package mo.dao.main;

import mo.entity.po.main.Privilege;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PrivilegeMapper {
    /**
     * 根据用户Id查询用户级别实体
     *
     * @param user_id 用户Id
     * @return 返回用户级别实体
     */
    @Select("select * from privilege where user_id = #{user_id}")
    Privilege findPrivilegeByUserId(@Param("user_id") Integer user_id);

    /**
     * 根据用户Id查询管理员级别
     *
     * @param user_id 用户Id
     * @return 管理员级别
     */
    @Select("select rightstr from privilege where user_id = #{user_id}")
    String findRightStrByUserId(@Param("user_id") Integer user_id);

    /**
     * 更新用户权限级别
     *
     * @param privilege 权限实体
     * @return 影响行数
     */
    @Update("update privilege set rightstr = #{privilege.rightstr} where user_id = #{privilege.user_id}")
    int updateRightStr(@Param("privilege") Privilege privilege);

    /**
     * 插入一条权限级别
     *
     * @param privilege 权限实体
     * @return 影响行数
     */
    @Insert("insert privilege (user_id,rightstr) values (#{privilege.user_id},#{privilege.rightstr})")
    int insertPrivilege(@Param("privilege") Privilege privilege);
}
