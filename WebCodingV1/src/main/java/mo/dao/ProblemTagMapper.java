package mo.dao;

import mo.entity.po.ProblemTag;
import mo.entity.po.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemTagMapper {

    @Insert("insert into problem_tag (problem_id,tag_id) values (#{problem_id},#{tag_id})")
    int insertProblemTagWithTagIdAndProblemId(@Param("problem_id") Integer problem_id, @Param("tag_id") Integer tag_id);

    @Select("select problem_id from problem_tag where tag_id = #{tag_id}")
    List<ProblemTag> findProblemIdFromProblemTagByTagId(@Param("tag_id") int tag_id);
}
