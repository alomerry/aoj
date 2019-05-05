package mo.service.impl;

import mo.dao.ContestApplyMapper;
import mo.dao.ContestMapper;
import mo.entity.po.ContestApply;
import mo.entity.vo.link.ContestApplyLink;
import mo.service.ContestApplyService;
import mo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestApplyServiceImpl implements ContestApplyService {

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestApplyMapper contestApplyMapper;

    @Resource
    private UserService userService;

    @Override
    public List<ContestApplyLink> getContestApplyByContestId(Integer contestId, int page, int per_page) {
        return makeLink(contestApplyMapper.getContestApplyByCreatorId(contestId, (page - 1) * per_page, per_page));
    }

    @Override
    public int getUncheckedApplyNumberByContestId(int contestId) {
        return contestApplyMapper.getUncheckedContestApplyNumberByContestId(contestId);
    }

    private List<ContestApplyLink> makeLink(List<ContestApply> contestApplies) {
        List<ContestApplyLink> contestApplyLinks = new ArrayList<>(contestApplies.size() + 3);
        for (ContestApply item : contestApplies) {
            contestApplyLinks.add(new ContestApplyLink(item, contestMapper.findContestByContestId(item.getContest_id()), userService.findUserByUserId(item.getUser_id())));
        }
        return contestApplyLinks;
    }

}
