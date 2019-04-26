package mo.controller.admin;

import mo.core.Result;

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
     * 创建新闻
     *
     * @param news 新闻实体json
     * @return http状态码
     */
    Result updateNews(String news);
}
