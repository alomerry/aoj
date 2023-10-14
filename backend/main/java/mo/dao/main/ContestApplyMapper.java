package mo.dao.main;

import mo.entity.po.main.ContestApply;
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
    @Select("select * from contest_apply where contest_id = #{contest_id} order by status limit #{start},#{per_page}")
    List<ContestApply> getContestAppliesByCreatorId(@Param("contest_id") Integer contestId,
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

    /**
     * 查询指定竞赛申请数
     *
     * @param contestId 竞赛Id
     * @return
     */
    @Select("select count(id) from contest_apply where contest_id = #{contest_id}")
    Integer getContestApplyNumberByContestId(@Param("contest_id") Integer contestId);

    /**
     * 根据Id查询申请
     *
     * @param id 申请Id
     * @return
     */
    @Select("select * from contest_apply where id = #{id}")
    ContestApply findContestApplyById(@Param("id") Integer id);

    /**
     * 更新指定申请信息
     *
     * @param id     申请Id
     * @param status 状态
     * @return
     */
    @Update("update contest_apply set status = #{status} where id = #{id}")
    int updateContestApplyStatusById(@Param("id") Integer id, @Param("status") int status);

    /**
     * 新建申请
     *
     * @param contest_id 竞赛Id
     * @param user_id    用户Id
     * @return
     */
    @Insert("insert into contest_apply (contest_id,user_id) values (#{contest_id},#{user_id})")
    int insertNewContestApply(@Param("contest_id") Integer contest_id, @Param("user_id") Integer user_id);

    /**
     * 根据竞赛Id和用户Id查询申请
     *
     * @param contest_id 竞赛Id
     * @param user_id    用户Id
     * @return
     */
    @Select("select count(id) from contest_apply where contest_id = #{contest_id} and user_id = #{user_id}")
    int getNumbersByContestIdAndUserId(@Param("contest_id") Integer contest_id, @Param("user_id") Integer user_id);

    /**
     * 查询指定用户是否申请指定竞赛成功
     *
     * @param contest_id 竞赛Id
     * @param user_id    用户Id
     * @return
     */
    @Select("select count(id) from contest_apply where contest_id = #{contest_id} and user_id = #{user_id} and status = '1'")
    int checkIsApplySuccessed(@Param("contest_id") Integer contest_id, @Param("user_id") Integer user_id);
}
