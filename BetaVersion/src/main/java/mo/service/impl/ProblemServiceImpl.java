package mo.service.impl;

import mo.dao.ContestApplyMapper;
import mo.dao.ContestProblemMapper;
import mo.dao.ProblemMapper;
import mo.entity.po.Problem;
import mo.service.abstracts.AbstractProblemService;
import mo.utils.DIYMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static mo.utils.string.StringDBValues.PROBLEM_create_time;

@Service
public class ProblemServiceImpl extends AbstractProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private ContestApplyMapper contestApplyMapper;


    @Override
    public List<Problem> findPublicProblemsByPage(int page, int pageNum) {
        return problemMapper.findProblemsByDefunctAndPage(getPublicLevel(public_one), (page - 1) * pageNum, pageNum);
    }

    @Override
    public Problem findProblemByProblemId(Integer problem_id) {
        return problemMapper.findProblemByProblemId(problem_id);
    }

    @Override
    public boolean checkProblemIsPublic(Integer problem_id, Integer user_id) {
        //查找contest_problem 竞赛题目表，找到问题对应的比赛号
        //根据用户Id和比赛号查找 contest_apply 比赛申请表 判断用户是否在比赛中
        Integer contest_id = contestProblemMapper.findCompetitionProblemByProblemIdFromContestProblem(problem_id);
        if (contest_id == null) {
            //说明该题目不在竞赛中
            //判断是否公开
            return problemMapper.checkProblemIsPublicFromProblems(problem_id) > 0;
        } else {
            //该题目在竞赛中,判断权限是否足够
            return contestApplyMapper.checkContestApplyHasBeenAcceptIsExsitByUserIdAndContestIdFromContestApply(user_id, contest_id) > 0;
        }
    }

    @Override
    public List<Problem> findCreatorProblemsByUserId(Integer user_id) {
        return problemMapper.findProblemsOrderByTypeByCreatorUserId(user_id, PROBLEM_create_time);
    }

    @Override
    public List<Problem> findProblemsByDefunct(int defunct, int page, int pageNum) {
        return problemMapper.findProblemsOrderByTypeByDefunctAndPage(getPublicLevel(defunct), PROBLEM_create_time, (page - 1) * pageNum, pageNum);
    }

    @Override
    public List<Problem> findProblemsByDefunct(int defunct) {
        return problemMapper.findProblemsOrderByTypeByDefunct(getPublicLevel(defunct), PROBLEM_create_time);
    }

    @Override
    public List<Problem> findProblemsByPage(int page, int pageNum) {
        return problemMapper.findProblemsByPageFromProblems((page - 1) * pageNum, pageNum);
    }

    @Override
    public List<Problem> findProblemsByPageOrderByTypeAndSort(int page, int pageNum, String type, String sort) {
        return problemMapper.findProblemsByPageOrderByTypeAndSortFromProblems(type, sort, (page - 1) * pageNum, pageNum);
    }

    @Override
    public DIYMessage delProblemByProblemId(Integer problem_id) {
        return problemMapper.delProblemByProblemId(problem_id) > 0 ? new DIYMessage(delSuccess, "删除成功！") : new DIYMessage(delFail, "删除失败！");
    }

}
