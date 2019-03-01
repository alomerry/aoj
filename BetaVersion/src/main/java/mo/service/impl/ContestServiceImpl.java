package mo.service.impl;

import mo.dao.ContestMapper;
import mo.entity.po.Contest;
import mo.service.ContestService;
import mo.utils.DIYMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

import static mo.permission.PermissionManage.Match_organizer;
import static mo.permission.PermissionManage.isLegalAdmin;

@Service
public class ContestServiceImpl implements ContestService {

    private static final Logger logger = LoggerFactory.getLogger(ContestServiceImpl.class);

    @Resource
    private ContestMapper contestMapper;

    private final static Timestamp ts = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Contest> getPublicContestsByPage(int page, int pageNum) {
        return updateTime(contestMapper.findPublicContestsByPageFromContest((page - 1) * pageNum, pageNum));
    }

    @Override
    public List<Contest> getContestsByPage(int page, int pageNum, String level, Integer user_id) {
        //判断管理员是否是比赛组织者
        if (isLegalAdmin(Match_organizer, level)) {
            logger.debug("用户级别判断完毕，当前用是【比赛组织者】");
            return updateTime(contestMapper.findContestsByPageFromContest((page - 1) * pageNum, page * pageNum));
        } else {
            logger.debug("用户级别判断完毕，当前用不是【比赛组织者】,只能查看本账户创建的比赛");
            return updateTime(contestMapper.findContestsByUserIdAndPageFromContest((page - 1) * pageNum, page * pageNum, user_id));
        }
    }

    @Override
    public List<Contest> getContestsByPageWithOrderBy(int page, int pageNum, String orderby, String sort, String level, Integer user_id) {
        List<Contest> contests = null;
        if (isLegalAdmin(Match_organizer, level)) {
            logger.debug("用户级别判断完毕，当前用是【比赛组织者】");
            contests = contestMapper.findContestsByPageOrderByTypeAndSortFromContest((page - 1) * pageNum, pageNum, orderby, sort);
        } else {
            logger.debug("用户级别判断完毕，当前用不是【比赛组织者】,只能查看本账户创建的比赛");
            contests = contestMapper.findContestsByPageAndCreatorUserIdOrderByTypeAndSortFromContest((page - 1) * pageNum, pageNum, orderby, sort, user_id);
        }

        for (Contest contest : contests) {
            logger.info('\n' + contest.toString() + '\n');
        }
        return updateTime(contests);
    }

    @Override
    public Contest getContestByContestId(int contestId) {
        return contestMapper.findContestByContestIdFromContest(contestId);
    }

    @Override
    public DIYMessage addContestByAdmin(Contest contest) {
        if (!checkContest(contest)) {
            return new DIYMessage(AddContestFail, "比赛添加失败！");
        }
        if (contestMapper.insertContest(contest) > 0) {
            logger.info("添加比赛成功！");
            return new DIYMessage(AddContestSuccess, "添加比赛成功！");
        } else {
            logger.info("插入比赛表失败！");
            return new DIYMessage(AddContestFail, "比赛添加失败！请检查信息！");
        }
    }

    @Override
    public DIYMessage delContestByAdmin(Integer contest_id) {
        if (checkIsRunning(contestMapper.findContestByContestIdFromContest(contest_id))) {
            logger.info("删除竞赛[{}]失败，比赛正在进行", contest_id);
            return new DIYMessage(DeleteContestFail, "该比赛正在进行!");
        } else {
            if (contestMapper.delContestFromContestByContestId(contest_id) > 0) {
                logger.info("删除竞赛[{}]成功", contest_id);
                return new DIYMessage(DeleteContestSuccess, "删除竞赛成功!");
            } else {
                logger.info("删除竞赛[{}]失败", contest_id);
                return new DIYMessage(DeleteContestFail, "删除竞赛失败!");
            }
        }

    }


    private List<Contest> updateTime(List<Contest> list) {
        for (Contest contest : list) {
            contest.updateStatus();
        }
        return list;
    }

    private boolean checkContest(Contest contest) {
        if (contest.getStart_time().after(contest.getEnd_time())) {
            logger.error("添加失败！时间错误！");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkIsRunning(Contest contest) {
        ts.setTime(System.currentTimeMillis());
        return ts.after(contest.getStart_time()) && ts.before(contest.getEnd_time());
    }
}
