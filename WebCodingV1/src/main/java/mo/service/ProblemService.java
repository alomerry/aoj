package mo.service;

import mo.entity.po.Problem;
import mo.entity.po.Tag;
import mo.entity.vo.link.ProblemLink;
import mo.exception.ServiceException;

import java.io.File;
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
     * 判断指定题目的公开级别是否为隐私(即无法被非创建者修改)
     *
     * @param problemId 题目Id
     * @return 是否为隐私
     */
    boolean isAbsolutePrivateProblem(Integer problemId);

    /**
     * 判断指定题目的公开级别是否为禁用
     *
     * @param problemId 题目Id
     * @return 是否为禁用
     */
    boolean isDisabledProblem(Integer problemId);

    /**
     * 判断指定用户是否是指定题目的创建者
     *
     * @param problemId 题目Id
     * @param userId    用户Id
     * @return 是创建者/非创建者
     */
    boolean isProblemCreator(Integer problemId, Integer userId);

    /**
     * 删除指定题目
     *
     * @param problemId 题目Id
     * @param underDel  待删文件
     * @return 是否删除成功 0数据库删除失败 -1文件删除失败 1删除成功
     */
    int deleteProblemByProblemId(Integer problemId, File underDel);

    /**
     * 新建题目
     *
     * @param problem 题目实体
     * @param tags    标签集合
     * @param user_id 创题者Id
     * @return 题目主键
     */
    Integer insertNewProblemAndTags(Problem problem, List<Tag> tags, Integer user_id) throws ServiceException;

    /**
     * 更新题目信息
     *
     * @param problem 题目实体
     * @return
     * @throws ServiceException
     */
    boolean updateProblemInfo(Problem problem) throws ServiceException;

    /**
     * 根据标签查询题目
     *
     * @param tag_id   标签Id
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<Problem> findSimpleProblemsByTagId(Integer tag_id, int page, int per_page);

    /**
     * 更新题目标签
     *
     * @param tags       标签集合
     * @param problem_id 待更新的题目Id
     * @return
     */
    boolean updateProblemTags(List<Tag> tags, Integer problem_id);
}
