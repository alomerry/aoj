package mo.controller.index;

import mo.core.Result;

public interface TagController {

    /**
     * 根据题目Id查找标签集
     *
     * @param problem_id 题目Id
     * @return 标签集
     */
    Result tags(Integer problem_id);

    /**
     * 查询标签
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    Result tags(int page, int per_page);
}
