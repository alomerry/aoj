package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.admin.AdminContestController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.User;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

import java.util.Objects;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@RestController
public class AdminContestControllerImpl implements AdminContestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminContestControllerImpl.class);

    @Resource
    private ContestService contestService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contests", method = RequestMethod.GET)
    public Result Contest(@RequestParam(name = "page", defaultValue = "1") String page,
                          @RequestParam(name = "per_page", defaultValue = "10") String per_page) {
        User user = (User) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_UER);

        JSONObject contests = new JSONObject();
        contests.put("contests", contestService.findContestsByPageAndPerPage(Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setData(contests).setCode(ResultCode.OK);
    }
}
