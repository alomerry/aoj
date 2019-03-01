package mo.service;

import mo.entity.vo.ContestProblemLink;
import mo.utils.DIYMessage;

import java.util.List;

public interface ContestProblemService {

    int insertSuccess = 1, insertFailed = 0;
    int delSuccess = 1, delFailed = 0;

    /**
     * 查询比赛问题集
     *
     * @param contest_id 比赛号
     * @return 问题集
     */
    List<ContestProblemLink> findCompetitionProblems(Integer contest_id);

    /**
     * 将指定题目添加至竞赛
     *
     * @param contest_id 竞赛号
     * @param problem_id 题目Id
     * @return 自定义消息
     */
    DIYMessage addProblemToContestByContestIdAndProblemId(Integer contest_id, Integer problem_id);

    /**
     * 将指定题目从指定竞赛中删除
     *
     * @param contest_id 竞赛号
     * @param problem_id 题目Id
     * @return 自定义结果消息
     */
    DIYMessage delProblemFromContestByContestIdAndProblemId(Integer contest_id, Integer problem_id);

    /**
     * 修改指定比赛的指定题目的顺序
     *
     * @param contest_id 竞赛号
     * @param problem_id 题目Id
     * @return 自定义结果消息
     */
    DIYMessage changeProblemNumFromContestByContestIdAndProblemId(Integer contest_id, Integer problem_id,int num);
}
