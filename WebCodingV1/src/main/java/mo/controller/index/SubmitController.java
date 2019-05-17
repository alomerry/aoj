package mo.controller.index;

import mo.core.Result;

import java.util.Map;

public interface SubmitController {
    /**
     * 提交代码
     *
     * @param submit
     * @return
     */
    Result insertSubmit(Map<String, Object> submit);

    /**
     * 查询solutions
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param myself   所查询的查询对象
     * @return solution集合
     */
    Result getSolutions(String page, String per_page, String myself);

    /**
     * 根据状态查询solutions
     *
     * @param state    提交状态
     * @param page     页码
     * @param per_page 每页数量
     * @param myself   所查询的查询对象
     * @return solution集合
     */
    Result getSolutions(Integer state, String page, String per_page, String myself);

    /**
     * 查询指定竞赛的提交
     *
     * @param contestId 竞赛Id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    Result getSolutions(Integer contestId, String page, String per_page);
}
