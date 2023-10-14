package mo.controller.index.impl;

import mo.controller.AbstractController;
import mo.controller.index.ContestApplyController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.main.Contest;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestApplyService;
import mo.service.ContestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ContestApplyControllerImpl extends AbstractController implements ContestApplyController {

    @Resource
    private ContestApplyService contestApplyService;

    @Resource
    private ContestService contestService;

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/contest/{contest_id}/contest_apply", method = RequestMethod.POST)
    public Result contestApply(@PathVariable Integer contest_id) {
        /**
         * 1.判断是否已报名
         * 2.判断人数是否已满
         */
        if (!contestApplyService.checkExistByContestIdAndUserId(getJWTUserId(), contest_id)) {
            Contest contest = contestService.findContestByContestId(contest_id);
            if (contest == null) {
                return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("竞赛不存在!");
            } else if (contest.getNow() >= contest.getMax()) {
                return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("参赛人数已满!");
            }
            if (contestApplyService.applyContest(getJWTUserId(), contest_id)) {
                return new Result().setCode(ResultCode.OK).setMessage("申请成功!");

            } else {
                return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("申请失败!");
            }
        } else {
            return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("已申请成功!勿重复申请!");
        }
    }
}
