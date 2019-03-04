package mo.service.impl;

import com.alibaba.fastjson.JSONObject;
import mo.core.Result;
import mo.core.ResultCode;
import mo.dao.PrivilegeMapper;
import mo.dao.UserMapper;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
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

import java.util.ArrayList;
import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


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
                JSONObject res = new JSONObject();
                userMapper.updateSessionIdByUserId(tmp_user.getUser_id(), session.getId());
                logger.info("user[{}]", tmp_user);
                session.setAttribute(ONLINEJUDGE_SESSION_UER, tmp_user);
                Privilege privilege = privilegeMapper.findPrivilegeByUserId(tmp_user.getUser_id());
                privilege = privilege == null ? new Privilege("user") : privilege;
                session.setAttribute(ONLINEJUDGE_SESSION_GROUP, privilege);
                tmp_user.setSession_id("");
                res.put("user", tmp_user);
                res.put("admin", privilege.getRightstr().startsWith("admin"));
                res.put("jwt", JWTUtils.makeToken(new UserLink(tmp_user, privilege), 20));
                return new Result().setCode(ResultCode.OK).setData(res).setMessage("登录成功!");
            }
        }
    }

    @Override
    public String checkRegister(User user, String rpt_pwd) {
        return null;
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

}
