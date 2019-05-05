package mo.dao;

import mo.entity.po.ContestApply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestApplyMapper {

    /**
     * 根据页码查询指定竞赛的竞赛申请
     *
     * @param contestId 竞赛Id
     * @param start     起始
     * @param per_page  每页数量
     * @return
     */
    @Select("select * from contest_apply where contest_id = #{contest_id} limit #{start},#{per_page}")
    List<ContestApply> getContestApplyByCreatorId(@Param("contest_id") Integer contestId,
                                                  @Param("start") int start,
                                                  @Param("per_page") int per_page);

    /**
     * 查询指定竞赛的为操作申请数
     *
     * @param contestId 竞赛Id
     * @return
     */
    @Select("select count(id) from contest_apply where contest_id = #{contest_id} and status = 0")
    Integer getUncheckedContestApplyNumberByContestId(@Param("contest_id") Integer contestId);
}
