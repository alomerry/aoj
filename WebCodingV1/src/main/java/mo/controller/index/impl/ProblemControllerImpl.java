package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.index.ProblemController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Problem;
import mo.entity.vo.ProblemLink;
import mo.service.ProblemService;
import mo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProblemControllerImpl implements ProblemController {

    private static final Logger logger = LoggerFactory.getLogger(ProblemControllerImpl.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;


    //Don't use
    @Override
    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        List<Problem> problems = problemService.findSimpleProblemsByDefunct("(1)", 1, 10);
        JSONObject res = new JSONObject();
        res.put("total", problems.size());
        res.put("results", problems);
        return new Result().setCode(ResultCode.OK).setData(res);
    }

    @Override
    @ResponseBody
    @RequestMapping("/problems/tag/{tag_id}")
    public Result problems(@PathVariable String tagId,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                           @RequestParam(value = "resType", defaultValue = "simple") String resType) {

        return null;
    }

    @Override
    @RequestMapping(value = "/problems/defunct/{defunct}")
    @ResponseBody
    public Result problems(@RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                           @RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @PathVariable String defunct) {
        List<Problem> problems = null;
        switch (resType) {
            case "simple": {
                problems = problemService.findSimpleProblemsByDefunct("(" + defunct + ")", Integer.parseInt(page), Integer.parseInt(per_page));
                break;
            }
            case "much": {
                //TOD 权限检测
                break;
            }
        }
        JSONObject res = new JSONObject();
        assert problems != null;
        res.put("total", problems.size());
        res.put("results", problems);
        return new Result().setCode(ResultCode.OK).setData(res);
    }

    @Override
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result problem(@PathVariable Integer id) {
        ProblemLink problem = new ProblemLink();
        problem.setProblem(problemService.findProblemByProblemId(id));
        if (problem.getProblem() == null) {
            return new Result().setCode(ResultCode.NOT_FOUND).setMessage("问题不存在!");
        } else {
            problem.setCreated_by(userService.findUserByUserId(problem.getProblem().getCreate_by()));
            JSONObject json = new JSONObject();
            json.put("result", problem);
            return new Result().setCode(ResultCode.OK).setData(json);
        }
    }
}
