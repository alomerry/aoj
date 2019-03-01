package mo.service;

import mo.dao.TestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {

    private Logger logger = LoggerFactory.getLogger(TestService.class);

    @Resource
    private TestMapper testMapper;

    /*
    确认申请
     */
    public void confrim(Integer user_id, Integer contest_id) {
        testMapper.updateOneApplication(user_id, contest_id);
        logger.info("更新申请者状态成功");
    }

    /*
    增加人数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNum(Integer contest_id) {
        testMapper.addOneContestNowNumber(contest_id);
        int a = 1 / 0;
    }

    public void conbimeA(Integer user_id, Integer contest_id) {
        confrim(user_id, contest_id);
        addNum(contest_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void conbimeB(Integer user_id, Integer contest_id) {
        testMapper.updateOneApplication(user_id, contest_id);
        testMapper.addOneContestNowNumber(contest_id);
        int a = 1 / 0;
    }

    public int searchNull(Integer problem_id) {
        return testMapper.findCompetitionProblemFromContestProblemByProblemId(problem_id);
    }
}
