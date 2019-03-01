package mo.interceptor;

import mo.entity.po.Privilege;
import mo.utils.string.StringValue;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminIntercepter implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AdminIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (checkURL(request)) {
            logger.info("url检测完毕，需要[{}]级别，开始判断用户级别", StringValue.USER_GROUP_ADMIN);
            Privilege res = (Privilege) request.getSession().getAttribute(StringValue.ONLINEJUDGE_SESSION_GROUP);
            logger.info("用户级别为[{}]", res.getRightstr());
            if (res.getRightstr().startsWith(StringValue.USER_GROUP_ADMIN)) {
                logger.info("用户级别判断完毕，继续执行");
                return true;
            } else {
                logger.info("用户级别判断完毕，级别不足，即将跳转至错误页面");
                throw new PersistenceException(StringValue.Exception_PermissionDenied);
            }
        } else {
            return true;
        }
    }


    /**
     * 判断url是否是admin级别
     *
     * @param request request
     * @return 若真则是admin级别
     */
    private boolean checkURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (!url.contains("/assets") || !url.contains("ace")) {
            logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(), request.getRequestURI());
        }
        return url.startsWith("/" + StringValue.USER_GROUP_ADMIN);
    }
}
