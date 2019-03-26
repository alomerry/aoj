package mo.controller.admin.impl;

import mo.controller.admin.AdminContestController;
import mo.entity.po.Contest;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.service.ContestApplyService;
import mo.service.ContestProblemService;
import mo.service.ContestService;
import mo.utils.DIYMessage;
import mo.utils.string.StringValue;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static mo.utils.string.StringValue.*;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER, ONLINEJUDGE_SESSION_GROUP}, types = {User.class, Privilege.class})
public class AdminContestControllerImpl implements AdminContestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminContestControllerImpl.class);

    @Resource
    private ContestService contestService;

    @Resource
    private ContestApplyService contestApplyService;

    @Resource
    private ContestProblemService contestProblemService;

    @Override
    @RequestMapping("/admin_contest_list")
    public String listContest(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "orderByType", defaultValue = "") String type,
                              @RequestParam(value = "sort", defaultValue = "") String sort,
                              @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user,
                              @ModelAttribute(ONLINEJUDGE_SESSION_GROUP) Privilege privilege, ModelMap map) {

        logger.info("order by type [{}],sort value [{}]", type, sort);

        List<Contest> contests = null;
        if (!StringUtils.isEmpty(type) && !StringUtils.isEmpty(sort)) {
            contests = contestService.getContestsByPageWithOrderBy(page, StringValue.middle_page_num, type, sort, privilege.getRightstr(), user.getUser_id());
        } else {
            contests = contestService.getContestsByPage(page, StringValue.middle_page_num, privilege.getRightstr(), user.getUser_id());
        }

        map.put("contests", contests);
        //按指定列排序时使用的属性
        map.put("orderByType", type);
        map.put("sort", sort);

        return "admin/contest/contest_list";
    }

    @Override
    @RequestMapping("/admin_contest_add")
    public String addContest(@ModelAttribute("msgs") DIYMessage msg, ModelMap map) {
        if (msg != null) {
            map.put(res_key, msg.getObject());
            map.put(res_type_key, msg.getMessageType());
        }
        return "admin/contest/contest_add";
    }

    @Override
    @RequestMapping(value = "/admin_contest_doadd", method = RequestMethod.POST)
    public String contestDoAdd(Contest contest, RedirectAttributes map, @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user) {
        logger.info("添加竞赛的信息\n[{}]\n", contest.toString());
        contest.setUser_id(user.getUser_id());
        DIYMessage res = contestService.addContestByAdmin(contest);
        map.addFlashAttribute("msgs", res);
        return "redirect:/admin_contest_add";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_contest_dodel", method = RequestMethod.POST, headers = "Accept=application/json")
    public String contestDoDelete(@RequestBody Map<String, Object> data) {
        DIYMessage res = contestService.delContestByAdmin(Integer.valueOf((String) data.get("contest_id")));
        return new JSONObject().put(res_type_key, res.getMessageType()).put(res_key, "" + res.getObject()).toString();
    }

    @Override
    @RequestMapping("/admin_contest_view")
    public String contestView(Integer contest_id, ModelMap map) {
        map.put("applies", contestApplyService.findContestApplyLinkByContestId(contest_id));
        map.put("competition", contestProblemService.findCompetitionProblems(contest_id));
        return "admin/contest/contest_view";
    }

    @Override
    @RequestMapping("/admin_contest_edit")
    public String contestEdit(Integer contest_id, ModelMap map) {
        map.put("contest", contestService.getContestByContestId(contest_id));
        map.put("competition", contestProblemService.findCompetitionProblems(contest_id));
        map.put("competition", contestProblemService.findCompetitionProblems(contest_id));
        return "admin/contest/contest_edit";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_problem_addToContest", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addProblemsToContest(@RequestBody Map<String, Object> data) {
        //TOD检测该问题是不是已经在比赛中
        DIYMessage message = contestProblemService.addProblemToContestByContestIdAndProblemId(Integer.valueOf(data.get("contest_id").toString()), Integer.valueOf(data.get("problem_id").toString()));
        return new JSONObject().put(res_type_key, message.getMessageType()).put(res_key, (String) message.getObject()).toString();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_problem_delFromContest", method = RequestMethod.POST, headers = "Accept=application/json")
    public String delProblemsFromContest(@RequestBody Map<String, Object> data) {
        DIYMessage message = contestProblemService.delProblemFromContestByContestIdAndProblemId(Integer.valueOf((String) data.get("contest_id")), Integer.valueOf((String) data.get("problem_id")));
        return new JSONObject().put(res_type_key, message.getMessageType()).put(res_key, (String) message.getObject()).toString();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_contest_changProblemNum", method = RequestMethod.POST, headers = "Accept=application/json")
    public String changContestProblemNum(@RequestBody Map<String, Object> data) {
        DIYMessage message = contestProblemService.changeProblemNumFromContestByContestIdAndProblemId(Integer.valueOf((String) data.get("contest_id")), Integer.valueOf((String) data.get("problem_id")), Integer.valueOf((String) data.get("num")));
        return new JSONObject().put(res_type_key, message.getMessageType()).put(res_key, (String) message.getObject()).toString();
    }
}
