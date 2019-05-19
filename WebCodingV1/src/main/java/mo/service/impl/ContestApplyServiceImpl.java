package mo.service.impl;

import mo.dao.ContestApplyMapper;
import mo.dao.ContestMapper;
import mo.entity.po.Contest;
import mo.entity.po.ContestApply;
import mo.entity.vo.link.ContestApplyLink;
import mo.exception.ServiceException;
import mo.service.ContestApplyService;
import mo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestApplyServiceImpl implements ContestApplyService {

    private static final Logger logger = LoggerFactory.getLogger(ContestApplyServiceImpl.class);

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestApplyMapper contestApplyMapper;

    @Resource
    private UserService userService;

    @Override
    public List<ContestApplyLink> getContestAppliesByContestId(Integer contestId, int page, int per_page) {
        return makeLink(contestApplyMapper.getContestAppliesByCreatorId(contestId, (page - 1) * per_page, per_page));
    }

    @Override
    public int getUncheckedApplyNumberByContestId(int contestId) {
        return contestApplyMapper.getUncheckedContestApplyNumberByContestId(contestId);
    }

    @Override
    public ContestApply findContestApplyById(Integer id) {
        return contestApplyMapper.findContestApplyById(id);
    }

    @Override
    @Transactional
    public boolean updateContestApply(Integer id, Integer status) {
        /**
         * 1.判断更新类别
         * 2.添加-人数是否已满
         *  2.1.未满添加
         *  2.2.满报错
         * 3.拒绝-该申请是否已被添加
         *  3.1.未被添加，直接拒绝
         *  3.2.已被添加，拒绝，减少参赛人数
         */
        ContestApply contestApply = contestApplyMapper.findContestApplyById(id);
        Contest contest = contestMapper.findContestByContestId(contestApply.getContest_id());
        if (status == 1) {
            if (contest.getNow() >= contest.getMax()) {
                logger.info("人数已满[{}]", contest.getMax());
            } else {
                if (contestMapper.addContestNowNumberByContestId(contest.getContest_id()) > 0 && contestApplyMapper.updateContestApplyStatusById(id, status) > 0) {
                    return true;
                } else {
                    throw new ServiceException();
                }
            }
        } else if (status == 2) {
            if (contestApply.getStatus() == 0 || contestApply.getStatus() == 2) {
                if (contestApplyMapper.updateContestApplyStatusById(id, status) > 0) {
                    return true;
                } else {
                    throw new ServiceException();
                }
            } else {
                if (contestApplyMapper.updateContestApplyStatusById(id, status) > 0 && contestMapper.delContestNowNumberByContestId(contest.getContest_id()) > 0) {
                    return true;
                } else {
                    throw new ServiceException();
                }
            }
        }
        return false;
    }

    @Override
    public boolean applyContest(Integer user_id, Integer contest_id) {
        return contestApplyMapper.insertNewContestApply(contest_id, user_id) > 0;
    }

    @Override
    public boolean checkExistByContestIdAndUserId(Integer user_id, Integer contest_id) {
        return contestApplyMapper.getNumbersByContestIdAndUserId(contest_id, user_id) > 0;
    }

    @Override
    public int getContestApplyTotalNumber(int contest_id) {
        return contestApplyMapper.getContestApplyNumberByContestId(contest_id);
    }

    private List<ContestApplyLink> makeLink(List<ContestApply> contestApplies) {
        List<ContestApplyLink> contestApplyLinks = new ArrayList<>(contestApplies.size() + 3);
        for (ContestApply item : contestApplies) {
            contestApplyLinks.add(new ContestApplyLink(item, contestMapper.findContestByContestId(item.getContest_id()), userService.findUserByUserId(item.getUser_id())));
        }
        return contestApplyLinks;
    }

}
