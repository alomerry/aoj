package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.admin.AdminContestApplyController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Contest;
import mo.entity.vo.link.ContestApplyLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestApplyService;
import mo.service.ContestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AdminContestApplyControllerImpl extends AbstractController implements AdminContestApplyController {

    @Resource
    private ContestApplyService contestApplyService;

    @Resource
    private ContestService contestService;

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/contest_apply", method = RequestMethod.GET)
    public Result contestApply(@PathVariable Integer contest_id,
                               @RequestParam(value = "page", defaultValue = "1") String page,
                               @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        Integer operatorId = getJWTUserId();
        Contest contest = contestService.findContestByContestId(contest_id);
        if (!contest.getUser_id().equals(operatorId)) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足!");
        } else {
            List<ContestApplyLink> contestApplys = contestApplyService.getContestApplyByContestId(contest_id, Integer.valueOf(page), Integer.valueOf(per_page));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contestApplys", contestApplys);
            return new Result().setCode(ResultCode.OK).setData(jsonObject);
        }
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/contest_apply_num", method = RequestMethod.GET)
    public Result applyNumber(@PathVariable Integer contest_id) {
        if (contestService.isCreator(getJWTUserId(), contest_id)) {
            JSONObject num = new JSONObject();
            num.put("apply_number", contestApplyService.getUncheckedApplyNumberByContestId(contest_id));
            return new Result().setCode(ResultCode.OK).setData(num);
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足!");
        }
    }
}
