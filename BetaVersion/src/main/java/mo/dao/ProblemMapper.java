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
     * 根据Id查询题目
     *
     * @param problem_id 题目Id
     * @return 题目实体
     */
    @Select("select * from problems where problem_id = #{problem_id}")
    Problem findProblemByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 查询题目创建者的题目
     *
     * @param user_id 题目创建者用户Id
     * @param type    排序列
     * @return 题目集
     */
    @Select("select * from problems where user_id = #{user_id} order by #{type}")
    List<Problem> findProblemsOrderByTypeByCreatorUserId(@Param("user_id") Integer user_id, @Param("type") String type);

    /**
     * 按页码查询指定权限的题目
     *
     * @param defunct 公开级别
     * @param type    排序列
     * @param start   起始
     * @param end     结束
     * @return 题目集
     */
    @Select("select * from problems where defunct = #{defunct} order by #{type} limit #{start},#{end}")
    List<Problem> findProblemsOrderByTypeByDefunctAndPage(@Param("defunct") char defunct, @Param("type") String type, @Param("start") int start, @Param("end") int end);

    /**
     * 按页码查询指定权限的题目
     *
     * @param defunct 公开级别
     * @param type    排序列
     * @return 题目集
     */
    @Select("select * from problems where defunct = #{defunct} order by #{type}")
    List<Problem> findProblemsOrderByTypeByDefunct(@Param("defunct") char defunct, @Param("type") String type);

    /**
     * 按页码查询指定权限的题目
     *
     * @param defunct 公开级别
     * @param start   起始
     * @param end     结束
     * @return 题目集
     */
    @Select("select * from problems where defunct = #{defunct} limit #{start},#{end}")
    List<Problem> findProblemsByDefunctAndPage(@Param("defunct") char defunct, @Param("start") int start, @Param("end") int end);

    /**
     * 检查是否指定题目是否公开
     *
     * @param problem_id 题目Id
     * @return 公开返回1，否则返回0
     */
    @Select("Select count(*) from problems where problem_id = #{problem_id} and defunct = '1'")
    Integer checkProblemIsPublicFromProblems(@Param("problem_id") Integer problem_id);

    /**
     * 根据题目Id查询题目的标题
     *
     * @param problem_id 题目Id
     * @return 题目标题
     */
    @Select("Select title from problems where problem_id = #{problem_id}")
    String findProblemTitleByProblemIdFromProblems(@Param("problem_id") Integer problem_id);

    /**
     * 根据页码查询题目
     *
     * @param start 起始
     * @param end   结束
     * @return 题目集
     */
    @Select("select * from problems limit #{start},#{end}")
    List<Problem> findProblemsByPageFromProblems(@Param("start") int start, @Param("end") int end);

    /**
     * 根据页码按指定列排序查询题目
     *
     * @param type  排序列
     * @param sort  升降序
     * @param start 起始
     * @param end   结束
     * @return 题目集
     */
    @Select("select * from problems order by ${type} ${sort} limit #{start},#{end}")
    List<Problem> findProblemsByPageOrderByTypeAndSortFromProblems(@Param("type") String type, @Param("sort") String sort, @Param("start") int start, @Param("end") int end);

    @Delete("delete from problems where problem_id = #{problem_id}")
    int delProblemByProblemId(@Param("problem_id") Integer problem_id);

}
