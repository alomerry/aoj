package mo.controller.index.impl;

import mo.controller.index.ProblemController;
import mo.entity.po.User;
import mo.service.ProblemService;
import mo.service.SolutionService;
import mo.utils.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.UnknownHostException;

import static mo.utils.string.StringValue.ONLINEJUDGE_SESSION_UER;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER}, types = User.class)
public class ProblemControllerImpl implements ProblemController {

    @Resource
    private ProblemService problemService;

    @Resource
    private SolutionService solutionService;

    private static final Logger logger = LoggerFactory.getLogger(ProblemControllerImpl.class);

    @Override
    @RequestMapping("/problem_list")
    public String problemList(@RequestParam(value = "page", defaultValue = "1") int page, ModelMap modelMap) {
        modelMap.addAttribute("problems", problemService.findPublicProblemsByPage(page, StringValue.middle_page_num));
        return "index/problems/problem_list";
    }

    @Override
    @RequestMapping("/problem_detail")
    public String problemDetail(Integer problem_id, ModelMap modelMap, @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user) {
        // TOD 检查问题是否公开  测试
        if (!problemService.checkProblemIsPublic(problem_id, user.getUser_id())) {
            return "404";
        }
        logger.info("problem_detail: problem_id =[{}] ", problem_id);
        modelMap.addAttribute("problem", problemService.findProblemByProblemId(problem_id));
        return "index/problems/problem_detail";
    }

    @Override
    @RequestMapping("/problem_submit")
    public String problemSubmit(Integer problem_id, ModelMap modelMap) {
        //TOD 比赛截止的题目只能阅读无法提交
        logger.info("problem_submit: problem_id =[{}] ", problem_id);
        modelMap.addAttribute("problem", problemService.findProblemByProblemId(problem_id));
        return "index/problems/problem_submit";
    }

    @Override
    @RequestMapping(value = "/problem_dosubmit", method = RequestMethod.POST)
    public String problemDoSubmit(@ModelAttribute(ONLINEJUDGE_SESSION_UER) User user, Integer problem_id, String language, String user_code) {
        try {
            solutionService.insertIntoSolution(problemService.findProblemByProblemId(problem_id), user.getUser_id(), user_code);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "redirect:solution_status";
    }


}
