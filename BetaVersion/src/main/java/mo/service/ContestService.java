package mo.service;

import mo.entity.po.Contest;
import mo.entity.vo.ContestApplyLink;
import mo.entity.vo.ContestProblemLink;
import mo.utils.DIYMessage;

import java.util.List;

public interface ContestService {

    /**
     * 添加比赛成功,申请加入比赛成功
     */
    int AddContestSuccess = 1,DeleteContestSuccess = 1;

    /**
     * 添加比赛失败,申请加入比赛失败
     */
    int AddContestFail = 0,DeleteContestFail = 0;

    /**
     * 按页码获取公开比赛
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @return 比赛集
     */
    List<Contest> getPublicContestsByPage(int page, int pageNum);

    /**
     * 按页码获取比赛
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @param level   用户级别(比赛组织者/比赛创建者)
     * @param user_id 用户Id
     * @return 比赛集
     */
    List<Contest> getContestsByPage(int page, int pageNum, String level, Integer user_id);

    /**
     * 根据指定列按指定排序方式获取竞赛
     *
     * @param page    页数
     * @param pageNum 每页数量
     * @param orderby 指定列
     * @param sort    升降序
     * @param level   用户级别(比赛组织者/比赛创建者)
     * @param user_id 用户Id
     * @return 竞赛集
     */
    List<Contest> getContestsByPageWithOrderBy(int page, int pageNum, String orderby, String sort, String level, Integer user_id);

    /**
     * 按主键获取比赛
     *
     * @param contestId 比赛号
     * @return 比赛
     */
    Contest getContestByContestId(int contestId);

    /**
     * 管理员插入比赛信息
     *
     * @param contest 比赛实体
     * @return 影响行数
     */
    DIYMessage addContestByAdmin(Contest contest);

    /**
     * 管理员确认删除指定的比赛
     * 若比赛正在进行，则无法删除
     * @param contest_id 比赛号
     * @return 删除结果
     */
    DIYMessage delContestByAdmin(Integer contest_id);
}
