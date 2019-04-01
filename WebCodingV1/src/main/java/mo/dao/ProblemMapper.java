package mo.dao;

import mo.entity.po.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper {

    /**
     * 查询简单问题集信息
     *
     * @param defunct  公开级别
     * @param start    起始
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select problem_id,submit,defunct,accepted,created_at,create_by,title,display_id from problems where defunct in ${defunct} limit #{start},#{per_page}")
    List<Problem> findSimpleProblemsByDefunctAndPage(@Param("defunct") String defunct, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 查询简单问题集信息
     *
     * @param defunct  公开级别
     * @param start    起始
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select problem_id,submit,defunct,accepted,created_at,create_by,title,display_id from problems where defunct in ${defunct} or create_by = #{create_by} limit #{start},#{per_page}")
    List<Problem> findSimpleProblemsWithOwnByDefunctAndPage(@Param("defunct") String defunct, @Param("create_by") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 根据Id查询题目
     *
     * @param problem_id 题目Id
     * @return 题目实体
     */
    @Select("select * from problems where problem_id = #{problem_id}")
    Problem findProblemByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 根据页码查询题目
     *
     * @param defunct  公开级别
     * @param start    起始
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select * from problems where defunct in ${defunct} limit #{start},#{per_page}")
    List<Problem> findProblemsByDefunctAndPage(@Param("defunct") String defunct, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 根据页码查询题目
     *
     * @param defunct  公开级别
     * @param user_id  创题者Id
     * @param start    起始
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select * from problems where defunct in ${defunct} or create_by = #{create_by} limit #{start},#{per_page}")
    List<Problem> findProblemsWithOwnByDefunctAndPage(@Param("defunct") String defunct, @Param("create_by") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 查询指定题目的公开级别
     * @param problem_id 题目Id
     * @return 公开级别
     */
    @Select("select defunct from problems where problem_id = #{problem_id}")
    Character findProblemPublicLevelByProblemId(@Param("problem_id") Integer problem_id);

}
