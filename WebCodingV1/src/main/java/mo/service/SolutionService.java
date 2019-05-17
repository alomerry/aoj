package mo.service;

import mo.entity.po.Solution;
import mo.entity.po.SourceCode;
import mo.entity.vo.link.SolutionLink;

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

    /**
     * 根据状态查询提交
     *
     * @param state    状态
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<SolutionLink> getSolutions(int state, int page, int per_page);

    /**
     * 查询指定用户的提交
     *
     * @param userId   用户Id
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<SolutionLink> getSolutionsByUserId(Integer userId, int page, int per_page);

    /**
     * 查询指定用户的指定状态的提交
     *
     * @param state    状态
     * @param userId   用户Id
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<SolutionLink> getSolutionsByUserId(int state, Integer userId, int page, int per_page);

    /**
     * 查询竞赛中的提交
     *
     * @param contestId 竞赛Id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    List<SolutionLink> getContestSolutions(Integer contestId, int page, int per_page);
}
