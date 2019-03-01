package mo.service;

import mo.entity.po.Problem;

import java.util.List;

public interface ProblemService {

    /**
     * 根据Id查找指定问题
     *
     * @param problem_id 问题Id
     * @return 问题实体
     */
    Problem findProblemByProblemId(Integer problem_id);

    /**
     * 根据公开级别查询问题
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @param defunct 公开级别
     * @return 问题集
     */
    List<Problem> findSimpleProblemsByDefunct(String defunct, int page, int pageNum);

    List<Problem> findProblemsByPageAndPerPage(String defunct, int page, int per_page);
}
