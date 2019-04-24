package mo.controller.impl;

import mo.controller.AbstractController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.User;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.UserService;
import mo.utils.FileUtils;
import mo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

@RestController
public class TestController extends AbstractController {

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


    @ResponseBody
    @RequestMapping(value = "/testcase", method = RequestMethod.POST)
    public Result testFile() {
        String dirName = "" + System.currentTimeMillis() + StringUtils.generateString(6);
        logger.info("测试上传路径A[{}]", getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + dirName);

        File problemCase = new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + "123");
        if (problemCase.exists()) {
            problemCase.renameTo(new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + "456"));
        } else {
            logger.info("文件夹不存在");
        }
        return new Result().setCode(ResultCode.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/test/unzip", method = RequestMethod.POST)
    public Result testFileUnzip() {

        return new Result().setCode(ResultCode.OK);
    }
}
