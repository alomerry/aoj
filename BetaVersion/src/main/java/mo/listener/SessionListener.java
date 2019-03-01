package mo.listener;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class SessionListener extends SessionsManager implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(SessionListener.class);

    private static Long online_number = 0L;

    public SessionListener() {
        logger.info("SessionListener默认构造函数调用");
        sessions = new ConcurrentHashMap<>();
    }

    public void sessionCreated(HttpSessionEvent se) {
        logger.info("检测到session创建，id为[{}]", se.getSession().getId());
        synchronized (sessions) {
            ++online_number;
            sessions.put(se.getSession().getId(), se.getSession());
            logger.info("当前sessions数量" + sessions.size());
        }
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("检测到session:id为[{}]销毁", se.getSession().getId());
        synchronized (sessions) {
            --online_number;
            sessions.remove(se.getSession().getId());
            logger.info("当前sessions数量" + sessions.size());
        }
    }

    public void overSession(String sessionId) {
        super.overSession(sessionId);
    }

    public Object sessionAttribute(String sessionId, String filed) {
        return super.sessionAttribute(sessionId, filed);
    }

    private HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }


    public void sessionRemoveAttribute(String sessionId, String filed) {
        sessions.get(sessionId).removeAttribute(filed);
    }

    public static long getOnline_number() {
        return online_number;
    }
}
