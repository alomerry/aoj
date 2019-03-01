package mo.controller.index;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;

public interface SolutionController {
    /**
     * 根据条件查找指定solution
     * @param user 用户id
     * @param page 指定页数
     * @param map 返回视图时数据载体
     */
    String listUserAll(User user, int page, ModelMap map);

    /**
     * 根据页码查询solution
     * @param page 指定页数
     * @param map 返回视图时数据载体
     */
    String listAll(int page, ModelMap map);
}
