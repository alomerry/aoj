package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.admin.AbstractAdminController;
import mo.controller.admin.AdminUserController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
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
}
