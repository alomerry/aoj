package mo.dao;

import mo.entity.po.News;
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
    @Insert("insert into news (user_id,title,content,update_time,create_at,defunct) values " +
            "(#{user_id},#{news.title},#{news.content},#{news.update_time},#{news.create_at},#{news.defunct})")
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
    List<News> findNewsByPageAndCreatorId(@Param("start") int start, @Param("per_page") int per_page);

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
}