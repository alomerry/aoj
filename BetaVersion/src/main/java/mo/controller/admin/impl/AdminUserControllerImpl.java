package mo.controller.admin.impl;

import mo.controller.admin.AdminUserController;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.service.PrivilegeService;
import mo.service.UserService;
import mo.utils.DIYMessage;
import mo.utils.string.StringValue;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static mo.utils.string.StringValue.res_key;
import static mo.utils.string.StringValue.res_type_key;

@Controller
@SessionAttributes(value = {StringValue.ONLINEJUDGE_SESSION_UER}, types = {User.class})
public class AdminUserControllerImpl implements AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserControllerImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Override
    @RequestMapping(value = "/admin_user_list")
    public String userList(ModelMap map, @RequestParam(value = "page", defaultValue = "1") int page) {
        List<UserLink> userLinks = userService.findUserLinkByPage(page, StringValue.middle_page_num);

        logger.info("读取第[{}]页userLink完毕", page);
        for (UserLink u : userLinks) {
            logger.info("用户名[{}],用户级别[{}]", u.getUser().getUsername(), u.getPrivilege().getRightstr());
        }
        map.put("userLinks", userLinks);
        return "admin/users/user_list";
    }

    @Override
    @RequestMapping("/admin_user_edit")
    public String userEdit(ModelMap map, Integer userId, @ModelAttribute("doEdit_msg") String msg) {
        map.put("user", userService.findUserByUserId(userId));
        map.put("doEdit_msg", msg);
        map.put("admin_level", privilegeService.findUserGroupByUserId(userId).getRightstr());

        logger.info("查询用户信息以备修改，用户权限为[{}]", map.get("admin_level"));
        return "admin/users/user_edit";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_user_doEdit", method = RequestMethod.POST)
    public String userDoEdit(User user, Privilege privilege, RedirectAttributes map) {
        logger.info("user info:[{}]", user.toString());
        logger.info("privilege info:[{}]", privilege.toString());
        return new JSONObject().put(res_type_key, "0").put(res_key, "修改失败！权限不够！").toString();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin_user_doDel", method = RequestMethod.POST, headers = "Accept=application/json")
    public String userDoDel(@RequestBody Map<String, Object> data) {
        //TOD 检查是否是管理员，如果是，提示需要先降级 测试
        DIYMessage message = userService.delUser(Integer.valueOf((String) data.get("user_id")));
        return new JSONObject().put(res_type_key, message.getMessageType()).put(res_key, message.getObject()).toString();
    }


}
