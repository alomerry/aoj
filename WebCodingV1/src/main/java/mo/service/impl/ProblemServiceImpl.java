package mo.service.impl;

import mo.dao.*;
import mo.entity.po.ContestProblem;
import mo.entity.po.Problem;
import mo.entity.po.Tag;
import mo.entity.po.User;
import mo.entity.vo.ProblemLink;
import mo.exception.ServiceException;
import mo.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.resources.da.FormatData_da;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ProblemTagMapper problemTagMapper;

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
        ProblemLink pl = null;
        for (Problem p : problems) {
//            if (user.getUser_id().equals(p.getCreate_by())) {
//                problemLinks.add(new ProblemLink(p, user));
//            } else {
//            }
            problemLinks.add(new ProblemLink(p, userMapper.findUserByUserId(p.getCreate_by())));
        }
        logger.info("problemLink[{}]", problemLinks);
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
        return switchProblemLevel(new int[]{3}, level);
    }

    @Override
    public boolean isDisabledProblem(Integer problemId) {
        char level = problemMapper.findProblemPublicLevelByProblemId(problemId);
        logger.info("判断题目[{}]的公开级别:[{}]", problemId, level);
        return switchProblemLevel(new int[]{0}, level);
    }

    @Override
    public boolean isProblemCreator(Integer problemId, Integer userId) {
        return userId.equals(problemMapper.findCreatorIdByProblemId(problemId));
    }

    @Override
    public boolean deleteProblemByProblemId(Integer problemId) {
        return problemMapper.deleteProblemByPorblemId(problemId) > 0;
    }

    @Override
    @Transactional
    public Integer insertNewProblemAndTags(Problem problem, List<Tag> tags, Integer user_id) throws ServiceException {
        problem.setCreated_at(new Timestamp(System.currentTimeMillis()));
        if (problemMapper.insertProblem(problem, user_id) > 0) {
            problem.setProblem_id(problemMapper.findLastInsertId());
            logger.info("题目新建成功!题目Id[{}]", problem.getProblem_id());
            if (tags != null) {
                Integer id = 0;
                for (Tag tag : tags) {
                    tag.setTag_id(tagMapper.findTagByTagName(tag.getTagname()));
                    if (tag.getTag_id() == null) {//tag不存在，新建tag
                        tagMapper.insertTag(tag.getTagname());
                        tag.setTag_id(tagMapper.findLastInsertId());
                        logger.info("标签新建成功!标签Id[{}]", tag.getTag_id());
                    }
                    //绑定关系
                    if (problemTagMapper.insertProblemTagWithTagIdAndProblemId(problem.getProblem_id(), tag.getTag_id()) > 0) {
                        logger.info("题目[{}]与标签[{}]绑定成功!", problem.getProblem_id(), tag.getTag_id());
                        return problem.getProblem_id();
                    } else {
                        throw new ServiceException("题目与标签绑定失败");
                    }
                }
            } else {
                return problem.getProblem_id();
            }
        }
        throw new ServiceException("题目新建失败!");
    }

    /**
     * 查询题目公开级别是否满足asklLevel级别
     *
     * @param askLevels 是否是此权限
     * @param level     实际公开级别
     * @return 是否满足
     */
    private boolean switchProblemLevel(int[] askLevels, int level) {
        //屏蔽-公开-部分公开-绝对私有0/1/2/3
        for (int i = 0; i < askLevels.length; i++) {
            if (level == askLevels[i]) {
                return true;
            }
        }
        return false;
    }

}
