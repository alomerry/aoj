package mo.controller.index;

import mo.core.Result;

public interface ContestController {
    /**
     * 查询竞赛
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    Result contests(String page, String per_page);
}
