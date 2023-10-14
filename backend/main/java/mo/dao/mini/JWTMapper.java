package mo.dao.mini;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JWTMapper {

    @Select("select count(jwt_id) from jwt where jwt_id = #{jwt_id}")
    int checkJWTExist(@Param("jwt_id") Integer jwt);
}
