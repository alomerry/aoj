package mo.controller.index.impl;

import mo.controller.index.UserController;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.ContestApplyLink;
import mo.listener.SessionListener;
import mo.service.ContestApplyService;
import mo.service.PrivilegeService;
import mo.service.UserService;
import mo.utils.DIYMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static mo.utils.string.StringValue.*;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER, ONLINEJUDGE_SESSION_GROUP}, types = {User.class, Privilege.class})
public class UserControllerImpl implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Resource
    private ContestApplyService contestApplyService;

    @Override
    @RequestMapping("user_login")
    public String login(@ModelAttribute("message") String message, ModelMap map) {
        return "index/users/user_login";
    }

    @Override
    @RequestMapping("user_register")
    public String register(@ModelAttribute("message") String message, ModelMap map) {
        return "index/users/user_register";
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/registerDo", method = RequestMethod.POST, headers = "Accept=application/json")
    public String registerDo(@RequestBody Map<String, Object> data, ModelMap map) {
        logger.info("检测注册信息中...");
        logger.info("注册信息[{}]:[{}]", "rptPwd", (String) data.get("rptPwd"));
        DIYMessage msg = userService.checkRegister(makeUser(data), (String) data.get("rptPwd"));
        if (msg.getMessageType() != UserService.REGISTER_SUCCESS) {
            logger.info("注册失败[{}]...失败原因[{}]", msg.getMessageType(), msg.getObject());
        } else {
            logger.info("注册成功");
            map.addAttribute(ONLINEJUDGE_SESSION_UER, msg.getObject());
        }
        return new JSONObject().put(res_type_key, msg.getMessageType()).put(res_key, msg.getObject()).toString();
    }


    @Override
    @ResponseBody
    @RequestMapping(value = "/loginDo", method = RequestMethod.POST, headers = "Accept=application/json")
    public String loginDo(@RequestBody Map<String, Object> data, ModelMap map, HttpServletRequest request, HttpServletResponse response) {

        logger.info("检测登录信息中...username[{}]:pwd[{}]", (String) data.get("username"), (String) data.get("passwd"));
        DIYMessage now = userService.checkLogin((String) data.get("username"), (String) data.get("passwd"), request.getSession().getId());
        if (now.getMessageType() == UserService.LOGIN_SUCCESS) {
            logger.info("登录成功，创建session，id为[{}]", request.getSession().getId());
            makeCookie(request, response);

            Privilege group = privilegeService.findUserGroupByUserId(((User) now.getObject()).getUser_id());
            map.addAttribute(ONLINEJUDGE_SESSION_UER, now.getObject());
            map.addAttribute(ONLINEJUDGE_SESSION_GROUP, group);
            return new JSONObject().put(res_type_key, now.getMessageType()).toString();
        } else {
            return new JSONObject().put(res_type_key, now.getMessageType()).toString();
        }
    }

    @Override
    @RequestMapping("/loginout")
    public String loginout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:home";
    }

    @Override
    @RequestMapping("/online_user_num")
    @ResponseBody
    public String online_number() {
        logger.info("在线人数:[{}]", SessionListener.getOnline_number());
        return String.valueOf(SessionListener.getOnline_number());
    }

    @Override
    @RequestMapping("/own_competition")
    public String ownCompetition(@ModelAttribute(ONLINEJUDGE_SESSION_UER) User user, ModelMap map) {
        List<ContestApplyLink> contests = contestApplyService.findMineApplyCompetition(user.getUser_id());
        map.put("contests", contests);
        return "index/contest/contest_mine";
    }

    private void makeCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("JSESSION", URLEncoder.encode(request.getSession().getId(), "utf-8"));
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            logger.info("创建cookies成功 , sessionId为[{}]", request.getSession().getId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private User makeUser(Map<String, Object> map) {
        String usename = (String) map.get("username");
        String nickname = (String) map.get("nickname");
        String passwd = (String) map.get("passwd");
        String school = (String) map.get("school");
        String email = (String) map.get("email");
        logger.info("注册信息[{}]:[{}]", "usename", usename);
        logger.info("注册信息[{}]:[{}]", "nickname", nickname);
        logger.info("注册信息[{}]:[{}]", "passwd", passwd);
        logger.info("注册信息[{}]:[{}]", "school", school);
        logger.info("注册信息[{}]:[{}]", "email", email);
        return new User(usename == null ? "" : usename,
                nickname == null ? "" : nickname,
                passwd == null ? "" : passwd,
                school == null ? "" : school,
                email == null ? "" : email);
    }
}

