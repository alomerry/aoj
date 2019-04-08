package mo.service;

import mo.entity.po.Solution;

public interface SolutionService {

    /**
     * 插入新Solution
     *
     * @param solution 解题实体
     * @return 插入结果
     */
    Integer insertIntoNewSolution(Solution solution);
}
