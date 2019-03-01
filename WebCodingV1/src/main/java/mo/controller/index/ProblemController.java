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
     * @return 第一页题目
     */
    Result problems();
}
