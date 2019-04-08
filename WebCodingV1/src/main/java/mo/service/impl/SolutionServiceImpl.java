package mo.service.impl;

import mo.dao.SolutionMapper;
import mo.entity.po.Solution;
import mo.entity.po.SourceCode;
import mo.exception.ServiceException;
import mo.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SolutionServiceImpl implements SolutionService {

    private static final Logger logger = LoggerFactory.getLogger(SolutionServiceImpl.class);

    @Resource
    private SolutionMapper solutionMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean insertIntoNewSolution(Solution solution, SourceCode sourceCode) {
        int res = solutionMapper.insertOneItemIntoSolution(solution.getProblem_id(),
                solution.getUser_id(),
                solution.getCreate_at().toString(),
                solution.getResult(),
                solution.getLanguage(),
                solution.getIp(),
                solution.getCode_lenght());
        if (res > 0) {
            int lines = solutionMapper.insertCodeIntoSource(solutionMapper.findLastInsertId(), sourceCode.getSource());
            if (lines > 0) {
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
}
