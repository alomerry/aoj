package mo.service.impl;

import mo.dao.SolutionMapper;
import mo.entity.po.Problem;
import mo.entity.po.Solution;
import mo.service.SolutionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static mo.utils.string.StringValue.OJ_WT1;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Resource
    private SolutionMapper solutionMapper;

    private static SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Solution> findSolutionsByUserIdOrderByJudgeTime(Integer userId, boolean sortType) {
        if (sortType) {
            return solutionMapper.findSolutionByUserIdOrderByJudgeTimeDesc(userId);
        }
        return solutionMapper.findSolutionByUserIdOrderByJudgeTimeAsc(userId);
    }

    @Override
    public List<Solution> findSolutionsByUserIdOrderByJudgeTimeByPage(Integer userId, boolean sortType, int page, int pageNum) {
        if (sortType) {
            return solutionMapper.findSolutionByUserIdOrderByJudgeTimeDescByPage(userId, (page - 1) * pageNum, pageNum);
        }
        return solutionMapper.findSolutionByUserIdOrderByJudgeTimeAscByPage(userId, (page - 1) * pageNum, pageNum);
    }

    @Override
    @Transactional
    public long insertIntoSolution(Problem problem, Integer user_id, String source) throws UnknownHostException {
        String res = sdt.format(new Date(System.currentTimeMillis()));
        System.out.println("插入时间：" + res);
        solutionMapper.insertOneItemIntoSolution(problem.getProblem_id(), user_id, problem.getTime_limit(), problem.getMemory_limit(),
                res, OJ_WT1, 1, InetAddress.getLocalHost().getHostAddress(), "1", 128, res);
        long solution_id = solutionMapper.findLastInsertId();
        return solutionMapper.insertCodeIntoSource(solution_id, source);
    }
}
