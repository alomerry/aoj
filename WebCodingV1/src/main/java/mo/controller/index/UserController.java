package mo.controller.index;

import mo.core.Result;
import mo.entity.po.main.User;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

public interface UserController {
    /**
     * 用户登录
     *
     * @param user    用户实体
     * @param request request请求
     * @return json结果
     */
    Result login(User user, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param user
     * @param sessionStatus
     * @return
     */
    Result logout(User user, SessionStatus sessionStatus);

    /**
     * 查询用户给
     *
     * @param username 用户名
     * @return
     */
    Result user(String username);

    /**
     * 用户注册
     *
     * @param user 用户实体
     * @return
     */
    Result register(User user, HttpServletRequest request);

}
