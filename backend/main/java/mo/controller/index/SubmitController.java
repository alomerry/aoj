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
     * 重新判题
     * @param solutionId 代码编号
     * @return
     */
    Result rejudge(String solutionId);

    /**
     * 查询指定竞赛的提交
     *
     * @param contestId 竞赛Id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    Result getSolutions(Integer contestId, String page, String per_page);

    /**
     * 查询编译错误信息
     * @param solutionId 提交编号
     * @return
     */
    Result getCompileInfo(String solutionId);

    /**
     * 查询排名
     * @return
     */
    Result getTotalRank(String page,String per_page);
}
