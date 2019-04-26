package mo.service;

import mo.entity.po.News;

public interface NewsService {
    /**
     * 创建新闻
     *
     * @param news    新闻实体
     * @param user_id 创建者Id
     * @return 创建结果
     */
    boolean createNews(News news, Integer user_id);
}
