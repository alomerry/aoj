package mo.service.impl;

import mo.dao.ProblemMapper;
import mo.dao.UserMapper;
import mo.entity.po.Problem;
import mo.entity.po.User;
import mo.entity.vo.ProblemLink;
import mo.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Problem findProblemByProblemId(Integer problem_id) {
        return problemMapper.findProblemByProblemId(problem_id);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsByDefunctAndPage(defunct, page - 1, per_page);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, page - 1, per_page);
    }

    @Override
    public List<ProblemLink> findSimpleProblemLinksByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        User user = userMapper.findUserByUserId(user_id);
        List<Problem> problems = problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, page, per_page);
        List<ProblemLink> problemLinks = new ArrayList<>(problems.size() + 3);
        for (Problem p : problems) {
            problemLinks.add(new ProblemLink(p, user));
        }
        return problemLinks;
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer page, Integer per_page) {
        return problemMapper.findProblemsByDefunctAndPage(defunct, page - 1, per_page);
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findProblemsWithOwnByDefunctAndPage(defunct, user_id, page - 1, per_page);
    }
}
