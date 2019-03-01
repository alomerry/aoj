package mo.listener;

import mo.entity.po.User;
import mo.utils.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    private Logger logger = LoggerFactory.getLogger(SessionAttributeListener.class);

    @Resource
    private SessionListener sessionListener;

    private Map<String, String> session_users;

    public SessionAttributeListener() {
        logger.warn("默认构造函数调用，开始监听Session");
        session_users = new ConcurrentHashMap<>();
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        logger.info("attributeAdded函数调用，Session 添加了属性[{}]", se.getName());
        onlineCheck(se);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        logger.info("attributeRemoved函数调用，Session 删除了属性[{}]", se.getName());

        removeLoginSession(se);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        logger.info("attributeReplaced函数调用，Session 替换了属性[{}]", se.getName());
    }

    /**
     * 判断用户是否在其他地登录
     */
    private void onlineCheck(HttpSessionBindingEvent se) {
        //更新的属性为登录用户
        if (StringValue.ONLINEJUDGE_SESSION_UER.equals(se.getName())) {
            if (session_users.containsKey(((User) se.getValue()).getUsername())) {
                if (!session_users.get(((User) se.getValue()).getUsername()).equals(se.getSession().getId())) {
                    logger.warn("用户[{}]已在其他地登录，将注销该用户", ((User) se.getValue()).getUsername());
                    sessionListener.sessionRemoveAttribute(session_users.get(((User) se.getValue()).getUsername()), se.getName());
                }
            } else {
                session_users.put(((User) se.getValue()).getUsername(), se.getSession().getId());
            }
        }
    }

    private void removeLoginSession(HttpSessionBindingEvent se) {
        if (StringValue.ONLINEJUDGE_SESSION_UER.equals(se.getName())) {
            session_users.remove(((User) se.getValue()).getUsername());
        }
    }
}
