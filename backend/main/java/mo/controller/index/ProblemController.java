package mo.controller.index;

import mo.core.Result;


public interface ProblemController {

    /**
     * 查询题目集
     *
     * @param page     当前页码
     * @param per_page 每页数量
     * @param resType  请求返回类型
     * @param defunct  公开级别
     * @return json数据
     */
    Result problems(String page, String per_page, String resType, String defunct);

    /**
     * 查询指定题目
     *
     * @param id 题目Id
     * @return 题目
     */
    Result problem(Integer id);

    /**
     * 查询题目
     *
     * @param page     当前页码
     * @param per_page 每页数量
     * @return 第一页题目
     */
    Result problems(String page, String per_page);

    /**
     * 根据Tag查询题目
     *
     * @param tagId    标签Id
     * @param page     当前页码
     * @param per_page 每页数量
     * @param resType  请求返回类型
     * @return 题目集合
     */
    Result problemsByTag(String tagId, String page, String per_page, String resType);

    /**
     * 查询竞赛中的题目
     *
     * @param contestId 竞赛Id
     * @return
     */
    Result contestProblems(Integer contestId);

    /**
     * 查询指定竞赛的提交信息
     *
     * @param problemId 题目Id
     * @return
     */
    Result problemSubmitDetail(Integer problemId);

    /**
     * 根据题目标题查找相似提u
     * @param title 标题
     * @return
     */
    Result searchProblemBySimilarProblemTitle(String title);
}
