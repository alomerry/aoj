package mo.controller.index;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;

public interface HomeController {
    /**
     * 主页面
     *
     * @param user     用户
     * @param modelMap 数据载体
     * @return 相对路径
     */
    String home(User user, ModelMap modelMap);
}
