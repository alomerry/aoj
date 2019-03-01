package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.admin.AdminUserController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminUserControllerImpl implements AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserControllerImpl.class);

    @Resource
    private UserService userService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Result users(@RequestParam(value = "page", defaultValue = "1") String page,
                        @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject users = new JSONObject();
        users.put("users", userService.findUsersByPageAndPerPage(Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setCode(ResultCode.OK).setData(users);
    }
}
