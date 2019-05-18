package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.index.SubmitController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Solution;
import mo.entity.po.SourceCode;
import mo.exception.ServiceException;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.ProblemService;
import mo.service.SolutionService;
import mo.utils.InetAddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class SubmitControllerImpl extends AbstractController implements SubmitController {

    private static final Logger logger = LoggerFactory.getLogger(SubmitControllerImpl.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private SolutionService solutionService;

    @Override
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = "application/json")
    public Result insertSubmit(@RequestBody Map<String, Object> submit) {
        Solution solution = initBySubmit(submit);
        SourceCode sourceCode;
        /*
         * 1.判断题目是否被禁用
         *   1.1禁用(错误！无法判题)
         *   1.2未禁用
         * 2.题目是否在竞赛中
         *   2.1不在竞赛中
         *   2.2在竞赛中
         * */
        if (problemService.isDisabledProblem(solution.getProblem_id())) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("题目被屏蔽，无法判题");
        } else {
            try {
                if (solutionService.insertIntoNewSolution(solution, new SourceCode((String) submit.get("code")))) {
                    return new Result().setCode(ResultCode.OK);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
                return new Result().setCode(ResultCode.FORBIDDEN).setMessage(e.getMessage());
            }
        }
        return null;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/solutions", method = RequestMethod.GET)
    public Result getSolutions(@RequestParam(value = "page", defaultValue = "1") String page,
                               @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                               @RequestParam(value = "myself", defaultValue = "0") String myself) {
        JSONObject solutions = new JSONObject();
        if ("0".equals(myself)) {
            solutions.put("solutions", solutionService.getSolutions(Integer.valueOf(page), Integer.valueOf(per_page)));
        } else {
            try {
                Integer operatorId = getJWTUserId();
                if (operatorId == null) {
                    return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("请登录！");
                } else {
                    solutions.put("solutions", solutionService.getSolutionsByUserId(operatorId, Integer.valueOf(page), Integer.valueOf(per_page)));
                    return new Result().setCode(ResultCode.OK).setData(solutions);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Result().setCode(ResultCode.OK).setData(solutions);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/state/{state}/solutions", method = RequestMethod.GET)
    public Result getSolutions(@PathVariable Integer state,
                               @RequestParam(value = "page", defaultValue = "1") String page,
                               @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                               @RequestParam(value = "myself", defaultValue = "0") String myself) {
        JSONObject solutions = new JSONObject();
        if ("0".equals(myself)) {
            solutions.put("solutions", solutionService.getSolutions(state, Integer.valueOf(page), Integer.valueOf(per_page)));
        } else {
            try {
                Integer operatorId = getJWTUserId();
                if (operatorId == null) {
                    return new Result().setCode(ResultCode.BAD_REQUEST).setMessage("请登录！");
                } else {
                    if (state == 15) {
                        solutions.put("solutions", solutionService.getSolutionsByUserId(operatorId, Integer.valueOf(page), Integer.valueOf(per_page)));
                    } else {
                        solutions.put("solutions", solutionService.getSolutionsByUserId(state, operatorId, Integer.valueOf(page), Integer.valueOf(per_page)));
                    }
                    return new Result().setCode(ResultCode.OK).setData(solutions);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Result().setCode(ResultCode.OK).setData(solutions);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/contest/{contestId}/solutions", method = RequestMethod.GET)
    public Result getSolutions(@PathVariable Integer contestId,
                               @RequestParam(value = "page", defaultValue = "1") String page,
                               @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject solutions = new JSONObject();
        solutions.put("solutions", solutionService.getContestSolutions(contestId, Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setCode(ResultCode.OK).setData(solutions);
    }

    /**
     * 读取map中的数据，包装程solution实体
     *
     * @param submit map数据
     * @return solution实体
     */
    private Solution initBySubmit(Map<String, Object> submit) {
        Solution solution = new Solution();
        solution.setIp(InetAddressUtils.getIpAddr(getHttpServletRequest()));
        solution.setCode_lenght((Integer) submit.get("code_lenght"));
//        solution.setContest_id();
        solution.setCreate_at(new Timestamp(System.currentTimeMillis()));
        solution.setLanguage(Integer.valueOf((String) submit.get("language")));
        solution.setProblem_id((Integer) submit.get("problem_id"));
        solution.setUser_id(getJWTUserId());
        solution.setResult(0);
        return solution;
    }
}
