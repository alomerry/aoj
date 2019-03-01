package mo.service.impl;

import mo.dao.*;
import mo.entity.po.Contest;
import mo.entity.po.ContestApply;
import mo.entity.vo.ContestApplyLink;
import mo.service.ContestApplyService;
import mo.utils.DIYMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static mo.permission.PermissionManage.Match_organizer;
import static mo.permission.PermissionManage.isLegalAdmin;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Service
public class ContestApplyServiceImpl implements ContestApplyService {

    private final static Logger logger = LoggerFactory.getLogger(ContestApplyServiceImpl.class);


    @Resource
    private ContestMapper contestMapper;


    @Resource
    private UserMapper userMapper;


    @Resource
    private ContestApplyMapper contestApplyMapper;


    @Override
    public ContestApplyLink findContestApplyLink(Integer contest_id, Integer user_id) {
        ContestApply contestApply = contestApplyMapper.findApplyByUserIdAndContestId(user_id, contest_id);
        return getContestApplyItem(contestApply.getUser_id(), contestApply.getContest_id(), contestApply.isStatus());
    }


    private List<ContestApplyLink> fillLink(List<ContestApply> applies) {
        return fillLink(applies, true);
    }

    /**
     * 子查询组成vo
     *
     * @param applies 申请集
     * @param flag    是否要查找用户
     * @return
     */
    private List<ContestApplyLink> fillLink(List<ContestApply> applies, boolean flag) {
        List<ContestApplyLink> list = new ArrayList<>((int) (applies.size() * 1.3));
        int i = 0, j = applies.size();
        for (; i < j; i++) {
            ContestApplyLink item = getContestApplyItem(flag ? applies.get(i).getUser_id() : null, applies.get(i).getContest_id(), applies.get(i).isStatus());
            item.getContest().updateStatus();
            list.add(item);
        }
        return list;
    }


    private ContestApplyLink getContestApplyItem(Integer user_id, Integer contest_id, boolean status) {
        if (user_id == null) {
            return new ContestApplyLink(null, contestMapper.findContestByContestIdFromContest(contest_id), status ? 1 : 0);
        } else {
            return new ContestApplyLink(userMapper.findUserByUserId(user_id), contestMapper.findContestByContestIdFromContest(contest_id), status ? 1 : 0);
        }
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public List<ContestApplyLink> findMineApplyCompetition(Integer user_id) {
        return fillLink(contestApplyMapper.findOneUserContestsFromContestByUserId(user_id), false);
    }


    @Override
    public List<ContestApplyLink> findApplicationFromContest(Integer user_id, int page, int pageNum, boolean permissionFlag) {
        return permissionFlag ? fillLink(contestApplyMapper.findContestApplysByPageFromContestApply((page - 1) * pageNum, pageNum)) : fillLink(contestApplyMapper.findCreatorContestApplysByCreatorUserIdFromContestApply(user_id, (page - 1) * pageNum, page * pageNum));
    }

    @Override
    public List<ContestApplyLink> findApplicationFromContest(Integer user_id, int page, int pageNum, String type, String sort, boolean permissionFlag) {
        return permissionFlag ? fillLink(contestApplyMapper.findContestApplysByPageOrderByTypeAndSortFromContestApply((page - 1) * pageNum, pageNum, type, sort)) : fillLink(contestApplyMapper.findCreatorContestApplysByCreatorUserIdOrderByTypeAndSortFromContestApply(user_id, (page - 1) * pageNum, pageNum, type, sort));
    }

    @Override
    public List<ContestApplyLink> findApplication(Integer user_id, int page, int pageNum, String type, String sort, String level) {
        boolean flag = isLegalAdmin(Match_organizer, level);
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(sort)) {
            return findApplicationFromContest(user_id, page, pageNum, flag);
        } else {
            return findApplicationFromContest(user_id, page, pageNum, type, sort, flag);
        }
    }
    //TODO 重载，添加或删除"多"个人的申请信息

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DIYMessage deleteContestApply(Integer contest_id, Integer user_id) {
        if (contestApplyMapper.deleteContestApplyFromContestApplyByUserIdAndContestId(user_id, contest_id) > 0) {
            logger.info("删除用户Id[{}]申请加入比赛Id[{}]成功！", user_id, contest_id);
            contestMapper.updateContestNowDecreaseFromContestByContestId(contest_id, 1);
            return new DIYMessage(applySuccess, "操作成功");
        } else {
            logger.info("删除用户Id[{}]申请加入比赛Id[{}]成功！", user_id, contest_id);
            return new DIYMessage(applyFailed, "操作失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DIYMessage updateContestApplyStatus(Integer contest_id, Integer user_id) {
        if (contestApplyMapper.checkContestApplyHasBeenAcceptIsExsitByUserIdAndContestIdFromContestApply(user_id, contest_id) > 0) {//检查管理员是否已经确认过
            logger.info("确认用户Id[{}]申请加入比赛Id[{}]失败！", user_id, contest_id);
            return new DIYMessage(applyFailed, "操作失败！请勿重复确认申请");
        } else if (contestApplyMapper.updateContestApplyByUserIdAndContestId(user_id, contest_id) > 0) {//确认申请成功，修改参赛人数
            //TODO 人数加一
            contestMapper.updateContestNowIncreaseFromContestByContestId(contest_id, 1);
            logger.info("确认用户Id[{}]申请加入比赛Id[{}]成功！", user_id, contest_id);
            return new DIYMessage(applySuccess, "操作成功！");
        } else {
            logger.info("确认用户Id[{}]申请加入比赛Id[{}]失败！", user_id, contest_id);
            return new DIYMessage(applyFailed, "操作失败！");
        }
    }

    @Override
    public DIYMessage ApplyContest(Integer contest_id, Integer user_id) {
        //检测课程是否可报名，开始是否超时
        Contest contest = contestMapper.findContestByContestIdFromContest(contest_id);
        if (new Timestamp(System.currentTimeMillis()).after(contest.getStart_time())) {
            logger.error("申请比赛失败！");
            return new DIYMessage(applyFailed, "比赛已开始，无法报名！");
        } else if (contest.getPrivates() == 1) {
            logger.error("申请比赛失败！");
            return new DIYMessage(applyFailed, "比赛非公开，无法报名！");
        } else if (!contest.isAccess()) {
            logger.error("申请比赛失败！");
            return new DIYMessage(applyFailed, "报名截止，无法报名！");
        } else if (contestMapper.findRestNumFromContestByContestId(contest_id) <= 0) {
            logger.error("申请比赛失败！");
            return new DIYMessage(applyFailed, "人数已满，无法报名！");
        } else {
            //判断已报名
            if (contestApplyMapper.checkContestApplyHasBeenAcceptIsExsitByUserIdAndContestIdFromContestApply(user_id, contest_id) > 0) {
                logger.error("申请比赛失败！");
                return new DIYMessage(applyFailed, "您已报名，请勿重复提交！");
            } else {
                logger.info("申请比赛成功！");
                contestApplyMapper.insertContestApply(user_id, contest_id);
                return new DIYMessage(applySuccess, "报名成功！");
            }
        }
    }

    @Override
    public List<ContestApplyLink> findContestApplyLinkByContestId(Integer contest_id) {
        return fillLink(contestApplyMapper.findContestApplysFromContestApplyByContestId(contest_id), true);
    }


}
