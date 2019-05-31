package mo.service.impl;

import mo.dao.main.PrivilegeMapper;
import mo.entity.po.main.Privilege;
import mo.service.PrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Override
    public Privilege findPrivilegeByUserId(Integer userId) {
        return privilegeMapper.findPrivilegeByUserId(userId);
    }
}
