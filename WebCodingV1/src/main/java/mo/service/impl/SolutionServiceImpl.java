package mo.service.impl;

import mo.dao.ProblemMapper;
import mo.dao.SolutionMapper;
import mo.dao.UserMapper;
import mo.entity.po.Solution;
import mo.entity.po.SourceCode;
import mo.entity.vo.link.SolutionLink;
import mo.exception.ServiceException;
import mo.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {

    private static final Logger logger = LoggerFactory.getLogger(SolutionServiceImpl.class);

    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProblemMapper problemMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean insertIntoNewSolution(Solution solution, SourceCode sourceCode) {
        String solution_id = solutionMapper.getUniqueSolutionId();
        int res = solutionMapper.insertOneItemIntoSolution(solution_id, solution.getProblem_id(),
                solution.getUser_id(),
                solution.getCreate_at().toString(),
                solution.getResult(),
                solution.getLanguage(),
                solution.getIp(),
                solution.getCode_lenght());
        if (res > 0) {
            if (problemMapper.addProblemOneSubmit(solution.getProblem_id()) > 0 && solutionMapper.insertCodeIntoSource(solution_id, sourceCode.getSource()) > 0) {
                return true;
            } else {
                logger.error("source code 插入失败");
                throw new ServiceException("source code 插入失败");
            }
        } else {
            logger.error("solution 插入失败");
            throw new ServiceException("solution 插入失败");
        }
    }

    @Override
    public List<SolutionLink> getSolutions(int page, int per_page) {
        return makeLink(solutionMapper.findSolutionOrderByJudgeTimeAndPage((page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getSolutions(int state, int page, int per_page) {
        return makeLink(solutionMapper.findSolutionOrderByJudgeTimeAndPage((page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getSolutionsByUserId(Integer userId, int page, int per_page) {
        return makeLink(solutionMapper.findSolutionByUserIdOrderByJudgeTimeAndPage(userId, (page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getContestSolutions(Integer contestId, int page, int per_page) {
        return makeLink(solutionMapper.findContestSolutionByContestIdOrderByJudgeTimeAndPage(contestId, (page - 1) * per_page, per_page));
    }

    private List<SolutionLink> makeLink(List<Solution> solution) {
        List<SolutionLink> solutionLinks = new ArrayList<>(solution.size() + 3);
        for (Solution s : solution) {
            solutionLinks.add(new SolutionLink(userMapper.findUserIdUserNameUserNickNameByUserId(s.getUser_id()),
                    s, problemMapper.findProblemIdProblemTitleByProblemId(s.getProblem_id())));
        }
        return solutionLinks;
    }
}
