package mo.controller.index.impl;

import mo.controller.index.ContestController;
import mo.entity.po.Contest;
import mo.entity.po.User;
import mo.entity.vo.ContestApplyLink;
import mo.service.ContestApplyService;
import mo.service.ContestProblemService;
import mo.service.ContestService;
import mo.utils.DIYMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static mo.utils.string.StringValue.*;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER}, types = {User.class})
public class ContestControllerImpl implements ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestControllerImpl.class);

    @Resource
    private ContestService contestService;

    @Resource
    private ContestApplyService contestApplyService;

    @Resource
    private ContestProblemService contestProblemService;

    @Override
    @RequestMapping("/contest_list")
    public String contestList(@RequestParam(value = "page", defaultValue = "1") int page, ModelMap map) {
        List<Contest> contests = contestService.getPublicContestsByPage(page, middle_page_num);
        map.put("contests", contests);
        return "index/contest/contest_list";
    }

    @Override
    @RequestMapping("/contest_detail")
    public String contestDetail(int contestId, ModelMap map) {
        map.put("contest", contestService.getContestByContestId(contestId));
        return "index/contest/contest_detail";
    }


    @Override
    @ResponseBody
    @RequestMapping(value = "/contest_doadd", method = RequestMethod.POST, headers = "Accept=application/json")
    public String contestDoAdd(@RequestBody Map<String, Object> data, @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user) {
        Integer contest_id = null;
        try {
            contest_id = Integer.parseInt((String) data.get("contest_id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            contest_id = null;
        }
        DIYMessage message = null;
        logger.info("获取json数据：contest_id:[{}]，请求申请加入比赛" + contest_id);
        if (contest_id == null) {
            message = new DIYMessage(ContestService.AddContestFail, "未知错误，请稍后重试！");
        } else {
            message = contestApplyService.ApplyContest(contest_id, user.getUser_id());
        }
        return new JSONObject().put(res_type_key, "" + message.getMessageType()).put(res_key, (String) message.getObject()).toString();
    }


    //TODO 测试
    @Override
    @RequestMapping(value = "/own_competition_list")
    public String competitionList(@ModelAttribute(ONLINEJUDGE_SESSION_UER) User user, Integer contest_id, ModelMap map) {
        Object res = checkCompetitionAccess(user, contest_id);
        if (res instanceof ContestApplyLink) {
            map.addAttribute("competition", contestProblemService.findCompetitionProblems(contest_id));
            return "index/contest/competition_list";
        } else {
            return "index/404";
        }
    }

    //TODO 测试
    @Override
    @RequestMapping(value = "/own_competition_check", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String competitionCheck(@RequestBody Map<String, Object> data, @ModelAttribute(ONLINEJUDGE_SESSION_UER) User user) {
        Object res = checkCompetitionAccess(user, Integer.parseInt((String) data.get("contest_id")));
        if (res instanceof ContestApplyLink) {
            return new JSONObject().put("type", "0").toString();
        } else {
            return new JSONObject().put("type", "" + res).toString();
        }
    }

    //TODO 手机测试修改时间测试安全性

    /**
     * 检查比赛信息正确性
     *
     * @param user       用户实体
     * @param contest_id 比赛Id
     * @return 0:该用户不在比赛中
     * -1:比赛未开始
     * 0:Object 比赛开始 返回实例
     * 1:比赛结束
     * 2:未加入比赛
     * 3:请联系管理员确认
     */
    //TODO 检查时间 检查权限(已加入比赛) 检查登录
    private Object checkCompetitionAccess(User user, Integer contest_id) {
        //检查比赛有效性
        ContestApplyLink contestApplyLink = contestApplyService.findContestApplyLink(contest_id, user.getUser_id());
        Contest contest = contestApplyLink.getContest();
        if (contest == null) {
            return 2;
        } else {
            if (contestApplyLink.getStatus() == 0) {//检查是否被接收申请
                return 3;
            } else {
                int res = contest.comparaToNow();
                if (res == 0) {//检查时间
                    return contestApplyLink;
                } else {
                    return res;
                }
            }

        }
    }
}
