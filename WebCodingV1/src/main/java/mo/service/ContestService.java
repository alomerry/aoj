package mo.service;

import mo.entity.po.Contest;
import mo.entity.vo.ContestNumber;
import mo.entity.vo.link.ContestApplyLink;
import mo.entity.vo.link.ContestLinkUser;

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

    /**
     * 查询指定题目是否在指定竞赛中
     *
     * @param contestId 竞赛Id
     * @param problemId 题目Id
     * @return
     */
    boolean isProblemInContest(Integer contestId, Integer problemId);

    /**
     * 新建竞赛
     *
     * @param contest   竞赛实体
     * @param creatorId 创建者Id
     * @return
     */
    boolean createNewContest(Contest contest, Integer creatorId);

    /**
     * 根据竞赛Id查询竞赛
     *
     * @param contest_id 竞赛Id
     * @return 竞赛实体
     */
    Contest findContestByContestId(Integer contest_id);

    /**
     * 查询指定用户创建的竞赛
     *
     * @param creatorId 创建者id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    List<ContestNumber> findContestsByCreatorIdAndPage(Integer creatorId, int page, int per_page);

    /**
     * 更新竞赛
     *
     * @param contest    竞赛
     * @param contest_id 竞赛Id
     * @return
     */
    boolean updateContestByContestId(Contest contest, Integer contest_id);

    /**
     * 查询指定用户是否是指定竞赛的创建者
     *
     * @param user_id   用户Id
     * @param contestId 竞赛Id
     * @return
     */
    boolean isCreator(Integer user_id, Integer contestId);

    /**
     * 查询指定竞赛
     *
     * @param contestId 竞赛Id
     * @return
     */
    ContestLinkUser findContestUserByContestId(Integer contestId);
}
