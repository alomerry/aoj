package mo.service;

import mo.entity.po.main.News;
import mo.entity.vo.link.NewsUserLink;

import java.util.List;

public interface NewsService {
    /**
     * 创建新闻
     *
     * @param news    新闻实体
     * @param user_id 创建者Id
     * @return 创建结果
     */
    boolean createNews(News news, Integer user_id);

    /**
     * 创建新闻
     *
     * @param news 新闻实体
     * @return 修改结果
     */
    boolean updateNews(News news);

    /**
     * 查询题目
     *
     * @param user_id  创建者Id
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<NewsUserLink> findNews(Integer user_id, int page, int per_page);

    /**
     * 查询公告
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<NewsUserLink> findNews(int page, int per_page);

    /**
     * 查询公告数量
     *
     * @return
     */
    int findNewsTotalNumber();

    /**
     * 查询创建者的公告数量
     *
     * @param userId 创建者Id
     * @return
     */
    int findNewsTotalNumberByUserId(Integer userId);

    /**
     * 根据竞赛Id查询公告
     *
     * @param contest_id 竞赛Id
     * @param page       页码
     * @param per_page   每页数量
     * @return
     */
    List<NewsUserLink> findNewsByContestId(Integer contest_id, int page, int per_page);

    /**
     * 根据竞赛Id和公开级别查询公告
     *
     * @param contest_id 竞赛Id
     * @param page       页码
     * @param per_page   每页数量
     * @param defunct    公开级别
     * @return
     */
    List<NewsUserLink> findNewsByContestIdAndDefunct(Integer contest_id, int page, int per_page, String defunct);

    /**
     * 删除指定公告
     *
     * @param news_id 公告Id
     * @return 影响行数
     */
    boolean deleteNews(Integer news_id);

    /**
     * 查找指定公告
     *
     * @param news_id 公告Id
     * @return 公告
     */
    News findNews(Integer news_id);

    /**
     * 根据页码和公开级别查询公告
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param defunct  公开级别
     * @return
     */
    List<NewsUserLink> findNewsByPageAndDefunct(int page, int per_page, String defunct);

    /**
     * 更新公告公开级别
     *
     * @param newsId 公告Id
     * @param state  装填
     * @return
     */
    boolean updateNewsDefunct(int newsId, String state);

    /**
     * 查询新闻数量
     *
     * @return
     */
    Integer getPublicNewsTotalNum();

    /**
     * 查询指定竞赛的公告数量
     *
     * @param contest_id 竞赛Id
     * @return
     */
    Integer getContestNewsTotalNum(int contest_id);
}
