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
}
