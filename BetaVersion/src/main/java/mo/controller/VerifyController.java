package mo.controller;

import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface VerifyController {
    /**
     *
     * @param request
     * @param response
     */
    void getVerify(HttpServletRequest request, HttpServletResponse response);

    /**
     *
     * @param data
     * @param session
     * @return
     */
    boolean checkVerify(@RequestBody Map<String, Object> data, HttpSession session);

    /**
     *
     * @param msg 错误信息
     * @param reason 错误原因
     * @param map 数据载体
     * @return
     */
    //String toError(String msg,String reason,ModelMap map);
}
