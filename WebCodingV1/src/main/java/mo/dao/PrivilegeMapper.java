package mo.dao;

import mo.entity.po.Privilege;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PrivilegeMapper {
    /**
     * 根据用户Id查询用户级别实体
     *
     * @param user_id 用户Id
     * @return 返回用户级别实体
     */
    @Select("select * from privilege where user_id = #{user_id}")
    Privilege findPrivilegeByUserId(@Param("user_id") Integer user_id);

    @Select("select rightstr from privilege where user_id = #{user_id}")
    String findRightStrByUserId(@Param("user_id") Integer user_id);
}
