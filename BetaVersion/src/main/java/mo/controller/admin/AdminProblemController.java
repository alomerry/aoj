package mo.controller.admin;

import mo.entity.po.User;
import mo.utils.DIYMessage;
import org.springframework.ui.ModelMap;

import java.util.Map;

public interface AdminProblemController {

    /**
     * ajax异步查询题目集
     *
     * @param data 前端数据载体，包含key defunct
     * @param user 用户实体
     * @return json数据
     */
    String getProblems(Map<String, Object> data, User user);

    String problemList(ModelMap map, int page, String type, String sort);

    String problemDoDelete(Map<String,Object> data);

    String addProblem(ModelMap map, DIYMessage msg);
}
