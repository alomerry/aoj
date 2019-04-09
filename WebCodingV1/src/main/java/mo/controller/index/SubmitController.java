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
     * @return solution集合
     */
    Result getSolutions(String page, String per_page);
}
