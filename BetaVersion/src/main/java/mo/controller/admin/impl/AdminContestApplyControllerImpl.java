package mo.controller.admin.impl;

import mo.controller.admin.AdminContestApplyController;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.ContestApplyLink;
import mo.service.ContestApplyService;
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
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER,ONLINEJUDGE_SESSION_GROUP},types = {User.class,Privilege.class})
public class AdminContestApplyControllerImpl implements AdminContestApplyController {

    private static final Logger logger = LoggerFactory.getLogger(AdminContestApplyControllerImpl.class);

    @Resource
    private ContestApplyService contestApplyService;

    @Override
    @RequestMapping("/admin_contest_apply")
    public String contestApplyList(@ModelAttribute(ONLINEJUDGE_SESSION_UER) User user,
                                   @ModelAttribute(ONLINEJUDGE_SESSION_GROUP) Privilege privilege,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "orderByType", defaultValue = "") String type,
                                   @RequestParam(value = "sort", defaultValue = "") String sort, ModelMap map) {

        List<ContestApplyLink> applies = contestApplyService.findApplication(user.getUser_id(), page, middle_page_num, type, sort, privilege.getRightstr());
        map.put("applies", applies);
        //按指定列排序时使用的属性
        map.put("orderByType", type);
        map.put("sort", sort);

        return "admin/contest_apply/contest_confirm";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_contest_doconfirm", method = RequestMethod.POST, headers = "Accept=application/json")
    public String confirmApply(@RequestBody Map<String, Object> data) {

        logger.info("同意用户Id[{}]加入比赛Id[{}],进入service执行事务", data.get("contest_id"), data.get("user_id"));

        DIYMessage res = contestApplyService.updateContestApplyStatus(Integer.valueOf((String) data.get("contest_id")), Integer.valueOf((String) data.get("user_id")));
        return new JSONObject().put(res_type_key, "" + res.getMessageType()).put(res_key, "" + res.getObject()).toString();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_contest_dodelete", method = RequestMethod.POST, headers = "Accept=application/json")
    public String deleteApply(@RequestBody Map<String, Object> data) {

        logger.info("拒绝用户Id[{}]加入比赛Id[{}]，进入service执行事务", data.get("contest_id"), data.get("user_id"));

        DIYMessage res = contestApplyService.deleteContestApply(Integer.valueOf((String) data.get("contest_id")), Integer.valueOf((String) data.get("user_id")));
        return new JSONObject().put(res_type_key, "" + res.getMessageType()).put(res_key, "" + res.getObject()).toString();
    }
}
