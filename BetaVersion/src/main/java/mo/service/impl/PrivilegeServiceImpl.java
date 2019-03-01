package mo.service.impl;

import mo.dao.PrivilegeMapper;
import mo.entity.po.Privilege;
import mo.service.PrivilegeService;
import mo.utils.string.StringValue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Override
    public Privilege findUserGroupByUserId(Integer userId) {
        Privilege res = privilegeMapper.findUserGroupByUserId(userId);
        return res == null ? new Privilege(userId, "user") : res;
    }
}
