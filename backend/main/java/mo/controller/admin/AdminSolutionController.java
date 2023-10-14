package mo.controller.admin;

import mo.core.Result;

public interface AdminSolutionController {
    /**
     * 查询指定竞赛的提交
     *
     * @param contest_id 竞赛Id
     * @param page       页码
     * @param per_page   每页数量
     * @return
     */
    Result solutions(Integer contest_id, String page, String per_page);
}
