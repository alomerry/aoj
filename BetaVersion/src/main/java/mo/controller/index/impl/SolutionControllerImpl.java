package mo.controller.index.impl;

import mo.controller.index.SolutionController;
import mo.entity.po.Solution;
import mo.entity.po.User;
import mo.service.SolutionService;
import mo.utils.string.StringValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@SessionAttributes(value = {StringValue.ONLINEJUDGE_SESSION_UER}, types = User.class)
public class SolutionControllerImpl implements SolutionController {

    @Resource
    private SolutionService solutionService;

    @Override
    @RequestMapping("/solution_status")
    public String listUserAll(@ModelAttribute(StringValue.ONLINEJUDGE_SESSION_UER) User user, @RequestParam(value = "page", defaultValue = "1") int page, ModelMap map) {
        List<Solution> solutions = solutionService.findSolutionsByUserIdOrderByJudgeTimeByPage(user.getUser_id(), true, page, StringValue.middle_page_num);
        map.addAttribute("solutions",solutions);
        return "index/solutions/solution_status";
    }

    @Override
    public String listAll(int page, ModelMap map) {
        return null;
    }
}
