package mo.dao;

import mo.entity.po.Solution;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SolutionMapper {

    //TODO 插入结果表：添加到指定竞赛

    /**
     * 插入结果表
     *
     * @param problem_id  问题Id
     * @param user_id     用户Id
     * @param time        用时（ms）
     * @param memory      所用空间
     * @param in_date     加入时间
     * @param result      结果（4：AC）
     * @param language    语言
     * @param ip          用户ip
     * @param valid       是否有效
     * @param code_lenght 代码长度
     * @param judgetime   判题时间
     * @return
     */
    @Insert("insert into solution (problem_id,user_id,time,memory,in_date,result,language,ip,valid,code_lenght,judgetime) " +
            "values (#{problem_id},#{user_id},#{time},#{memory},#{in_date},#{result},#{language},#{ip}, #{valid},#{code_lenght},#{judgetime})")
    int insertOneItemIntoSolution(@Param("problem_id") Integer problem_id,
                                  @Param("user_id") Integer user_id,
                                  @Param("time") Integer time,
                                  @Param("memory") Integer memory,
                                  @Param("in_date") String in_date,
                                  @Param("result") Integer result,
                                  @Param("language") Integer language,
                                  @Param("ip") String ip,
                                  @Param("valid") String valid,
                                  @Param("code_lenght") Integer code_lenght,
                                  @Param("judgetime") String judgetime);

    /**
     * 插入源代码表
     *
     * @param solution_id 结果Id
     * @param source      源代码
     * @return 影响行数
     */
    @Insert("insert into source_code (solution_id,source) values (#{solution_id},#{source})")
    int insertCodeIntoSource(@Param("solution_id") long solution_id, @Param("source") String source);

    /**
     * 按判题时间降序获取结果集
     *
     * @param user_id 用户Id
     * @return 结果集
     */
    @Select("select * from solution where user_id = #{user_id} order by judgetime desc")
    List<Solution> findSolutionByUserIdOrderByJudgeTimeDesc(@Param("user_id") Integer user_id);

    /**
     * 按判题时间升序获取结果集
     *
     * @param user_id 用户Id
     * @return 结果集
     */
    @Select("select * from solution where user_id = #{user_id} order by judgetime asc")
    List<Solution> findSolutionByUserIdOrderByJudgeTimeAsc(@Param("user_id") Integer user_id);

    /**
     * 按页码以判题时间降序获取结果集
     *
     * @param user_id 用户Id
     * @param start   起始
     * @param end     结束
     * @return 结果集
     */
    @Select("select * from solution where user_id = #{user_id} order by judgetime desc limit #{start},#{end}")
    List<Solution> findSolutionByUserIdOrderByJudgeTimeDescByPage(@Param("user_id") Integer user_id, @Param("start") int start, @Param("end") int end);

    /**
     * 按页码以判题时间升序获取结果集
     *
     * @param user_id 用户Id
     * @param start   起始
     * @param end     结束
     * @return 结果集
     */
    @Select("select * from solution where user_id = #{user_id} order by judgetime asc limit #{start},#{end}")
    List<Solution> findSolutionByUserIdOrderByJudgeTimeAscByPage(@Param("user_id") Integer user_id, @Param("start") int start, @Param("end") int end);

    /**
     * 查询上次插入的主键
     *
     * @return
     */
    @Select("SELECT LAST_INSERT_ID()")
    long findLastInsertId();
}
