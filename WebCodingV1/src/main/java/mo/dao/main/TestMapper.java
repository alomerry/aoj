package mo.dao.main;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TestMapper {

    /**
     * 竞赛参加人数+1
     *
     * @param contest_id 竞赛Id
     * @return 影响行数
     */
    @Update("update contest set now=now +1 where contest_id = #{contest_id}")
    int addOneContestNowNumber(@Param("contest_id") Integer contest_id);

    /**
     * 更新申请状态
     *
     * @param user_id    申请者
     * @param contest_id 申请的比赛号
     * @return 影响行数
     */
    @Update("update contest_apply set status = 1 where user_id = #{user_id} and contest_id = #{contest_id}")
    int updateOneApplication(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    @Select("select contest_id from contest_problem where problem_id = #{problem_id}")
    int findCompetitionProblemFromContestProblemByProblemId(@Param("problem_id") Integer problem_id);
}
