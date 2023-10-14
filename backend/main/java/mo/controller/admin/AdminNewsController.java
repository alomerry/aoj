package mo.controller.admin;

import mo.core.Result;
import mo.entity.po.main.News;

public interface AdminNewsController {
    /**
     * 创建新闻
     *
     * @param news 新闻实体json
     * @return http状态码
     */
    Result createNews(String news);

    /**
     * 查询新闻
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    Result getNews(String page, String per_page);

    /**
     * 修改新闻
     *
     * @param news 新闻实体json
     * @return http状态码
     */
    Result updateNews(News news);


    /**
     * 删除指定公告
     *
     * @param news_id 公告Id
     * @return http状态码
     */
    Result deleteNews(Integer news_id);

    /**
     * 查询指定竞赛公告
     *
     * @param contest_id 竞赛Id
     * @param page       页码
     * @param per_page   每页数量
     * @return
     */
    Result getNews(Integer contest_id, Integer page, Integer per_page);

    /**
     * 更新公告公开状态
     *
     * @param news_id 公告Id
     * @param state   公开状态
     * @return
     */
    Result newsDisableState(Integer news_id, String state);
}
