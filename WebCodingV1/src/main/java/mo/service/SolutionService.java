package mo.service;

import mo.entity.po.Solution;

public interface SolutionService {

    /**
     * 插入新Solution
     *
     * @param solution 解题实体
     * @return solutionId
     */
    Long insertIntoNewSolution(Solution solution);
}
