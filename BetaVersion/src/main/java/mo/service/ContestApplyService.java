package mo.service;

import mo.entity.po.ContestApply;
import mo.entity.vo.ContestApplyLink;
import mo.entity.vo.ContestProblemLink;
import mo.utils.DIYMessage;

import java.util.List;

public interface ContestApplyService {

    /**
     * 添加比赛成功,申请加入比赛成功
     */
    int AddContestSuccess = 1, applySuccess = 1;

    /**
     * 添加比赛失败,申请加入比赛失败
     */
    int AddContestFail = 0, applyFailed = 0;

    /**
     * 根据用户Id和比赛号查询申请表
     *
     * @param contest_id 比赛号
     * @param user_id    用户Id
     * @return 申请表VO实体
     */
    ContestApplyLink findContestApplyLink(Integer contest_id, Integer user_id);

    /**
     * 获取用户自己参加的比赛
     *
     * @param user_id 用户Id
     * @return
     */
    List<ContestApplyLink> findMineApplyCompetition(Integer user_id);

    /**
     * 根据信息查找指定权限的申请表
     *
     * @param user_id 管理者Id
     * @param page    页码
     * @param pageNum 每页数量
     * @param type    排序列
     * @param sort    升降序
     * @param level   用户级别
     * @return 申请集
     */
    List<ContestApplyLink> findApplication(Integer user_id, int page, int pageNum, String type, String sort, String level);

    List<ContestApplyLink> findApplicationFromContest(Integer user_id, int page, int pageNum, boolean permissionFlag);

    List<ContestApplyLink> findApplicationFromContest(Integer user_id, int page, int pageNum, String type, String sort, boolean permissionFlag);

    /**
     * 管理员同意加入比赛，更新申请状态
     *
     * @param user_id    申请者Id
     * @param contest_id 比赛Id
     * @return 自定义消息
     */
    DIYMessage updateContestApplyStatus(Integer contest_id, Integer user_id);

    /**
     * 管理员拒绝用户加入比赛，删除申请数据
     *
     * @param user_id    用户Id
     * @param contest_id 比赛号
     * @return 自定义消息
     */
    DIYMessage deleteContestApply(Integer contest_id, Integer user_id);

    /**
     * 用户申请加入比赛
     *
     * @param contest_id 比赛号
     * @param user_id    用户Id
     * @return 自定义消息
     */
    DIYMessage ApplyContest(Integer contest_id, Integer user_id);

    /**
     * 根据竞赛Id查看该竞赛的申请表
     * @param contest_id 竞赛号
     * @return 申请集
     */
    List<ContestApplyLink> findContestApplyLinkByContestId(Integer contest_id);
}
