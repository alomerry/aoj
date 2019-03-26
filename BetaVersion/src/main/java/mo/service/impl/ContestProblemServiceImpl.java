package mo.service.impl;

import mo.dao.ContestProblemMapper;
import mo.dao.ProblemMapper;
import mo.entity.po.ContestProblem;
import mo.entity.vo.ContestProblemLink;
import mo.service.ContestProblemService;
import mo.utils.DIYMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestProblemServiceImpl implements ContestProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ContestProblemServiceImpl.class);

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private ProblemMapper problemMapper;

    @Override
    public List<ContestProblemLink> findCompetitionProblems(Integer contest_id) {
        List<ContestProblemLink> problems = new ArrayList<>();
        for (ContestProblem c : contestProblemMapper.findCompetitionProblemsByContestIdFromContestProblem(contest_id)) {
            ContestProblemLink tmp = new ContestProblemLink(null, problemMapper.findProblemByProblemId(c.getProblem_id()), c.getTitle(), c.getNum());
            problems.add(tmp);
        }
        return problems;
    }

    //TOD 禁止添加绝对私有的问题
    @Override
    public DIYMessage addProblemToContestByContestIdAndProblemId(Integer contest_id, Integer problem_id) {
        logger.info("contestId[{}],problemId[{}]", contest_id, problem_id);
        return contestProblemMapper.insertContestProblemByContestIdAndProblemId(contest_id, problem_id, contestProblemMapper.findContestMaxNumByContestIdFromContestProblem(contest_id) + 1, problemMapper.findProblemTitleByProblemIdFromProblems(problem_id)) > 0 ? new DIYMessage(insertSuccess, "添加题目成功！") : new DIYMessage(insertFailed, "添加题目失败！");
    }

    @Override
    public DIYMessage delProblemFromContestByContestIdAndProblemId(Integer contest_id, Integer problem_id) {
        logger.info("contestId[{}],problemId[{}]", contest_id, problem_id);
        return contestProblemMapper.delProblemFromContestByContestIdAndProblemId(contest_id, problem_id) > 0 ? new DIYMessage(delSuccess, "删除成功!") : new DIYMessage(delFailed, "删除失败!");
    }

    @Override
    public DIYMessage changeProblemNumFromContestByContestIdAndProblemId(Integer contest_id, Integer problem_id, int num) {
        logger.info("contestId[{}],problemId[{}]", contest_id, problem_id);
        return contestProblemMapper.changeProblemNumFromContestByContestIdAndProblemId(num, contest_id, problem_id) > 0 ? new DIYMessage(delSuccess, "修改成功!") : new DIYMessage(delFailed, "修改失败!");
    }
}
