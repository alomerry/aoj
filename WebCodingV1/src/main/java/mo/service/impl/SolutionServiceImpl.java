package mo.service.impl;

import mo.dao.SolutionMapper;
import mo.entity.po.Solution;
import mo.exception.ServiceException;
import mo.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SolutionServiceImpl implements SolutionService {

    private static final Logger logger = LoggerFactory.getLogger(SolutionServiceImpl.class);

    @Resource
    private SolutionMapper solutionMapper;

    @Override
    @Transactional
    public Long insertIntoNewSolution(Solution solution) {
        int res = solutionMapper.insertOneItemIntoSolution(solution.getProblem_id(),
                solution.getUser_id(),
                solution.getCreate_at().toString(),
                solution.getResult(),
                solution.getLanguage(),
                solution.getIp(),
                solution.getCode_lenght());
        if (res > 0) {
            return solutionMapper.findLastInsertId();
        } else {
            logger.error("solution 插入失败");
            return null;
        }
    }
}
