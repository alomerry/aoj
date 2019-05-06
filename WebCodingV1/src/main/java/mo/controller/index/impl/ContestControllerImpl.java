package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.index.ContestController;
import mo.core.Result;
import mo.core.ResultCode;
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
}
