package mo.controller.admin;

import mo.core.Result;

import java.util.Map;

public interface AdminUserController {
    /**
     * 查找用户
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return 用户集
     */
    Result users(String page, String per_page);

    /**
     * 删除指定用户
     *
     * @param user_id 用户Id
     * @return 结果
     */
    Result deleteUser(String user_id);

    /**
     * 禁用用户
     *
     * @param user_id 用户Id
     * @param state   true 正常 false 禁用
     * @return 结果
     */
    Result disableUser(String user_id, Integer state);

    /**
     * 修改用户
     *
     * @param user json格式用户
     * @return 结果
     */
    Result updateUser(Map<String, String> user);
}
