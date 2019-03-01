package mo.dao;

import mo.entity.po.ContestApply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestApplyMapper {

    /**
     * 用户申请加入比赛
     *
     * @param user_id    用户Id
     * @param contest_id 比赛号
     * @return 影响行数
     */
    @Insert("insert into contest_apply (user_id,contest_id) values (#{user_id},#{contest_id})")
    int insertContestApply(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 查询是否存在申请
     *
     * @param user_id    用户Id
     * @param contest_id 比赛号
     * @return 返回数量
     */
    @Select("select count(*) from contest_apply where user_id = #{user_id} and contest_id = #{contest_id}")
    int checkContestApplyIsExsitApplyByUserIdAndContestIdFromContest(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 查找指定已被接受申请的比赛 是否存在
     *
     * @param user_id    用户Id
     * @param contest_id 比赛号
     * @return 比赛申请
     */
    @Select("select count(*) from contest_apply where user_id = #{user_id} and contest_id = #{contest_id} and status = 1;")
    int checkContestApplyHasBeenAcceptIsExsitByUserIdAndContestIdFromContestApply(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 查找指定比赛创建者的比赛申请者
     *
     * @param user_id 创建者Id
     * @return 申请集
     */
    @Select("select * from contest_apply where status = 0 and contest_id in (select contest_id from contest where user_id = #{user_id}) limit #{start} ,#{end}")
    List<ContestApply> findCreatorContestApplysByCreatorUserIdFromContestApply(@Param("user_id") Integer user_id, @Param("start") int start, @Param("end") int end);

    /**
     * 根据创建者Id查找指定页比赛集的申请者
     *
     * @param user_id 创建者Id
     * @param start   起始
     * @param end     结束
     * @param type    排序列
     * @param sort    升降序
     * @return 申请集
     */
    @Select("select * from contest_apply where status = 0 and contest_id in (select contest_id from contest where user_id = #{user_id}) order by ${type} ${sort} limit #{start} ,#{end}")
    List<ContestApply> findCreatorContestApplysByCreatorUserIdOrderByTypeAndSortFromContestApply(@Param("user_id") Integer user_id, @Param("start") int start, @Param("end") int end, @Param("type") String type, @Param("sort") String sort);

    @Select("select * from contest_apply where status = 0 order by ${type} ${sort} limit #{start} ,#{end}")
    List<ContestApply> findContestApplysByPageOrderByTypeAndSortFromContestApply(@Param("start") int start, @Param("end") int end, @Param("type") String type, @Param("sort") String sort);

    @Select("select * from contest_apply where status = 0 limit #{start} ,#{end}")
    List<ContestApply> findContestApplysByPageFromContestApply(@Param("start") int start, @Param("end") int end);

    /**
     * 更新申请状态
     *
     * @param user_id    申请者
     * @param contest_id 申请的比赛号
     * @return 影响行数
     */
    @Update("update contest_apply set status = 1 where user_id = #{user_id} and contest_id = #{contest_id}")
    int updateContestApplyByUserIdAndContestId(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 删除指定用户的比赛申请
     *
     * @param user_id    用户Id
     * @param contest_id 比赛号
     * @return 影响行数
     */
    @Delete("delete from contest_apply where user_id = #{user_id} and contest_id = #{contest_id}")
    int deleteContestApplyFromContestApplyByUserIdAndContestId(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 查找指定用户的参加的比赛集
     *
     * @param user_id 用户Id
     * @return 比赛集
     */
    @Select("select * from contest_apply where user_id = #{user_id}")
    List<ContestApply> findOneUserContestsFromContestByUserId(@Param("user_id") Integer user_id);

    /**
     * @param user_id
     * @param contest_id
     * @return
     */
    @Select("select * from contest_apply where user_id = #{user_id} and contest_id = #{contest_id}")
    ContestApply findApplyByUserIdAndContestId(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    @Select("select * from contest_apply where contest_id = #{contest_id}")
    List<ContestApply> findContestApplysFromContestApplyByContestId(@Param("contest_id") Integer contest_id);

}
