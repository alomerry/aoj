package mo.dao;

import mo.entity.po.Problem;
import org.apache.ibatis.annotations.*;

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
     *
     * @param problem_id 题目Id
     * @return 公开级别
     */
    @Select("select defunct from problems where problem_id = #{problem_id}")
    Character findProblemPublicLevelByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 根据题目Id查找创建者Id
     *
     * @param problem_id 题目Id
     * @return 创建者Id
     */
    @Select("select create_by from problems where problem_id = #{problem_id}")
    Integer findCreatorIdByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 根据题目Id查询题目标题
     *
     * @param problem_id 题目Id
     * @return 题目实体
     */
    @Select("select problem_id,title from problems where  problem_id = #{problem_id}")
    Problem findProblemIdProblemTitleByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 删除指定题目
     *
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Delete("delete from problems where problem_id = #{problem_id}")
    int deleteProblemByPorblemId(@Param("problem_id") Integer problem_id);

    /**
     * 新建题目
     *
     * @param problem
     * @param user_id
     * @return
     */
    @Insert("insert into problems (display_id ,title,create_by," +
            "hint,source,description," +
            "created_at,memory_limit,time_limit," +
            "sample_input,sample_output,defunct,output,input) " +
            "values (#{problem.display_id},#{problem.title},#{create_by}," +
            "#{problem.hint},#{problem.source},#{problem.description}," +
            "#{problem.created_at},#{problem.memory_limit},#{problem.time_limit}," +
            "#{problem.sample_input},#{problem.sample_output},#{problem.defunct},#{problem.output},#{problem.input})")
    int insertProblem(@Param("problem") Problem problem, @Param("create_by") Integer user_id);

    /**
     * 更新题目
     *
     * @param problem 题目实体
     * @return
     */
    @Update("update problems set display_id =#{problem.display_id}," +
            "title=#{problem.title}," +
            "hint=#{problem.hint}," +
            "source=#{problem.source}," +
            "description=#{problem.description}," +
            "memory_limit=#{problem.memory_limit}," +
            "time_limit=#{problem.time_limit}," +
            "sample_input=#{problem.sample_input}," +
            "sample_output=#{problem.sample_output}," +
            "defunct=#{problem.defunct}," +
            "output=#{problem.output}," +
            "input=#{problem.input} where problem_id = #{problem.problem_id}")
    int updateProblem(@Param("problem") Problem problem);

    @Select("select LAST_INSERT_ID()")
    Integer findLastInsertId();

    /**
     * 根据题目Ids查询
     *
     * @param problem_ids 题目Id数组
     * @param start       起始
     * @param per_page    每页数量
     * @return
     */
    @Select("select problem_id,submit,defunct,accepted,created_at,create_by,title,display_id from problems " +
            "where problem_id in ${problem_ids} limit #{start},#{per_page}")
    List<Problem> findSimpleProblemByProblemIdS(@Param("problem_ids") String problem_ids, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 根据公开级别查询题目数量
     *
     * @param defunct "(1,2,3)"
     * @return
     */
    @Select("select count(problem_id) from problems where defunct in ${defunct}")
    Integer findProblemTotalNumsByDefunct(@Param("defunct") String defunct);

    /**
     * 更新指定题目公开级别
     *
     * @param problem_id 题目Id
     * @param defunct    公开级别
     * @return
     */
    @Update("update problems set defunct =#{defunct} where problem_id = #{problem_id}")
    int updateDefunctByProblemId(@Param("problem_id") Integer problem_id, @Param("defunct") String defunct);
}