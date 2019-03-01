package mo.service;

import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.utils.DIYMessage;

import java.util.List;

public interface UserService {

    int LOGIN_SUCCESS = 1;//登录成功
    int LOGIN_ERROR_NO_USER = 2;//用户名不存在
    int LOGIN_ERROR_WRONG_PASSWORD = 3;//密码错误
    int REGISTER_SUCCESS = 4;//注册成功
    int REGISTER_USER_EXCIST = 5;//用户名已存在
    int REGISTER_PWD_NOSAME = 6;//两次密码不一致
    int SUCCESS = 1, FAILED = 0;
    /**
     * 密码盐
     */
    String SALT = "#&***#$#@";

    /**
     * 验证用户登录
     *
     * @param username 用户名
     * @param passwd   密码
     * @return 登录成功返回用户对象，否则返回错误代码和错误描述
     */
    DIYMessage checkLogin(String username, String passwd, String sessionId);

    /**
     * 验证注册信息
     *
     * @param user    用户实体，包含各项注册所需信息
     * @param rpt_pwd 用户密码
     * @return 返回注册结果
     */
    DIYMessage checkRegister(User user, String rpt_pwd);


    /**
     * 根据页数查询 带权限级别 的用户表
     *
     * @param page    页数
     * @param pageNum 每页条数
     * @return 用户列表
     */
    List<UserLink> findUserLinkByPage(int page, int pageNum);

    /**
     * 根据页数 排序 查询 用户表
     *
     * @param page    页数
     * @param pageNum 每页条数
     * @param orderBy 排序字段
     * @param type    排序方式
     * @return 带排序的 带权限级别 的用户列表
     */
    List<UserLink> findUserLinkByPageAndOrderBy(int page, int pageNum, String orderBy, String type);


    /**
     * 根据指定Id查找用户
     *
     * @param userId 用户Id
     * @return 返回用户实体
     */
    User findUserByUserId(Integer userId);

    /**
     * 删除指定用户
     *
     * @param user_id 用户Id
     * @return 自定义操作结果
     */
    DIYMessage delUser(Integer user_id);
}
