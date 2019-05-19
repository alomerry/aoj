package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.admin.AdminContestController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Contest;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestService;
import mo.service.PrivilegeService;
import mo.service.ProblemService;
import mo.service.UserService;
import mo.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;


import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;
import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@RestController
public class AdminContestControllerImpl extends AbstractController implements AdminContestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminContestControllerImpl.class);

    @Resource
    private ContestService contestService;

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Resource
    private ProblemService problemService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contests", method = RequestMethod.GET)
    public Result contest(@RequestParam(name = "page", defaultValue = "1") String page,
                          @RequestParam(name = "per_page", defaultValue = "10") String per_page) {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        User user = (User) attributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_UER);
        Privilege privilege = (Privilege) attributes.getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_GROUP);

        logger.info("user[{}]\nprivilege[{}]", user, privilege);

        if (user == null || privilege == null) {
            logger.info("用户未登录，将使用JWT验证");
            String jws = attributes.getRequest().getHeader("jwt");
            Integer user_id = Integer.valueOf((String) JWTUtils.getBodyValue(jws, "jti"));
            logger.info("jws[{}],jti[{}]", jws, user_id);
            user = userService.findUserByUserId(user_id);
            privilege = privilegeService.findPrivilegeByUserId(user_id);
        }

        JSONObject contests = new JSONObject();
        contests.put("contests", contestService.findContestAndCreatorByPageFromAdminPrivilege(
                Integer.valueOf(page), Integer.valueOf(per_page), privilege.getRightstr(), user.getUser_id()));
        return new Result().setData(contests).setCode(ResultCode.OK);
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/problem/{problem_id}", method = RequestMethod.POST)
    public Result addProblem(@PathVariable String problem_id,
                             @PathVariable String contest_id) {
        Integer user_id = getJWTUserId(), problemId = Integer.valueOf(problem_id);
        //判断user是否有权限编辑contest_id;
        if (problemService.isDisabledProblem(problemId)) {//判断题目是否被屏蔽
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("该题目被屏蔽");
        } else if (problemService.isAbsolutePrivateProblem(problemId)
                && problemService.isProblemCreator(problemId, user_id)) {//判断该题目是否为绝对隐私(并且非创建者编辑)
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("该题目无法访问");
        }
        if (!contestService.hasAccess(user_id, Integer.valueOf(contest_id))) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("无操作权限");
        }
        if (contestService.addProblemToContest(problemId, Integer.valueOf(contest_id)) > 0) {
            return new Result().setCode(ResultCode.OK);
        } else {
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("添加题目失败,可能原因：1.题目已存在；2.内部错误");//0/-1
        }
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/problem/{problem_id}", method = RequestMethod.DELETE)
    public Result deleteProblem(@PathVariable String problem_id,
                                @PathVariable String contest_id) {

        //获取jwt中的用户Id
        Integer user_id = getJWTUserId();
        if (!contestService.hasAccess(user_id, Integer.valueOf(contest_id))) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("无操作权限");
        }
        //删除题目
        if (contestService.deleteProblemFromContest(Integer.valueOf(problem_id), Integer.valueOf(contest_id)) > 0) {
            return new Result().setCode(ResultCode.OK);
        } else {
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("删除题目失败，可能原因：1.题目不存在；2.内部错误");//0/-1
        }

    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest", method = RequestMethod.POST)
    public Result contest(@RequestBody Contest contest) {
        logger.info("创建竞赛[{}]", contest);
        if (contestService.createNewContest(contest, getJWTUserId())) {
            return new Result().setCode(ResultCode.OK);
        }
        return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("新建竞赛失败");
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}", method = RequestMethod.GET)
    public Result contest(@PathVariable Integer contest_id) {
        Privilege privilege = privilegeService.findPrivilegeByUserId(getJWTUserId());
        Contest contest = contestService.findContestByContestId(contest_id);
        if (contest == null) {
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("竞赛不存在!");
        } else if (getJWTUserId() != contest.getUser_id() && !PermissionManager.isLegalAdmin(Permission.Contest_organizer, privilege.getRightstr())) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足!");
        } else {
            JSONObject res = new JSONObject();
            res.put("contest", contest);
            return new Result().setCode(ResultCode.OK).setData(res);
        }
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}", method = RequestMethod.PUT)
    public Result contest(@RequestBody Contest contest, @PathVariable Integer contest_id) {
        Privilege privilege = privilegeService.findPrivilegeByUserId(getJWTUserId());
        Integer creator = contestService.findContestByContestId(contest_id).getUser_id();
        if (getJWTUserId() != creator && !PermissionManager.isLegalAdmin(Permission.Contest_organizer, privilege.getRightstr())) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足!");
        } else {
            if (contestService.updateContestByContestId(contest, contest_id)) {
                return new Result().setCode(ResultCode.OK);
            } else {
                return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("更新失败!");
            }
        }
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/creator/contest", method = RequestMethod.GET)
    public Result contests(@RequestParam(name = "page", defaultValue = "1") String page,
                           @RequestParam(name = "per_page", defaultValue = "10") String per_page) {
        JSONObject contests = new JSONObject();
        contests.put("contests", contestService.findContestsByCreatorIdAndPage(getJWTUserId(), Integer.valueOf(page), Integer.valueOf(per_page)));
        contests.put("total", contestService.findCreatorContestTotalNumber(getJWTUserId()));
        return new Result().setCode(ResultCode.OK).setData(contests);
    }
}
