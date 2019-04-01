package mo.controller.admin;

import mo.core.Result;

public interface AdminProblemController {
    /**
     * 查询题目集
     *
     * @param resType  结果类型
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    Result problems(String resType, String page, String per_page);

    /**
     * 查询指定比赛题目集
     *
     * @param resType  结果类型
     * @param page       页码
     * @param per_page   每页数量
     * @param contest_id 比赛Id
     * @return 题目集
     */
    Result problems(String resType,String page, String per_page, Integer contest_id);
}
