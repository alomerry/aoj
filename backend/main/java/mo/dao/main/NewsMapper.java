package mo.dao.main;

import mo.entity.po.main.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    /**
     * 为竞赛添加新闻
     *
     * @param news       新闻实体
     * @param user_id    创建者Id
     * @param contest_id 竞赛Id
     * @return 影响行数
     */
    @Insert("insert into news (user_id,title,content,update_time,create_at,defunct,contest_id) values " +
            "(#{user_id},#{news.title},#{news.content},#{news.update_time},#{news.create_at},#{news.defunct},#{contest_id})")
    int insertNewsBlindToContest(@Param("news") News news, @Param("user_id") Integer user_id, @Param("contest_id") Integer contest_id);

    /**
     * 创建新闻
     *
     * @param news    新闻实体
     * @param user_id 创建者Id
     * @return 影响行数
     */
    @Insert("insert into news (user_id,title,content,update_time,create_at,defunct,contest_id) values " +
            "(#{user_id},#{news.title},#{news.content},#{news.update_time},#{news.create_at},#{news.defunct},#{news.contest_id})")
    int insertNews(@Param("news") News news, @Param("user_id") Integer user_id);

    /**
     * 查询新闻
     *
     * @param user_id  创建者Id
     * @param start    起始
     * @param per_page 查询数量
     * @return
     */
    @Select("select * from news where user_id = #{user_id} limit #{start},#{per_page}")
    List<News> findNewsByPageAndCreatorId(@Param("user_id") Integer user_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 查询新闻
     *
     * @param start    起始
     * @param per_page 查询数量
     * @return
     */
    @Select("select * from news limit #{start},#{per_page}")
    List<News> findNewsByPage(@Param("start") int start, @Param("per_page") int per_page);

    /**
     * 查询指定竞赛的公告
     *
     * @param contest_id 竞赛Id
     * @param start      起始
     * @param per_page   每页数量
     * @return
     */
    @Select("select * from news where contest_id = #{contest_id} limit #{start},#{per_page}")
    List<News> findNewsByContestIdAndPage(@Param("contest_id") int contest_id, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 修改公告
     *
     * @param news 公告实体
     * @return 影响行数
     */
    @Update("update news set title = #{news.title},content=#{news.content},update_time=#{news.update_time},defunct=#{news.defunct} where news_id = #{news.news_id} ")
    int updateNewsByNewsId(@Param("news") News news);

    /**
     * 删除制定公告
     *
     * @param news_id 公告Id
     * @return 影响行数
     */
    @Delete("delete from news where news_id = #{news_id}")
    int deleteNewsByNewsId(@Param("news_id") Integer news_id);

    /**
     * 查找指定公告
     *
     * @param news_id 公告Id
     * @return 公告
     */
    @Select("select * from news where news_id = #{news_id}")
    News findNewsByNewsId(@Param("news_id") Integer news_id);

    /**
     * 根据公开级别查询公告
     *
     * @param defunct  公开级别
     * @param start    起始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from news where defunct = #{defunct} limit #{start},#{per_page}")
    List<News> findNewsByPageAndDefunct(@Param("defunct") String defunct, @Param("start") int start, @Param("per_page") int per_page);

    /**
     * 更新公告的公开级别
     *
     * @param news_id 公告Id
     * @param state   公开级别
     * @return
     */
    @Update("update news set defunct =#{state} where news_id = #{news_id}")
    int updateNewsDefunct(@Param("news_id") int news_id, @Param("state") String state);

    /**
     * 根据公开级别和竞赛Id查询新闻
     *
     * @param contest_id 竞赛Id
     * @param start      起始
     * @param per_page   每页数量
     * @param defunct    公开级别
     * @return
     */
    @Select("select * from news where contest_id = #{contest_id} and defunct = #{defunct}  limit #{start} ,#{per_page}")
    List<News> findNewsByContestIdAndDefunct(@Param("contest_id") int contest_id, @Param("start") int start, @Param("per_page") int per_page, @Param("defunct") String defunct);

    /**
     * 根据公开级别查询指定新闻数量
     *
     * @param defunct 公开级别
     * @return 公告数量
     */
    @Select("select count(news_id) from news where defunct = #{defunct}")
    Integer findNewsTotalNumberByDefunct(@Param("defunct") String defunct);

    /**
     * 根据竞赛Id查询指定新闻数量
     *
     * @param contest_id 竞赛Id
     * @return 公告数量
     */
    @Select("select count(news_id) from news where contest_id = #{contest_id}")
    Integer findContestNewsTotalNumberByContestId(@Param("contest_id") Integer contest_id);

    /**
     * 查询公告数量
     *
     * @return 公告数量
     */
    @Select("select count(news_id) from news")
    int findNewsTotalNumber();

    /**
     * 查询创建者公告数量
     *
     * @param userId 创建者Id
     * @return 公告数量
     */
    @Select("select count(news_id) from news where user_id = #{user_id}")
    int findNewsTotalNumberByUserId(@Param("user_id") Integer userId);

    /**
     * 根据公告Id查询创建者Id
     * @param news_id 公告Id
     * @return 创建者Id
     */
    @Select("select user_id from news where news_id = #{news_id}")
    Integer getNewsCreatorIdByNewsId(@Param("news_id") int news_id);
}