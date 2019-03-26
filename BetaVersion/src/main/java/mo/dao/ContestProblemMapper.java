package mo.dao;

import mo.entity.po.ContestProblem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestProblemMapper {

    /**
     * 查询指定比赛的问题集
     *
     * @param contest_id 比赛号
     * @return 问题集
     */
    @Select("select * from contest_problem where contest_id = #{contest_id}")
    List<ContestProblem> findCompetitionProblemsByContestIdFromContestProblem(@Param("contest_id") Integer contest_id);

    /**
     * 根据问题Id查找所在比赛号
     *
     * @param problem_id 问题Id
     * @return 比赛号
     */
    @Select("select contest_id from contest_problem where problem_id = #{problem_id})")
    Integer findCompetitionProblemByProblemIdFromContestProblem(@Param("problem_id") Integer problem_id);

    /**
     * 将指定题目添加至指定比赛
     *
     * @param contest_id 比赛号
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Insert("insert into contest_problem (contest_id,problem_id,num,title) values(#{contest_id},#{problem_id},#{num},#{title})")
    int insertContestProblemByContestIdAndProblemId(@Param("contest_id") Integer contest_id, @Param("problem_id") Integer problem_id, @Param("num") int num, @Param("title") String title);

    //TOD 删除时修改最大序列

    /**
     * 查询指定比赛当前最大的比赛序列
     *
     * @param contest_id 比赛号
     * @return num的最大值
     */
    @Select("select max(num) from contest_problem where contest_id =#{contest_id}")
    int findContestMaxNumByContestIdFromContestProblem(@Param("contest_id") Integer contest_id);

    /**
     * 删除比赛中指定题目
     *
     * @param contest_id 竞赛号
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Delete("delete from contest_problem where contest_id = #{contest_id} and problem_id = #{problem_id}")
    int delProblemFromContestByContestIdAndProblemId(@Param("contest_id") Integer contest_id, @Param("problem_id") Integer problem_id);

    /**
     * 修改指定比赛的指定题目的顺序号
     *
     * @param num        顺序号
     * @param contest_id 竞赛号
     * @param problem_id 题目Id
     * @return 影响行数
     */
    @Update("update contest_problem set num = #{num} where contest_id = #{contest_id} and problem_id = #{problem_id}")
    int changeProblemNumFromContestByContestIdAndProblemId(@Param("num") int num, @Param("contest_id") Integer contest_id, @Param("problem_id") Integer problem_id);
}
