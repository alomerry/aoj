package mo.controller.index;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserController {
    /**
     * 登录页面
     *
     * @param message 登录错误信息
     * @param map     数据载体
     * @return 跳转路径
     */
    String login(String message, ModelMap map);

    /**
     * 注册页面
     *
     * @param message 注册错误信息
     * @param map     数据载体
     * @return 跳转路径
     */
    String register(String message, ModelMap map);

    /**
     * 注册业务
     *
     * @param data json数据载体
     * @param map  数据载体
     * @return 跳转路径
     */
    String registerDo(Map<String, Object> data, ModelMap map);

    /**
     * 登录业务
     *
     * @param data json数据载体
     * @return 跳转路径
     */
    String loginDo(Map<String, Object> data, ModelMap map, HttpServletRequest request, HttpServletResponse response);

    /**
     * 注销
     *
     * @param sessionStatus session状态
     * @return 跳转路径
     */
    String loginout(SessionStatus sessionStatus);

    /**
     * 获取在线人数
     *
     * @return 返回
     */
    String online_number();

    /**
     * 用户参加的比赛
     *
     * @param user 用户实体
     * @param map  数据载体
     * @return 相对路径
     */
    String ownCompetition(User user, ModelMap map);
}
