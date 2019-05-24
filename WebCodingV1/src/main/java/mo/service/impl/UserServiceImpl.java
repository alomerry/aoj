package mo.service.impl;

import com.alibaba.fastjson.JSONObject;
import mo.core.Result;
import mo.core.ResultCode;
import mo.dao.PrivilegeMapper;
import mo.dao.SolutionMapper;
import mo.dao.UserMapper;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserContestResult;
import mo.entity.vo.link.UserLink;
import mo.service.UserService;
import mo.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;
import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@Service
@PropertySource(value = {"classpath:salt.properties"}, ignoreResourceNotFound = false, encoding = "UTF-8")
public class UserServiceImpl implements UserService {

    @Value("${PASSWORD.SALT}")
    private String SALT;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Resource
    private SolutionMapper solutionMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    //todo 不规范
    @Override
    public Result checkLogin(String username, String passwd, HttpSession session) {
        logger.info("salt : [{}]", SALT);
        passwd += SALT;
        if (userMapper.GetNumByUserName(username) < 1) {
            return new Result().setCode(ResultCode.NOT_FOUND).setMessage("用户名不存在!请修改后重试!");
        } else {
            passwd = DigestUtils.md5DigestAsHex(passwd.getBytes());
            User tmp_user = userMapper.findUserByUserNameAndUserPwd(username, passwd);
            if (tmp_user == null) {
                return new Result().setCode(ResultCode.NOT_FOUND).setMessage("密码错误!请重新输入!");
            } else {
                if (tmp_user.isDisabled()) {
                    return new Result().setCode(ResultCode.FORBIDDEN).setMessage("您的帐号被屏蔽!");
                }
                JSONObject res = new JSONObject();
                userMapper.updateSessionIdByUserId(tmp_user.getUser_id(), session.getId(), new Timestamp(System.currentTimeMillis()));
                logger.info("user[{}]", tmp_user);
                session.setAttribute(ONLINEJUDGE_SESSION_UER, tmp_user);
                Privilege privilege = privilegeMapper.findPrivilegeByUserId(tmp_user.getUser_id());
                privilege = privilege == null ? new Privilege("user") : privilege;
                session.setAttribute(ONLINEJUDGE_SESSION_GROUP, privilege);
                tmp_user.setSession_id("");
                res.put("user", tmp_user);
                res.put("admin", privilege.getRightstr().startsWith("admin"));
                if (privilege.getRightstr().startsWith("admin")) {
                    res.put("level", privilege.getRightstr());
                }
                res.put("jwt", JWTUtils.makeToken(new UserLink(tmp_user, privilege), 300));
                return new Result().setCode(ResultCode.OK).setData(res).setMessage("登录成功!");
            }
        }
    }

    @Override
    public boolean checkUserNameExist(String username) {
        return userMapper.GetNumByUserName(username) > 0;
    }

    @Override
    public Integer register(User user, HttpSession session) {
        user.setPasswd(user.getPasswd() + SALT);
        user.setPasswd(DigestUtils.md5DigestAsHex(user.getPasswd().getBytes()));
        if (userMapper.insertNewUser(user) > 0) {
            Integer userId = userMapper.findLastInsertId();
            User tmp_user = userMapper.findUserByUserId(userId);
            session.setAttribute(ONLINEJUDGE_SESSION_UER, tmp_user);
            session.setAttribute(ONLINEJUDGE_SESSION_GROUP, new Privilege("user"));
            userMapper.updateSessionIdByUserId(userId, session.getId(), new Timestamp(System.currentTimeMillis()));
            return userId;
        }
        return -1;
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public List<UserLink> findUsersByPageAndPerPage(Integer page, Integer per_page) {
        List<UserLink> userLinks = new ArrayList<>();
        for (User user : userMapper.findUsersByPage((page - 1) * per_page, per_page)) {
            userLinks.add(new UserLink(user, privilegeMapper.findPrivilegeByUserId(user.getUser_id())));
        }
        return userLinks;
    }

    @Override
    public UserLink findUserLinkByUserId(Integer user_id) {
        return new UserLink(userMapper.findUserByUserId(user_id), privilegeMapper.findPrivilegeByUserId(user_id));
    }

    @Override
    public Integer deleteUserByUserId(Integer user_id) {
        return userMapper.deleteUserByUserId(user_id);
    }

    @Override
    public Integer disableUser(Integer user_id, Integer state) {
        return userMapper.updateUserDisabled(user_id, state);
    }

    @Override
    public Integer updateUser(Map<String, String> user, UserLink oldUser) {
        /*
         * 1.nickname
         * 2.level
         * 3.passwd
         * 4.email
         * 5.disabled
         * */
        String level = "".equals(user.get("level")) ? "user" : user.get("level");
        if (oldUser.getPrivilege() == null) {
            logger.info("修改权限级别[{}],方式[插入]", (privilegeMapper.insertPrivilege(new Privilege(oldUser.getUser().getUser_id(), level)) > 0 ? "成功" : "失败"));
        } else {
            oldUser.getPrivilege().setRightstr(level);
            logger.info("修改权限级别[{}],方式[修改]", privilegeMapper.updateRightStr(oldUser.getPrivilege()) > 0 ? "成功" : "失败");
        }
        oldUser.getUser().setNickname("".equals(user.get("nickname")) ? oldUser.getUser().getNickname() : user.get("nickname"));
        oldUser.getUser().setPasswd("".equals(user.get("passwd")) ? oldUser.getUser().getPasswd() : DigestUtils.md5DigestAsHex((user.get("passwd") + SALT).getBytes()));
        oldUser.getUser().setEmail("".equals(user.get("email")) ? oldUser.getUser().getEmail() : user.get("email"));
        oldUser.getUser().setDisabled(user.get("disabled") == null ? oldUser.getUser().isDisabled() : Boolean.valueOf(user.get("disabled")));
        logger.info("修改用户信息[{}]", (userMapper.updateUserNickNameEmailPasswdDisAbled(oldUser.getUser())) > 0 ? "成功" : "失败");
        return 1;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public List<UserContestResult> users(Integer contest_id, int page, int per_page) {
        int[] ids = userMapper.findContestsUsers(contest_id, (page - 1) * per_page, per_page);
        List<UserContestResult> results = new ArrayList<>(ids.length + 3);
        for (int user_id : ids) {
            results.add(new UserContestResult(userMapper.findUserByUserId(user_id),
                    solutionMapper.getUserCorrectSlovedNum(user_id, contest_id),
                    solutionMapper.getUserCorrectSolutionNum(user_id, contest_id),
                    solutionMapper.getUserTotalSolutionNum(user_id, contest_id)));
        }
        return results;
    }

    @Override
    public JSONObject makeJWT(Integer userId) {
        JSONObject res = new JSONObject();
        User tmp_user = userMapper.findUserByUserId(userId);
        Privilege privilege = privilegeMapper.findPrivilegeByUserId(userId);
        tmp_user.setSession_id("");
        privilege = privilege == null ? new Privilege("user") : privilege;
        res.put("admin", privilege.getRightstr().startsWith("admin"));
        res.put("user", tmp_user);
        if (privilege.getRightstr().startsWith("admin")) {
            res.put("level", privilege.getRightstr());
        }
        res.put("jwt", JWTUtils.makeToken(new UserLink(tmp_user, privilege), 300));
        logger.info("user[{}]", res);
        return res;
    }

    @Override
    public int getUserTotalNumer() {
        return userMapper.getUserTotalNumber();
    }

    @Override
    public List<UserLink> findSimilarUserByUserNameAndNickName(String keycode) {
        List<UserLink> userLinks = new ArrayList<>();
        for (User user : userMapper.findUserBySimilarUserNameAndNickName(keycode)) {
            userLinks.add(new UserLink(user, privilegeMapper.findPrivilegeByUserId(user.getUser_id())));
        }
        return userLinks;
    }
}
