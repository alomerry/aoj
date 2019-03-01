package mo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class SessionsManager {

    private Logger logger = LoggerFactory.getLogger(SessionsManager.class);

    /**
     * String SessionId
     * HttpSession session
     */
    Map<String, HttpSession> sessions;

    protected void overSession(String sessionId) {
        sessions.get(sessionId).invalidate();
    }

    protected Object sessionAttribute(String sessionId, String filed) {
        return sessions.get(sessionId).getAttribute(filed);
    }

    protected void sessionRemoveAttribute(String sessionId, String filed) {
        sessions.get(sessionId).removeAttribute(filed);
    }

    public int getOnlineNumber() {
        return sessions.size();
    }

    private void showAllSession() {
        StringBuilder stb = new StringBuilder();
        for (Map.Entry<String, HttpSession> entry : sessions.entrySet()) {
            stb.append(entry.getKey() + ":" + entry.getValue().getId() + "\n");
        }
        logger.info("session列表如下\n[{}]", stb.toString());
    }
}
