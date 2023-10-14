package mo.dao.main;

import mo.entity.po.main.Contest;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ContestMapper {

    /**
     * 根据页码查询比赛集
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return 比赛集
     */
    @Select("select * from contest limit #{start},#{per_page}")
    List<Contest> findContestByPageAndPerPage(@Param("start") Integer start, @Param("per_page") Integer per_page);

    /**
     * 根据公开级别查询比赛集
     *
     * @param start    起始页
     * @param per_page 每页数量
     * @param privates 公开级别
     * @return 比赛集
     */
    @Select("select * from contest where privates in ${privates} limit  #{start},#{per_page}")
    List<Contest> findContestsByPageAndDefunct(@Param("start") Integer start, @Param("per_page") Integer per_page, @Param("privates") String privates);

    /**
     * 根据公开级别查询比赛集（包含创建比赛者）
     *
     * @param start    起始
     * @param per_page 每页数量
     * @param privates 公开级别
     * @param userId   创建者Id
     * @return 比赛集
     */
    @Select("select * from contest where user_id = #{user_id} and privates in ${privates} limit #{start},#{per_page}")
    List<Contest> findContestByPageAndDefunctWithOwnContest(@Param("start") Integer start,
                                                            @Param("per_page") Integer per_page,
                                                            @Param("privates") String privates,
                                                            @Param("user_id") Integer userId);

    /**
     * 根据竞赛Id查询创建者
     *
     * @param contest_id 竞赛Id
     * @return 创建者Id
     */
    @Select("select user_id from contest where contest_id = #{contest_id}")
    Integer findCreatorByContestId(@Param("contest_id") Integer contest_id);

    /**
     * 查找竞赛中是否包含指定题目
     *
     * @param contest_id 竞赛Id
     * @param problem_id 题目Id
     * @return 是否包含
     */
    @Select("select contest_id from contest where contest_id = #{contest_id} and problem_id =#{problem_id}")
    Integer findProblemFromContestByProblemIdAndContestId(@Param("contest_id") Integer contest_id, @Param("problem_id") Integer problem_id);

    /**
     * 插入新竞赛
     *
     * @param contest 竞赛实体
     * @param user_id 创建者Id
     * @return
     */
    @Insert("insert into contest (title,access,user_id, start_at,end_at,describes,max,organizer) values " +
            "(#{contest.title},#{contest.access},#{user_id},#{contest.start_at},#{contest.end_at},#{contest.describes},#{contest.max},#{contest.organizer}) ")
    int insertNewContest(@Param("contest") Contest contest, @Param("user_id") Integer user_id);

    /**
     * 根据竞赛Id查询竞赛
     *
     * @param contest_id 竞赛Id
     * @return 竞赛实体
     */
    @Select("select * from contest where contest_id = #{contest_id}")
    Contest findContestByContestId(@Param("contest_id") int contest_id);

    /**
     * 更新竞赛
     *
     * @param contest    竞赛信息
     * @param contest_id 竞赛Id
     * @return
     */
    @Update("update contest set title=#{contest.title},access=#{contest.access}, describes=#{contest.describes},start_at=#{contest.start_at},end_at=#{contest.end_at}," +
            "privates=#{contest.privates},organizer=#{contest.organizer},max=#{contest.max} where contest_id = #{contest_id}")
    int updateContestByContestId(@Param("contest") Contest contest, @Param("contest_id") Integer contest_id);

    /**
     * 查询指定用户创建的竞赛
     *
     * @param user_id  创建者Id
     * @param start    起始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from contest where user_id = #{user_id} limit #{start} ,#{per_page}")
    List<Contest> findContestsByCreatorIdAndPage(@Param("user_id") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 根据用户Id和竞赛Id查询竞赛
     *
     * @param user_id    用户Id
     * @param contest_id 竞赛Id
     * @return
     */
    @Select("select contest_id from contest where user_id = #{user_id} and contest_id = #{contest_id}")
    int findContestByContestIdAndCreatorId(@Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 给指定竞赛添加一个参赛人员
     *
     * @param contest_id 竞赛Id
     * @return
     */
    @Update("update contest set now = now+1 where contest_id = #{contest_id}")
    int addContestNowNumberByContestId(@Param("contest_id") Integer contest_id);

    /**
     * 给指定竞赛减少一个参赛人员
     *
     * @param contest_id 竞赛Id
     * @return
     */
    @Update("update contest set now = now-1 where contest_id = #{contest_id}")
    int delContestNowNumberByContestId(@Param("contest_id") Integer contest_id);

    /**
     * 查询指定公开级别的竞赛数量
     *
     * @param privates 公开级别
     * @return
     */
    @Select("select count(contest_id) from contest where privates = #{privates}")
    Integer findContestTotalNumberByDefunct(@Param("privates") int privates);

    /**
     * 查询指定用户创建的竞赛数量
     *
     * @param user_id 用户Id
     * @return
     */
    @Select("select count(contest_id) from contest where user_id = #{user_id}")
    Integer findUserContestTotalNumber(@Param("user_id") Integer user_id);
}
