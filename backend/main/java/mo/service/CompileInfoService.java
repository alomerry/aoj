package mo.service;

import mo.entity.po.main.CompileInfo;

public interface CompileInfoService {
    /**
     * 查询编译错误信息
     * @param solutionId 提交编号
     * @return
     */
    CompileInfo findCompileInfoBySolutionId(String solutionId);
}
