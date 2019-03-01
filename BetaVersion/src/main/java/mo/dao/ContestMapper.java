package mo.dao;

import mo.entity.po.Contest;
import mo.entity.po.ContestApply;
import mo.entity.po.ContestProblem;
import mo.entity.vo.ContestApplyLink;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestMapper {

    /**
     * 按页码获取公开比赛集
     *
     * @param start 起始
     * @param end   结束
     * @return 比赛集
     */
    @Select("select * from contest where privates = 0 limit #{start} ,#{end}")
    List<Contest> findPublicContestsByPageFromContest(@Param("start") int start, @Param("end") int end);

    /**
     * 按页码获取公开并且可报名的比赛
     *
     * @param start 起始
     * @param end   结束
     * @return 比赛集
     */
    @Select("select * from contest where privates = 0 and access =  1 limit #{start} ,#{end}")
    List<Contest> findPublicAndAccessContestsByPageFromContest(@Param("start") int start, @Param("end") int end);

    /**
     * 按页码获取比赛集
     *
     * @param start 起始
     * @param end   结束
     * @return 比赛集
     */
    @Select("select * from contest limit #{start},#{end} ")
    List<Contest> findContestsByPageFromContest(@Param("start") int start, @Param("end") int end);


    /**
     * 按页码获取指定用户的比赛集
     *
     * @param start   起始
     * @param end     结束
     * @param user_id 用户Id
     * @return 比赛集
     */
    @Select("select * from contest where user_id = #{user_id} limit #{start},#{end}")
    List<Contest> findContestsByUserIdAndPageFromContest(@Param("start") int start, @Param("end") int end, @Param("user_id") Integer user_id);

    /**
     * 按Id获取比赛信息
     *
     * @param contest_id 比赛号
     * @return 比赛实体
     */
    @Select("select * from contest where contest_id = #{contest_id}")
    Contest findContestByContestIdFromContest(@Param("contest_id") int contest_id);


    /**
     * 根据页码和指定列查询比赛
     *
     * @param start   起始
     * @param end     结束
     * @param orderBy 指定列
     * @param type    升降序
     * @return 比赛集
     */
    @Select("select * from contest order by ${orderBy} ${type} limit #{start} ,#{end}")
    List<Contest> findContestsByPageOrderByTypeAndSortFromContest(@Param("start") int start, @Param("end") int end, @Param("orderBy") String orderBy, @Param("type") String type);

    /**
     * 根据页码和指定列查询指定用户创建的比赛
     *
     * @param start   起始
     * @param end     结束
     * @param orderBy 指定列
     * @param type    升降序
     * @param user_id 用户Id
     * @return 比赛集
     */
    @Select("select * from contest order by ${orderBy} ${type} where user_id = #{user_id} limit #{start} ,#{end}")
    List<Contest> findContestsByPageAndCreatorUserIdOrderByTypeAndSortFromContest(@Param("start") int start, @Param("end") int end, @Param("orderBy") String orderBy, @Param("type") String type, @Param("user_id") Integer user_id);

    /**
     * 管理员插入比赛
     *
     * @param contest 比赛实体
     * @return 影响行数
     */
    @Insert("insert into contest (title,start_time,end_time,privates,organizer,access,describes,user_id,max) values (#{title},#{start_time},#{end_time},#{privates},#{organizer},#{access},#{describes},#{user_id},#{max})")
    int insertContest(Contest contest);


    /**
     * 获取竞赛剩余可加入人数
     *
     * @param contest_id 竞赛号
     * @return 剩余数量
     */
    @Select("select max-now from contest  where contest_id = #{contest_id}")
    int findRestNumFromContestByContestId(@Param("contest_id") Integer contest_id);


    /**
     * 竞赛人数-N
     *
     * @param contest_id 竞赛号
     * @param num        减少人数
     * @return 影响行数
     */
    @Update("update contest set now=now -${num} where contest_id = #{contest_id}")
    int updateContestNowDecreaseFromContestByContestId(@Param("contest_id") Integer contest_id, @Param("num") int num);

    /**
     * 竞赛参加人数+N
     *
     * @param contest_id 竞赛Id
     * @param num        增加人数
     * @return 影响行数
     */
    @Update("update contest set now=now +${num} where contest_id = #{contest_id}")
    int updateContestNowIncreaseFromContestByContestId(@Param("contest_id") Integer contest_id, @Param("num") int num);

    /**
     * 根据比赛号删除指定比赛
     *
     * @param contest_id 比赛号
     * @return 影响行数
     */
    @Delete("delete from contest where contest_id = #{contest_id}")
    int delContestFromContestByContestId(Integer contest_id);
}
