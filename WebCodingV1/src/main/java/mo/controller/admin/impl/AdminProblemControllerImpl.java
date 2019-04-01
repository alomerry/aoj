package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.admin.AdminProblemController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
import mo.entity.po.Problem;
import mo.entity.po.User;
import mo.entity.vo.ProblemLink;
import mo.entity.vo.UserLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ProblemService;
import mo.service.UserService;
import mo.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;
import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@RestController
public class AdminProblemControllerImpl implements AdminProblemController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProblemControllerImpl.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        logger.info("resType[{}],page[{}],per_page[{}]", resType, page, per_page);
        UserLink userLink = userService.findUserLinkByUserId(Integer.valueOf((String) JWTUtils.getBodyValue((((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("jwt")), "jti")));
        logger.info("userLink[{}]", userLink);
        String level = userLink.getPrivilege().getRightstr();
        String defunct = PermissionManager.isLegalAdmin(Permission.Topic_adder, level) ? "(0,1,2,3)" : (PermissionManager.isAdmin(level) ? "(1,2)" : "(1)");
        JSONObject problems = new JSONObject();
        switch (resType) {
            case "simple": {
                problems.put("problems", problemService.findSimpleProblemLinksByDefunct(defunct, userLink.getUser().getUser_id(), Integer.valueOf(page), Integer.valueOf(per_page)));
                break;
            }
            case "detail": {
                problems.put("problems", problemService.findProblemsByPageAndPerPage(defunct, userLink.getUser().getUser_id(), Integer.valueOf(page), Integer.valueOf(per_page)));
                break;
            }
        }
        return new Result().setCode(ResultCode.OK).setData(problems);
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                           @PathVariable Integer contest_id) {
        JSONObject problems = new JSONObject();
        List<ProblemLink> list;
        switch (resType) {
            case "simple": {
                list = problemService.findSimpleProblemsByPageAndContestId(Integer.valueOf(page), Integer.valueOf(per_page), contest_id);
                problems.put("problems", list);
                break;
            }
            case "detail": {
                break;
            }
        }
        return new Result().setCode(ResultCode.OK).setData(problems);
    }
}
