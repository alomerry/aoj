package mo.controller.admin;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;

public interface AdminHomeController {
    /**
     * 后台主页面
     *
     * @param map  数据载体
     * @param user 用户实体
     * @return 相对路径
     */
    String home(ModelMap map, User user);
}
