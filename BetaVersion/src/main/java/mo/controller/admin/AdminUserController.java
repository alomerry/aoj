package mo.controller.admin;

import mo.entity.po.Privilege;
import mo.entity.po.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface AdminUserController {

    /**
     * 用户管理-用户列表
     *
     * @param map  数据载体
     * @param page 页码
     * @return 返回页面路径
     */
    String userList(ModelMap map, int page);

    /**
     * 用户信息修改
     *
     * @param map    数据载体
     * @param userId 用户Id
     * @param msg    上次修改消息
     * @return 返回页面路径
     */
    String userEdit(ModelMap map, Integer userId, String msg);

    /**
     * 用户信息确认修改请求
     *
     * @param user      用户实体
     * @param privilege 用户权限
     * @param map       数据载体
     * @return 结果页面路径
     */
    String userDoEdit(User user, Privilege privilege, RedirectAttributes map);

    /**
     * 管理确认删除指定用户
     * 前端发送ajax异步post请求，将需要删除的用户Id封装成json数据
     *
     * @param data json数据map
     * @return json数据：操作结果
     */
    String userDoDel(Map<String, Object> data);
}
