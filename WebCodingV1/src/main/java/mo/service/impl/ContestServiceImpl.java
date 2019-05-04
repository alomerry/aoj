package mo.service.impl;

import mo.core.Permission;
import mo.core.PermissionManager;
import mo.dao.ContestMapper;
import mo.dao.ContestProblemMapper;
import mo.dao.PrivilegeMapper;
import mo.dao.UserMapper;
import mo.entity.po.Contest;
import mo.entity.vo.link.ContestLinkUser;
import mo.exception.ServiceException;
import mo.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    private static final Logger logger = LoggerFactory.getLogger(ContestServiceImpl.class);

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Contest> findContestsByPageAndPerPage(Integer page, Integer per_page) {
        return contestMapper.findContestByPageAndPerPage((page - 1) * per_page, per_page);
    }

    @Override
    public List<Contest> findContestsByPageAndDefunct(Integer page, Integer per_page, Integer[] defunct) {
        return contestMapper.findContestsByPageAndDefunct((page - 1) * per_page, per_page, getDefunct(defunct));
    }

    @Override
    public List<Contest> findContestsByPageFromAdminPrivilege(Integer page, Integer per_page, String rightstr, Integer userId) {
        if (!PermissionManager.isAdmin(rightstr)) {
            logger.info("用户权限[{}]，权限不足，查询失败", rightstr);
            throw new ServiceException();
        }

        Permission[] contestLevel = {Permission.Contest_organizer};
        if (PermissionManager.isOneLegalAdmin(contestLevel, rightstr)) {
            return contestMapper.findContestByPageAndPerPage((page - 1) * per_page, per_page);
        } else {
            return contestMapper.findContestByPageAndDefunctWithOwnContest((page - 1) * per_page, per_page, "(0)", userId);
        }
    }

    @Override
    public List<ContestLinkUser> findContestAndCreatorByPageFromAdminPrivilege(Integer page, Integer per_page, String rightstr, Integer userId) {
        List<Contest> contests = findContestsByPageFromAdminPrivilege(page, per_page, rightstr, userId);
        List<ContestLinkUser> contestLinkUsers = new ArrayList<>(contests.size() + 5);
        for (Contest c : contests) {
            contestLinkUsers.add(new ContestLinkUser(c, userMapper.findUserIdUserNameUserNickNameByUserId(c.getUser_id())));
        }
        return contestLinkUsers;
    }

    @Override
    public boolean hasAccess(Integer userId, Integer contestId) {
        Integer creator_id = contestMapper.findCreatorByContestId(contestId);

        logger.info("竞赛[{}]的创建者为[{}]", contestId, creator_id);
        if (creator_id == userId) {
            logger.info("操作者[{}]是创建者，有操作权限", contestId);
            return true;
        } else {
            String level = privilegeMapper.findRightStrByUserId(userId);
            logger.info("操作者不是创建者，判断管理员级别:[{}]", level);
            if (PermissionManager.isAdmin(level) && PermissionManager.isLegalAdmin(Permission.Contest_organizer, level)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Integer addProblemToContest(Integer problemId, Integer contestId) {
        if (contestProblemMapper.findCountsByContestIdAndProblemId(contestId, problemId) > 0) {
            return -1;
        } else {
            return contestProblemMapper.addProblemToContest(contestId, problemId);
        }
    }

    @Override
    public Integer deleteProblemFromContest(Integer problemId, Integer contestId) {
        if (contestProblemMapper.findCountsByContestIdAndProblemId(contestId, problemId) <= 0) {
            return -1;
        } else {
            return contestProblemMapper.deletProblemFromContest(contestId, problemId);
        }
    }

    @Override
    public boolean isProblemInContest(Integer contestId, Integer problemId) {
        return contestMapper.findProblemFromContestByProblemIdAndContestId(contestId, problemId) > 0;
    }

    @Override
    public boolean createNewContest(Contest contest, Integer creatorId) {
        return contestMapper.insertNewContest(contest, creatorId) > 0;
    }

    @Override
    public Contest findContestByContestId(Integer contest_id) {
        return contestMapper.findContestByContestId(contest_id);
    }

    /**
     * 将指定公开级别的竞赛集属性整合成string
     *
     * @param defunct 公开级别
     * @return 整合公开级别
     */
    private String getDefunct(Integer[] defunct) {
        StringBuilder sbf = new StringBuilder();
        boolean x = true;
        for (Integer i : defunct) {
            if (x) {
                sbf.append("(").append(i);
                x = false;
            } else {
                sbf.append(",").append(i);
            }
        }
        return sbf.append(")").toString();
    }
}
