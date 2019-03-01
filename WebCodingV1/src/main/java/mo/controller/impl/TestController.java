package mo.controller.impl;

import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.User;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    @AuthCheck(RequiredType.JWT)
    public Result testLogin(User obj) {
        logger.info("检测登录信息中...username[{}]:pwd[{}]", obj.getUsername(), obj.getPasswd());
//        return userService.checkLogin(obj.getUsername(), obj.getPasswd());
        return new Result().setCode(ResultCode.OK);
    }
}
