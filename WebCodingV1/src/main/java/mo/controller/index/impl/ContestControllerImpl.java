package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.index.ContestController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Contest;
import mo.entity.vo.link.ContestLinkUser;
import mo.service.ContestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ContestControllerImpl implements ContestController {

    @Resource
    private ContestService contestService;

    @Override
    @ResponseBody
    @RequestMapping(value = "/contests", method = RequestMethod.GET)
    public Result contests(@RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject contests = new JSONObject();
        contests.put("contests", contestService.findContestsByPageAndDefunct(Integer.valueOf(page), Integer.valueOf(per_page), new Integer[]{1}));
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
}
