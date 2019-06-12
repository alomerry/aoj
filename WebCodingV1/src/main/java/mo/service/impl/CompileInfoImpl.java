package mo.service.impl;

import mo.dao.main.CompileInfoMapper;
import mo.entity.po.main.CompileInfo;
import mo.service.CompileInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompileInfoImpl implements CompileInfoService {

    @Resource
    private CompileInfoMapper compileInfoMapper;

    @Override
    public CompileInfo findCompileInfoBySolutionId(String solutionId) {
        return compileInfoMapper.findCompileInfoBySolutionId(solutionId);
    }
}
