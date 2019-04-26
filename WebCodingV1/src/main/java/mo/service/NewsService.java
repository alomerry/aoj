package mo.service;

import mo.entity.po.News;
import mo.entity.vo.NewsUserLink;

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
     * 查询题目
     *
     * @param user_id  创建者Id
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<NewsUserLink> findNews(Integer user_id, int page, int per_page);
}
