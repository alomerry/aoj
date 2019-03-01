package mo.service;

import mo.entity.po.Problem;
import mo.entity.po.Solution;

import java.net.UnknownHostException;
import java.util.List;

public interface SolutionService {
    /**
     * 查找指定用户的所有判题记录，按指定时间顺序排列
     *
     * @param userId   用户id
     * @param sortType 时间排序，若为真则按时间降序排列，否则按时间降序排列
     * @return 返回指定时间排序的所有判题记录
     */
    List<Solution> findSolutionsByUserIdOrderByJudgeTime(Integer userId, boolean sortType);

    /**
     * 根据页码和每页数量查找指定用户的部分判题记录，按指定时间顺序排列
     *
     * @param userId   用户id
     * @param sortType 时间排序，若为真则按时间降序排列，否则按时间降序排列
     * @param page     页数
     * @param pageNum  每页数量
     * @return 返回满足要求的判题记录
     */
    List<Solution> findSolutionsByUserIdOrderByJudgeTimeByPage(Integer userId, boolean sortType, int page, int pageNum);

    /**
     *
     * @param problem
     * @param user_id
     * @param source
     * @return
     * @throws UnknownHostException
     */
    long insertIntoSolution(Problem problem, Integer user_id, String source) throws UnknownHostException;
}
