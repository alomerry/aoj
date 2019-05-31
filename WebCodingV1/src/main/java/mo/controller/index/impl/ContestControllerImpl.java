package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.index.ContestController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.vo.link.ContestLinkUser;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestApplyService;
import mo.service.ContestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ContestControllerImpl extends AbstractController implements ContestController {

    @Resource
    private ContestService contestService;

    @Resource
    private ContestApplyService contestApplyService;

    @Override
    @ResponseBody
    @RequestMapping(value = "/contests", method = RequestMethod.GET)
    public Result contests(@RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        //todo 查询到公开竞赛和自己参加的隐私竞赛
        JSONObject contests = new JSONObject();
        contests.put("contests", contestService.findContestsByPageAndDefunct(Integer.valueOf(page), Integer.valueOf(per_page), new Integer[]{1}));
        contests.put("total", contestService.findPublicContestTotalNumber());
        return new Result().setCode(ResultCode.OK).setData(contests);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/contest/{contestId}", method = RequestMethod.GET)
    public Result contest(@PathVariable Integer contestId) {
        JSONObject contests = new JSONObject();
        ContestLinkUser res = contestService.findContestUserByContestId(contestId);
        if (res.getContest().getPrivates() == 0) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("竞赛信息无法访问!");
        }
        contests.put("contest", res);
        return new Result().setCode(ResultCode.OK).setData(contests);
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/contest/{contestId}/access", method = RequestMethod.GET)
    public Result hasAccess(@PathVariable Integer contestId) {
        Integer operatorId = getJWTUserId();

        JSONObject res = new JSONObject();
        res.put("result", contestApplyService.checkIsApplySuccessed(contestId, operatorId));
        return new Result().setCode(ResultCode.OK).setData(res);
    }
}
