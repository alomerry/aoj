package mo.dao.main;

import mo.entity.po.main.CompileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CompileInfoMapper {

    /**
     * 查询编译错误信息
     * @param solutionId 提交编号
     * @return
     */
    @Select("select * from compile_info where solution_id = #{solution_id}")
    CompileInfo findCompileInfoBySolutionId(@Param("solution_id") String solutionId);
}
