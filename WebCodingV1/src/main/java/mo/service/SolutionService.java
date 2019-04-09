package mo.service;

import mo.entity.po.Solution;
import mo.entity.po.SourceCode;
import mo.entity.vo.SolutionLink;

import java.util.List;

public interface SolutionService {

    /**
     * 插入新Solution
     *
     * @param solution   解题实体
     * @param sourceCode 源代码实体
     * @return 是否插入成功
     */
    boolean insertIntoNewSolution(Solution solution, SourceCode sourceCode);

    /**
     * 查询solutions
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return solutions
     */
    List<SolutionLink> getSolutions(int page, int per_page);
}
