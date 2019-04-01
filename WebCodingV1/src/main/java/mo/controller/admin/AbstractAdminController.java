package mo.controller.admin;

import mo.utils.JWTUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AbstractAdminController {
    /**
     * 获取jwt中的jti(user_id)
     *
     * @return user_id
     */
    public Integer getJWTUserId() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String jws = attributes.getRequest().getHeader("jwt");
        Integer user_id = Integer.valueOf((String) JWTUtils.getBodyValue(jws, "jti"));
        return user_id;
    }
}
