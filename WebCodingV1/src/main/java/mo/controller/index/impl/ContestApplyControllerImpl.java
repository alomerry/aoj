package mo.controller.index.impl;

import mo.controller.AbstractController;
import mo.controller.index.ContestApplyController;
import mo.core.Result;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestApplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ContestApplyControllerImpl extends AbstractController implements ContestApplyController {

    @Resource
    private ContestApplyService contestApplyService;

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/contest_apply", method = RequestMethod.POST)
    public Result contestApply(@RequestParam("contest_id") Integer contest_id) {
        //人数是否已满
        return null;
    }
}
