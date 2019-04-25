package mo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProblemTagMapper {

    @Insert("insert into problem_tag (problem_id,tag_id) values (#{problem_id},#{tag_id})")
    int insertProblemTagWithTagIdAndProblemId(@Param("problem_id") Integer problem_id, @Param("tag_id") Integer tag_id);
}
