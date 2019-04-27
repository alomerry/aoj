package mo.service;

import mo.entity.po.News;
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
     * 查询题目
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<NewsUserLink> findNews(int page, int per_page);

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
}
