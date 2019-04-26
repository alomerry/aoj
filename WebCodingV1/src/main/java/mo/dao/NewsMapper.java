package mo.dao;

import mo.entity.po.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NewsMapper {

    @Insert("insert into news () values ()")
    int insertNewsBlindToContest(@Param("news") News news);

    int insertNews(@Param("news") News news);
}
