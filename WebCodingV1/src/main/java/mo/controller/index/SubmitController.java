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
}
