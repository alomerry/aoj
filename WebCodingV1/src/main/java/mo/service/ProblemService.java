package mo.service;

import mo.entity.po.Problem;
import mo.entity.vo.ProblemLink;

import java.util.List;

public interface ProblemService {

    /**
     * 根据Id查找指定题目
     *
     * @param problem_id 题目Id
     * @return 题目实体
     */
    Problem findProblemByProblemId(Integer problem_id);

    /**
     * 根据公开级别查询题目简单信息
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param defunct  公开级别
     * @return 题目集
     */
    List<Problem> findSimpleProblemsByDefunct(String defunct, Integer page, Integer per_page);

    /**
     * 根据公开级别查询题目简单信息
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param user_id  创题者Id
     * @param defunct  公开级别
     * @return 题目集
     */
    List<Problem> findSimpleProblemsByDefunct(String defunct, Integer user_id, Integer page, Integer per_page);


    /**
     * 根据公开级别查询题目简单信息
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param user_id  创题者Id
     * @param defunct  公开级别
     * @return 题目集
     */
    List<ProblemLink> findSimpleProblemLinksByDefunct(String defunct, Integer user_id, Integer page, Integer per_page);

    /**
     * 根据公开级别查询题目信息
     *
     * @param defunct  公开级别
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    List<Problem> findProblemsByPageAndPerPage(String defunct, Integer page, Integer per_page);

    /**
     * 查询创建者题目和指定公开级别题目集
     *
     * @param defunct  公开级别
     * @param user_id  创题者Id
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    List<Problem> findProblemsByPageAndPerPage(String defunct, Integer user_id, Integer page, Integer per_page);

    /**
     * 查询指定比赛的题目集
     *
     * @param page       页码
     * @param per_page   每页数量
     * @param contest_id 竞赛Id
     * @return 题目集
     */
    List<ProblemLink> findSimpleProblemsByPageAndContestId(Integer page, Integer per_page, Integer contest_id);

    /**
     * 判断指定题目的公开级别
     * @param problemId 题目Id
     * @return
     */
    boolean isAbsolutePrivateProblem(Integer problemId);
}
