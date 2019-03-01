package mo.service;

import mo.entity.po.Privilege;

public interface PrivilegeService {
    /**
     * 根据用户Id查找用户所在组
     *
     * @param userId
     * @return
     */
    Privilege findUserGroupByUserId(Integer userId);
}
