package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.admin.AdminSolutionController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ContestService;
import mo.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AdminSolutionControllerImpl extends AbstractController implements AdminSolutionController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSolutionControllerImpl.class);

    @Resource
    private SolutionService solutionService;

    @Resource
    private ContestService contestService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/solutions", method = RequestMethod.GET)
    public Result solutions(@PathVariable Integer contest_id,
                            @RequestParam(value = "page", defaultValue = "1") String page,
                            @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        Integer operatorId = getJWTUserId();
        if (contestService.hasAccess(operatorId, contest_id)) {
            JSONObject solutions = new JSONObject();
            solutions.put("solutions", solutionService.getContestSolutions(contest_id, Integer.valueOf(page), Integer.valueOf(per_page)));
            return new Result().setData(solutions).setCode(ResultCode.OK);
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足，无法查看!");
        }
    }
}
