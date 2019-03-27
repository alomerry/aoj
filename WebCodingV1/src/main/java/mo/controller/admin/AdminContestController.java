package mo.controller.admin;

import mo.core.Result;

public interface AdminContestController {

    /**
     * 查询竞赛集合
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return 竞赛集合
     */
    Result Contest(String page, String per_page);
}
