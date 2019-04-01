package mo.dao;

import mo.entity.po.Contest;
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
    @Select("select * from contest where privates in ${privates}")
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

}
