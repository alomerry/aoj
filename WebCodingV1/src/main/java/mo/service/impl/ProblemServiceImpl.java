package mo.service.impl;

import mo.dao.ContestProblemMapper;
import mo.dao.ProblemMapper;
import mo.dao.UserMapper;
import mo.entity.po.ContestProblem;
import mo.entity.po.Problem;
import mo.entity.po.User;
import mo.entity.vo.ProblemLink;
import mo.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Override
    public Problem findProblemByProblemId(Integer problem_id) {
        return problemMapper.findProblemByProblemId(problem_id);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsByDefunctAndPage(defunct, (page - 1) * per_page, per_page);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
    }

    @Override
    public List<ProblemLink> findSimpleProblemLinksByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        User user = userMapper.findUserByUserId(user_id);
        List<Problem> problems = problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
        List<ProblemLink> problemLinks = new ArrayList<>(problems.size() + 3);
        for (Problem p : problems) {
            if (user.getUser_id().equals(p.getCreate_by())) {
                problemLinks.add(new ProblemLink(p, user));
            } else {
                problemLinks.add(new ProblemLink(p, userMapper.findUserByUserId(p.getCreate_by())));
            }
        }
        return problemLinks;
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer page, Integer per_page) {
        return problemMapper.findProblemsByDefunctAndPage(defunct, (page - 1) * per_page, per_page);
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
    }

    @Override
    public List<ProblemLink> findSimpleProblemsByPageAndContestId(Integer page, Integer per_page, Integer contest_id) {
        //TODO 是否能优化
        List<ContestProblem> contestProblems = contestProblemMapper.findContestProblemByPageAndContestId((page - 1) * per_page, per_page, contest_id);
        List<ProblemLink> problemLinks = new ArrayList<>(contestProblems.size() + 5);
        Problem problem = null;
        for (ContestProblem cp : contestProblems) {
            problem = problemMapper.findProblemByProblemId(cp.getProblem_id());
            problemLinks.add(new ProblemLink(problem, userMapper.findUserByUserId(problem.getCreate_by())));
        }
        return problemLinks;
    }

    @Override
    public boolean isAbsolutePrivateProblem(Integer problemId) {
        char level = problemMapper.findProblemPublicLevelByProblemId(problemId);
        logger.info("判断题目[{}]的公开级别:[{}]", problemId, level);
        switch (level) {
            //屏蔽-公开-部分公开-绝对私有0/1/2/3
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
            case 2: {
                return false;
            }
            case 3: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
