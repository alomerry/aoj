package mo.dao.main;

import mo.entity.po.main.ContestProblem;
import mo.entity.po.main.Problem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestProblemMapper {

    /**
     * 查询竞赛-题目
     *
     * @param start      起始
     * @param per_page   每页数量
     * @param contest_id 比赛Id
     * @return 竞赛-题目
     */
    @Select("select * from contest_problem where contest_id = #{contest_id} limit #{start},#{per_page}")
    List<ContestProblem> findContestProblemByPageAndContestId(@Param("start") Integer start,
                                                              @Param("per_page") Integer per_page,
                                                              @Param("contest_id") Integer contest_id);

    /**
     * 添加题目至比赛
     *
     * @param contest_id 竞赛Id
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Insert("insert into contest_problem (contest_id,problem_id) values (#{contest_id},#{problem_id})")
    Integer addProblemToContest(@Param("contest_id") Integer contest_id,
                                @Param("problem_id") Integer problem_id);

    /**
     * 删除比赛中题目
     *
     * @param contest_id 竞赛Id
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Delete("delete from contest_problem where contest_id = #{contest_id} and problem_id = #{problem_id}")
    Integer deletProblemFromContest(@Param("contest_id") Integer contest_id,
                                    @Param("problem_id") Integer problem_id);

    /**
     * 查询竞赛中的题目集合
     *
     * @param contest_id
     * @return
     */
    @Select("select * from problems where problem_id in(select problem_id from contest_problem where contest_id = #{contest_id})")
    List<Problem> findProblemsFromContestProblemByContestId(@Param("contest_id") Integer contest_id);


    /**
     * 查询某个题目是否已在竞赛中
     *
     * @param contest_id 竞赛Id
     * @param problem_id 题目Id
     * @return 记录数
     */
    @Select("select count(*) from contest_problem where contest_id = #{contest_id} and problem_id = #{problem_id}")
    Integer findCountsByContestIdAndProblemId(@Param("contest_id") Integer contest_id,
                                              @Param("problem_id") Integer problem_id);
}