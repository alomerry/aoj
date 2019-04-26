package mo.controller.index;

import mo.core.Result;

public interface NewsController {

    /**
     * 查询新闻
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    Result getNews(String page, String per_page);
}
