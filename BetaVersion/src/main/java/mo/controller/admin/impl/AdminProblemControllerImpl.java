package mo.controller.admin.impl;

import mo.controller.admin.AdminProblemController;
import mo.entity.po.Problem;
import mo.entity.po.User;
import mo.service.ContestProblemService;
import mo.service.ProblemService;
import mo.utils.DIYMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static mo.utils.string.StringValue.*;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER}, types = {User.class})
public class AdminProblemControllerImpl implements AdminProblemController {

    private final static Logger logger = LoggerFactory.getLogger(AdminProblemControllerImpl.class);

    @Resource
    private ProblemService problemService;

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_get_problems", method = RequestMethod.POST, headers = "Accept=application/json")
    public String getProblems(@RequestBody Map<String, Object> data, @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user) {
        Object defunct_tmp = data.get("defunct");
        List<Problem> problems = null;
        if (defunct_tmp == null || data.containsKey("ask")) {
            logger.info("post查询 用户[{}]的题目集", user.getUser_id());
            problems = problemService.findCreatorProblemsByUserId(user.getUser_id());
        } else {
            logger.info("post查询题目集，公开级别[{}]", defunct_tmp);
            int defunct = Integer.valueOf(defunct_tmp.toString());
            problems = problemService.findProblemsByDefunct(defunct);
        }
        return new JSONObject().put("problems", new JSONArray(problems)).toString();
    }

    @Override
    @RequestMapping(value = "/admin_problem_list")
    public String problemList(ModelMap map,
                              @RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(value = "orderByType", defaultValue = "") String type,
                              @RequestParam(value = "sort", defaultValue = "") String sort) {
        logger.info("order by type [{}],sort value [{}]", type, sort);

        if (!StringUtils.isEmpty(type) && !StringUtils.isEmpty(sort)) {
            map.put("problems", problemService.findProblemsByPageOrderByTypeAndSort(page, middle_page_num, type, sort));
        } else {
            map.put("problems", problemService.findProblemsByPage(page, middle_page_num));
        }

        map.put("orderByType", type);
        map.put("sort", sort);
        return "admin/problems/problem_list";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_problem_dodel", method = RequestMethod.POST, headers = "Accept=application/json")
    public String problemDoDelete(@RequestBody Map<String, Object> data) {
        //TOD 删除权限检查 测试权限不够 删除失败
        DIYMessage res = problemService.delProblemByProblemId(Integer.valueOf((String) data.get("problem_id")));
        return new JSONObject().put(res_type_key, res.getMessageType()).put(res_key, "" + res.getObject()).toString();
    }

    @Override
    @RequestMapping(value = "/admin_problem_add")
    public String addProblem(ModelMap map, @ModelAttribute("msg") DIYMessage msg) {
        if (msg != null) {
            map.put(res_key, msg.getObject());
            map.put(res_type_key, msg.getMessageType());
        }
        return "admin/problems/problem_add";
    }
}
