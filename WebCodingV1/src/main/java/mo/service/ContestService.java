package mo.service;

import com.alibaba.druid.sql.visitor.functions.Char;
import mo.entity.po.Contest;
import mo.entity.vo.ContestLinkUser;

import java.util.List;

public interface ContestService {

    /**
     * 根据页码查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return 竞赛集
     */
    List<Contest> findContestsByPageAndPerPage(Integer page, Integer per_page);

    /**
     * 根据公开级别查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param defunct  公开级别
     * @return 竞赛集
     */
    List<Contest> findContestsByPageAndDefunct(Integer page, Integer per_page, Integer[] defunct);

    /**
     * 根据管理员级别查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param rightstr 管理员权限级别
     * @param userId   管理员Id
     * @return 竞赛集
     */
    List<Contest> findContestsByPageFromAdminPrivilege(Integer page, Integer per_page, String rightstr, Integer userId);

    /**
     * 根据管理员级别查询(竞赛-创建者)集合
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param rightstr 管理员权限级别
     * @param userId   管理员Id
     * @return 结果集
     */
    List<ContestLinkUser> findContestAndCreatorByPageFromAdminPrivilege(Integer page, Integer per_page, String rightstr, Integer userId);

    /**
     * 判断指定用户是否有权限操作指定比赛
     *
     * @param userId    用户Id
     * @param contestId 比赛Id
     * @return 结果
     */
    boolean hasAccess(Integer userId, Integer contestId);

    /**
     * 添加题目至竞赛
     *
     * @param problemId 题目Id
     * @param contestId 竞赛Id
     * @return 结果-1=题目已存在 0=添加失败 1=添加成功
     */
    Integer addProblemToContest(Integer problemId, Integer contestId);

    /**
     * 从指定竞赛中删除指定题目
     *
     * @param problemId 题目Id
     * @param contestId 竞赛Id
     * @return 结果-1=题目不存在 0=删除失败 1=删除成功
     */
    Integer deleteProblemFromContest(Integer problemId, Integer contestId);
}
