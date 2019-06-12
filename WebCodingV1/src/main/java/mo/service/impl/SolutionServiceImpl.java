package mo.service.impl;

import mo.dao.main.ProblemMapper;
import mo.dao.main.SolutionMapper;
import mo.dao.main.UserMapper;
import mo.entity.po.main.Solution;
import mo.entity.po.main.SourceCode;
import mo.entity.po.main.User;
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
//            if (problemMapper.addProblemOneSubmit(solution.getProblem_id()) > 0 && solutionMapper.insertCodeIntoSource(solution_id, sourceCode.getSource()) > 0) {
            if (solutionMapper.insertCodeIntoSource(solution_id, sourceCode.getSource()) > 0) {
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
        return makeLink(solutionMapper.findSolutionByResultOrderByJudgeTimeAndPage(state, (page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getSolutionsByUserId(Integer userId, int page, int per_page) {
        return makeLink(solutionMapper.findSolutionByUserIdOrderByJudgeTimeAndPage(userId, (page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getSolutionsByUserId(int state, Integer userId, int page, int per_page) {
        return makeLink(solutionMapper.findSolutionByUserIdAndResultOrderByJudgeTimeAndPage(state, userId, (page - 1) * per_page, per_page));
    }

    @Override
    public List<SolutionLink> getContestSolutions(Integer contestId, int page, int per_page) {
        return makeLink(solutionMapper.findContestSolutionByContestIdOrderByJudgeTimeAndPage(contestId, (page - 1) * per_page, per_page));
    }

    @Override
    public Integer getUserSolutionTotalNumber(Integer userId) {
        return solutionMapper.getUserSolutionTotalNumber(userId);
    }

    @Override
    public Integer getUserSolutionTotalNumber(Integer userId, int state) {
        return solutionMapper.getUserStateSolutionTotalNumber(userId, state);
    }

    @Override
    public Integer getSolutionTotalNumber() {
        return solutionMapper.getSolutionTotalNumber();
    }

    @Override
    public Integer getSolutionTotalNumber(int state) {
        return solutionMapper.getStateSolutionTotalNumber(state);
    }

    @Override
    public boolean checkIsCreatorOfSolution(String solutionId, Integer user_id) {
        return solutionMapper.checkIsSolutionCreator(solutionId,user_id)>0;
    }

    @Override
    public int rejudge(String solutionId) {
        return solutionMapper.rejudge(solutionId);
    }

    @Override
    public List<User> getTotalSolvedRank(int page, int per_page) {
        return solutionMapper.getTotalSolvedRank((page-1)*per_page,per_page);
    }

    @Override
    public List<User> getPercentSolvedRank(int page, int per_page) {
        return solutionMapper.getPercentSolvedRank((page-1)*per_page,per_page);
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
