package mo.dao;

import mo.entity.po.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {

    /**
     * 根据题目Id查找题目标签
     *
     * @param problem_id 题目Id
     * @return 标签集合
     */
    @Select("select * from tag where tag_id in(select tag_id from problem_tag where problem_id = #{problem_id})")
    List<Tag> findTagsByProblemId(@Param("problem_id") Integer problem_id);
}
