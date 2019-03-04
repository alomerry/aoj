package mo.aop;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.utils.JWTUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.logging.Level;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;
import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@Aspect
@Component
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthCheck.class);

    @Around("@annotation(mo.interceptor.annotation.AuthCheck)")
    public Object checkAuthCheck(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> clasz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        logger.info("拦截方法[{}]成功", methodName);
        Class[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        Method method = clasz.getMethod(methodName, argClass);

        AuthCheck acer = null;

        if (method.isAnnotationPresent(AuthCheck.class)) {
            acer = method.getAnnotation(AuthCheck.class);
            Result res = checkAuth(acer);
            logger.info("result[{}]", res);

            if (res != null) {
                return res;
            }
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("内部错误");
    }

    /**
     * @param acer 权限要求注解实体
     * @return Result结果
     */
    private Result checkAuth(AuthCheck acer) {
        Result result = null;
        RequiredType[] requiredTypes = acer.value();
        for (RequiredType type : requiredTypes) {
            logger.debug("注解要求检测[{}]权限", type);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                logger.info("ServletRequestAttributes空指正");
                return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("Session错误");
            }
            switch (type) {
                case ADMIN: {
                    String level = (String) JWTUtils.getBodyValue((requestAttributes.getRequest().getHeader("jwt")), "level");
                    if (!PermissionManager.isAdmin(level)) {
                        result = new Result();
                        result.setCode(ResultCode.FORBIDDEN).setMessage("权限不足");
                        return result;
                    }
                    break;
                }
                case LOGIN: {
                /*从session里面获取对应的值
                logger.info("session-v1:[{}]", requestAttributes.getAttribute(ONLINEJUDGE_SESSION_UER, RequestAttributes.SCOPE_SESSION));
                logger.info("session-v2:[{}]", requestAttributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_UER));*/
                    User user = null;
                    user = (User) requestAttributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_UER);
                    logger.info("request:session:key[{}]:values[{}]", ONLINEJUDGE_SESSION_UER, user);
                    if (user == null) {
                        result = new Result();
                        result.setCode(ResultCode.UNAUTHORIZED).setMessage("请登录后操作");
                        return result;
                    } else {
                        return null;
                    }
                }
                case JWT: {
                    try {
                        if (!JWTUtils.checkLegal(requestAttributes.getRequest().getHeader("jwt"))) {
                            throw new SignatureException("jwt签名不匹配");
                        } else {
                            logger.info("JWT验证成功");
                            break;
                        }
                    } catch (ExpiredJwtException e) {
                        e.printStackTrace();
                        logger.warn("签名认证失败,token过期");
                        User user = (User) requestAttributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_UER);
                        Privilege privilege = (Privilege) requestAttributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_GROUP);
                        result = new Result();
                        if (user != null) {
                            JSONObject object = new JSONObject();
                            object.put("token", JWTUtils.makeToken(new UserLink(user, privilege), 20));
                            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败,签名过期").setData(object);
                        } else {
                            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败,签名过期");
                        }

                        return result;
                    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                        //签名算法不支持//失去载体//jwt签名不匹配
                        logger.warn("签名认证失败,错误原因可能为:签名算法不支持,失去载体,签名不匹配");
                        e.printStackTrace();
                        result = new Result();
                        result.setCode(ResultCode.FORBIDDEN).setMessage("签名认证失败,签名不匹配");
                        return result;
                    }
                }
            }
        }

        return null;
    }


}
