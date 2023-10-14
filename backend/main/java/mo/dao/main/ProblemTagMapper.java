package mo.dao.main;

import mo.entity.po.main.ProblemTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemTagMapper {

    /**
     * 新建problem tag 关联
     *
     * @param problem_id
     * @param tag_id
     * @return
     */
    @Insert("insert into problem_tag (problem_id,tag_id) values (#{problem_id},#{tag_id})")
    int insertProblemTagWithTagIdAndProblemId(@Param("problem_id") Integer problem_id, @Param("tag_id") Integer tag_id);

    /**
     * 根据标签Id查询problemTag
     *
     * @param tag_id
     * @return
     */
    @Select("select problem_id from problem_tag where tag_id = #{tag_id}")
    List<ProblemTag> findProblemIdFromProblemTagByTagId(@Param("tag_id") int tag_id);

    /**
     * 根据题目Id查询标签
     *
     * @param problem_id
     * @return
     */
    @Select("select * from problem_tag where problem_id = #{problem_id}")
    List<ProblemTag> findProblemTagByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 根据题目Id删除标签
     *
     * @param problem_id
     * @return
     */
    @Delete("delete from problem_tag where problem_id = #{problem_id}")
    int deleteProblemTagByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 插入list
     *
     * @param problemTags
     * @return
     */
    @Insert("<script>" +
            " insert into problem_tag (problem_id,tag_id) values " +
            " <foreach collection = 'list' item='item' index='index' separator=',' > " +
            " (#{item.problem_id},#{item.tag_id} )" +
            " </foreach>" +
            "</script>")
    int insertProblemTagList(@Param("list") List<ProblemTag> problemTags);

    /**
     * 删除list
     *
     * @param problemTags
     * @return
     */
    @Delete("<script>" +
            " delete from problem_tag where id in " +
            "<foreach collection = 'list' item='item' index='index' separator=',' open='('  close=')' > " +
            " #{list.id}" +
            " </foreach>" +
            "</script>")
    int deleteProblemTagList(@Param("list") List<ProblemTag> problemTags);

    /**
     * 查询题目的标签数量
     *
     * @param problem_id 题目Id
     * @return
     */
    @Select("select count(id) from problem_tag where problem_id = #{problem_id}")
    int findProblemTagNumByProblemId(@Param("problem_id") Integer problem_id);
}
