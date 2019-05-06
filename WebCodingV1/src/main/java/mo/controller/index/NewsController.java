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

    /**
     * 查询指定竞赛的新闻
     *
     * @param contestId 竞赛Id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    Result getNews(Integer contestId, String page, String per_page);
}
