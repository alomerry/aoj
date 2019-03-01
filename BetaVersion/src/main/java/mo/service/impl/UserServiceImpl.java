package mo.service.impl;

import mo.dao.PrivilegeMapper;
import mo.dao.UserMapper;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.service.UserService;
import mo.utils.DIYMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Override
    public DIYMessage checkLogin(String username, String passwd, String sessionId) {
        passwd += UserService.SALT;
        if (userMapper.GetNumByUserName(username) < 1) {
            return new DIYMessage(UserService.LOGIN_ERROR_NO_USER, "用户名不存在!请修改后重试!");
        } else {
            passwd = DigestUtils.md5DigestAsHex(passwd.getBytes());
            User tmp_user = userMapper.findUserByUserNameAndUserPwd(username, passwd);
            if (tmp_user == null) {
                return new DIYMessage(UserService.LOGIN_ERROR_WRONG_PASSWORD, "密码错误!请重新输入!");
            } else {
                userMapper.updateSessionIdByUserId(tmp_user.getUser_id(), sessionId);
                return new DIYMessage(UserService.LOGIN_SUCCESS, tmp_user);
            }
        }
    }

    @Override
    public DIYMessage checkRegister(User user, String rpt_pwd) {
        user.setAccess_time(new Timestamp(System.currentTimeMillis()));
        user.setPasswd(user.getPasswd() + UserService.SALT);
        user.setPasswd(DigestUtils.md5DigestAsHex(user.getPasswd().getBytes()));

        rpt_pwd += UserService.SALT;
        rpt_pwd = DigestUtils.md5DigestAsHex(rpt_pwd.getBytes());
        DIYMessage msg = new DIYMessage();
        if (!user.getPasswd().equals(rpt_pwd)) {
            msg.setMessageType(UserService.REGISTER_PWD_NOSAME);
            msg.setObject("两次密码不一致!");
        } else if (userMapper.GetNumByUserName(user.getUsername()) > 0) {
            msg.setMessageType(UserService.REGISTER_USER_EXCIST);
            msg.setObject("该用户名已存在!");
        } else {
            if (userMapper.insertUser(user) > 0) {
                msg.setObject(user);
            }
            msg.setMessageType(UserService.REGISTER_SUCCESS);
        }
        return msg;
    }


    @Override
    public List<UserLink> findUserLinkByPage(int page, int pageNum) {
        List<User> users = userMapper.listUsersByPage((page - 1) * pageNum, pageNum);
        return fillList(users);
    }

    @Override
    public List<UserLink> findUserLinkByPageAndOrderBy(int page, int pageNum, String orderBy, String type) {
        List<User> users = userMapper.listUsersByPageAndOrderBy((page - 1) * pageNum, pageNum, orderBy, type);
        return fillList(users);
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userMapper.findUserByUserId(userId);
    }

    //检查是否是管理员，如果是，提示需要先降级
    @Override
    public DIYMessage delUser(Integer user_id) {
        Privilege privilege = privilegeMapper.findUserGroupByUserId(user_id);
        if (privilege != null && privilege.getRightstr().startsWith("admin")) {
            return new DIYMessage(FAILED, "此用户为Admin,请降级后重新操作!");
        } else {
            return userMapper.delUserByUserId(user_id) > 0 ? new DIYMessage(SUCCESS, "删除成功!") : new DIYMessage(FAILED, "删除失败,请联系管理员查看日志!");
        }
    }

    private List<UserLink> fillList(List<User> users) {
        List<UserLink> userLinks = new ArrayList<>();
        for (User user : users) {
            Privilege up = privilegeMapper.findUserGroupByUserId(user.getUser_id());
            if (up == null) {
                up = new Privilege("user");
            }
            UserLink tmp = new UserLink(user, up);
            userLinks.add(tmp);
        }
        return userLinks;
    }
}