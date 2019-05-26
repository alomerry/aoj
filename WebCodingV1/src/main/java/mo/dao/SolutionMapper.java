package mo.dao;

import mo.entity.po.Solution;
import mo.entity.vo.SolutionStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SolutionMapper {

    //TOD 插入结果表：添加到指定竞赛

    /**
     * 插入结果表
     *
     * @param solution_id 12位随机唯一字符串
     * @param problem_id  问题Id
     * @param user_id     用户Id
     * @param result      结果（4:AC 0:WA）
     * @param language    语言
     * @param ip          用户ip
     * @param create_at   创建时间
     * @param code_lenght 代码长度
     * @return
     */
    @Insert("insert into solution (solution_id,problem_id,user_id,create_at,result,language,ip,code_lenght) " +
            "values (#{solution_id},#{problem_id},#{user_id},#{create_at},#{result},#{language},#{ip},#{code_lenght})")
    int insertOneItemIntoSolution(@Param("solution_id") String solution_id,
                                  @Param("problem_id") Integer problem_id,
                                  @Param("user_id") Integer user_id,
                                  @Param("create_at") String create_at,
                                  @Param("result") Integer result,
                                  @Param("language") Integer language,
                                  @Param("ip") String ip,
                                  @Param("code_lenght") Integer code_lenght);

    /**
     * 插入源代码表
     *
     * @param solution_id 结果Id
     * @param source      源代码
     * @return 影响行数
     */
    @Insert("insert into source_code (solution_id,source) values (#{solution_id},#{source})")
    int insertCodeIntoSource(@Param("solution_id") String solution_id, @Param("source") String source);

    /**
     * 按页码以判题时间降序获取结果集
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return 结果集
     */
    @Select("select * from solution order by judgetime desc limit #{start},#{per_page}")
    List<Solution> findSolutionOrderByJudgeTimeAndPage(@Param("start") int start, @Param("per_page") int per_page);

    /**
     * 根据提交装填按页码以判题时间降序获取结果集
     *
     * @param state    状态
     * @param start    起始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from solution where result = #{result} order by judgetime desc limit #{start},#{per_page}")
    List<Solution> findSolutionByResultOrderByJudgeTimeAndPage(@Param("result") int state, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * @param user_id  提交的用户
     * @param start    开始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from solution where user_id = #{user_id} order by judgetime desc limit #{start},#{per_page}")
    List<Solution> findSolutionByUserIdOrderByJudgeTimeAndPage(@Param("user_id") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * @param user_id  提交的用户
     * @param start    开始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from solution where user_id = #{user_id} and result = #{result} order by judgetime desc limit #{start},#{per_page}")
    List<Solution> findSolutionByUserIdAndResultOrderByJudgeTimeAndPage(@Param("result") int state, @Param("user_id") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 按页码以判题时间降序获取结果集
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return 结果集
     */
    @Select("select * from solution where contest_id = #{contest_id} order by judgetime desc limit #{start},#{per_page}")
    List<Solution> findContestSolutionByContestIdOrderByJudgeTimeAndPage(@Param("contest_id") Integer contest_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 获取12位不重复随机字符串
     *
     * @return
     */
    @Select("SELECT solution_id FROM ((select substring(MD5(RAND()),1,12)AS solution_id) AS solution_id) WHERE solution_id NOT IN (SELECT solution_id FROM solution) LIMIT 1")
    String getUniqueSolutionId();

    /**
     * 查询指定用户在指定竞赛中的正确题目数
     *
     * @param user_id
     * @param contest_id
     * @return
     */
    @Select("select count(distinct problem_id) from (select problem_id from solution where contest_id = #{contest_id} and user_id= #{user_id} and result ='4') as problem_id")
    int getUserCorrectSlovedNum(@Param("user_id") int user_id, @Param("contest_id") int contest_id);

    /**
     * 查询指定用户在指定竞赛中的正确题目数
     *
     * @param user_id
     * @param contest_id
     * @return
     */
    @Select("select count(solution_id) from solution where contest_id = #{contest_id} and user_id= #{user_id} and result ='4'")
    int getUserCorrectSolutionNum(@Param("user_id") int user_id, @Param("contest_id") int contest_id);

    /**
     * 查询指定用户在指定竞赛中的提交
     *
     * @param user_id
     * @param contest_id
     * @return
     */
    @Select("select count(solution_id) from solution where contest_id = #{contest_id} and user_id= #{user_id}")
    int getUserTotalSolutionNum(@Param("user_id") int user_id, @Param("contest_id") int contest_id);

    /**
     * 查询指定用户指定状态的提交数
     *
     * @param userId 用户Id
     * @param state  状态
     * @return
     */
    @Select("select count(solution_id) from solution where user_id = #{user_id} and result = #{result}")
    Integer getUserStateSolutionTotalNumber(@Param("user_id") Integer userId, @Param("result") int state);

    /**
     * 查询指定用户的提交数
     *
     * @param userId 用户Id
     * @return
     */
    @Select("select count(solution_id) from solution where user_id = #{user_id}")
    Integer getUserSolutionTotalNumber(@Param("user_id") Integer userId);

    /**
     * 查询指定状态的提交数
     *
     * @param state 装填
     * @return
     */
    @Select("select count(solution_id) from solution where result = #{result}")
    Integer getStateSolutionTotalNumber(@Param("result") int state);

    /**
     * 查询提交数
     *
     * @return
     */
    @Select("select count(solution_id) from solution")
    Integer getSolutionTotalNumber();

    @Select("select count(solution_id) as total ,result  from solution where problem_id = #{problem_id} and result in ('4','7','8','10','11','6') group by result order by total desc ")
    List<SolutionStatus> findProblemStatus(@Param("problem_id") Integer problemId);


    /**
     * 按判题时间降序获取结果集
     *
     * @param user_id 用户Id
     * @return 结果集
     */
//    @Select("select * from solution where user_id = #{user_id} order by judgetime desc")
//    List<Solution> findSolutionByUserIdOrderByJudgeTimeDesc(@Param("user_id") Integer user_id);

    /**
     * 按判题时间升序获取结果集
     *
     * @param user_id 用户Id
     * @return 结果集
     */
//    @Select("select * from solution where user_id = #{user_id} order by judgetime asc")
//    List<Solution> findSolutionByUserIdOrderByJudgeTimeAsc(@Param("user_id") Integer user_id);


    /**
     * 按页码以判题时间升序获取结果集
     *
     * @param user_id 用户Id
     * @param start   起始
     * @param end     结束
     * @return 结果集
     */
//    @Select("select * from solution where user_id = #{user_id} order by judgetime asc limit #{start},#{end}")
//    List<Solution> findSolutionByUserIdOrderByJudgeTimeAscByPage(@Param("user_id") Integer user_id, @Param("start") int start, @Param("end") int end);

}
