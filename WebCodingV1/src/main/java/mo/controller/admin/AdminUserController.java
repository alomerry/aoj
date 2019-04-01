package mo.controller.admin;

import mo.core.Result;

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
}
