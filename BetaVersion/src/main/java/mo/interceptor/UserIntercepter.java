package mo.interceptor;

import mo.entity.po.User;
import mo.utils.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserIntercepter implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(UserIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        if (checkURL(request)) {
            logger.info("url检测完毕", "该url需要用户登录状态！");
            if (checkLogin(request)) {
                logger.info("用户登录状态检测完毕", "用户已登录！");
                return true;
            } else {
                logger.info("用户登录状态检测完毕", "用户未登录！即将跳转至用户登录页面！");
                request.getRequestDispatcher("/user_login").forward(request, response);
                return false;
            }
        } else {
            return true;
        }
    }


    /**
     * @param request request
     * @return 返回用户是否登录
     */
    private boolean checkLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(StringValue.ONLINEJUDGE_SESSION_UER);
        return user != null && !user.getUser_id().equals(0);
    }

    /**
     * 检测访问的url是否需要用户登录状态
     *
     * @param request 用户请求
     * @return 当需要登录状态会返回真
     */
    private boolean checkURL(HttpServletRequest request) {
        String sf = request.getServletPath();
        if (!sf.contains("/assets") || !sf.contains("ace")) {
            logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(), request.getRequestURI());
        }
        for (String st : StringValue.LOGIN_REQUIRED_URL) {
            if (st.equals(sf)) {
                return true;
            }
        }
        return sf.startsWith("/" + StringValue.USER_GROUP_ADMIN);
    }

}
