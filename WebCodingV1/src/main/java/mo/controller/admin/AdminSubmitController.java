package mo.controller.admin;

import mo.core.Result;

import java.util.Map;

public interface AdminSubmitController {

    /**
     * 提交代码
     *
     * @param submit
     * @return
     */
    Result insertSubmit(Map<String, Object> submit);
}
