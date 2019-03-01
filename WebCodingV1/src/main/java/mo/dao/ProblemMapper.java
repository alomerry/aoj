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
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select problem_id,submit,accepted,title from problems where defunct in ${defunct} limit #{page},#{per_page}")
    List<Problem> findSimpleProblemsByDefunctAndPage(@Param("defunct") String defunct, @Param("page") int page, @Param("per_page") int per_page);

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
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    @Select("select * from problems where defunct in ${defunct} limit #{page},#{per_page}")
    List<Problem> findProblemsByDefunctAndPage(@Param("defunct") String defunct, @Param("page") int page, @Param("per_page") int per_page);

}
