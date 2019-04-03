package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.admin.AbstractAdminController;
import mo.controller.admin.AdminUserController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.PrivilegeService;
import mo.service.UserService;
import mo.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class AdminUserControllerImpl extends AbstractAdminController implements AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserControllerImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public Result users(@RequestParam(value = "page", defaultValue = "1") String page,
                        @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject users = new JSONObject();
        users.put("users", userService.findUsersByPageAndPerPage(Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setCode(ResultCode.OK).setData(users);
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/user/{user_id}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable String user_id) {
        //判断操作者权限,是否是[用户管理员]
        Integer operatorId = getJWTUserId();
        Privilege privilege = privilegeService.findPrivilegeByUserId(operatorId);
        if (privilege != null && PermissionManager.isAdmin(privilege.getRightstr()) && PermissionManager.isLegalAdmin(Permission.User_manager, privilege.getRightstr())) {
            //判断被删除者权限
            privilege = privilegeService.findPrivilegeByUserId(Integer.valueOf(user_id));
            if (privilege != null && PermissionManager.isAdmin(privilege.getRightstr())) {
                return new Result().setCode(ResultCode.FORBIDDEN).setMessage("该用户为管理员，请降级后操作！");
            } else {
                //执行删除
                if (userService.deleteUserByUserId(Integer.valueOf(user_id)) > 0) {
                    return new Result().setCode(ResultCode.OK);
                } else {
                    return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("内部错误！删除失败！");
                }
            }
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足！");
        }
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    public Result disableUser(@PathVariable String user_id,
                              @PathVariable Integer state) {
        //判断是否是 管理员
        Integer operatorId = getJWTUserId();
        Privilege privilege = privilegeService.findPrivilegeByUserId(operatorId);
        if (privilege != null && PermissionManager.isAdmin(privilege.getRightstr())) {
            //TOdo
        }
        return null;
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/user", method = RequestMethod.PUT, consumes = "application/json")
    public Result updateUser(@RequestBody Map<String, String> user) {
        logger.info("修改用户[{}]", user);
        /*
         * 1.nickname
         * 2.level
         * 3.passwd
         * 4.email
         * 5.disabled
         * */
        Integer operatorId = getJWTUserId();
        Privilege privilege = privilegeService.findPrivilegeByUserId(operatorId);
        if (privilege == null || !PermissionManager.isAdmin(privilege.getRightstr())) {
            logger.info("非管理员，无操作权限");
            return new Result().setMessage("非管理员，无操作权限").setCode(ResultCode.FORBIDDEN);
        }
        UserLink userLink = null;
        String level = user.get("level");
        if (user.get("user_id") == null) {
            logger.info("参数错误");
            return new Result().setMessage("参数错误！").setCode(ResultCode.BAD_REQUEST);
        } else if (level != null) {
            userLink = userService.findUserLinkByUserId(Integer.valueOf(user.get("user_id")));
            logger.info("即将修改权限为[{}]", level);
            if (userLink.getPrivilege() == null) {
                logger.info("信息错误，用户不存在");
                return new Result().setMessage("参数错误！不存在该用户").setCode(ResultCode.BAD_REQUEST);
            } else {
                if (PermissionManager.isAllLegalAdmins(PermissionManager.changedLevel(userLink.getPrivilege() == null ? "" : userLink.getPrivilege().getRightstr(), level), privilege.getRightstr())) {
                    //可以修改
                    if (userService.updateUser(user, userLink) > 0) {
                        logger.info("修改成功");
                        return new Result().setCode(ResultCode.OK);
                    } else {
                        logger.info("修改失败");
                        return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("内部错误！");
                    }
                } else {
                    logger.info("权限不足");
                    return new Result().setMessage("权限不足，操作失败!").setCode(ResultCode.FORBIDDEN);
                }
            }
        } else {
            return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("内部错误！");
        }
    }
}
