package mo.service.impl;

import mo.dao.ProblemMapper;
import mo.entity.po.Problem;
import mo.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Override
    public Problem findProblemByProblemId(Integer problem_id) {
        return problemMapper.findProblemByProblemId(problem_id);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, int page, int pageNum) {
        return problemMapper.findSimpleProblemsByDefunctAndPage(defunct, (page - 1) * pageNum, pageNum);
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, int page, int per_page) {
        return problemMapper.findProblemsByDefunctAndPage(defunct, page, per_page);
    }
}
