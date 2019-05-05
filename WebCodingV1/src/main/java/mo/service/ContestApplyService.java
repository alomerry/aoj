package mo.service;


import mo.entity.po.ContestApply;
import mo.entity.vo.link.ContestApplyLink;

import java.util.List;

public interface ContestApplyService {
    /**
     * 查询竞赛申请
     *
     * @param contestId 竞赛Id
     * @param page      页码
     * @param per_page  每页数量
     * @return
     */
    List<ContestApplyLink> getContestAppliesByContestId(Integer contestId, int page, int per_page);

    /**
     * 查询指定竞赛的申请(待确定)
     *
     * @param contestId 竞赛Id
     * @return
     */
    int getUncheckedApplyNumberByContestId(int contestId);

    /**
     * 根据Id查询申请
     *
     * @param id 申请Id
     * @return
     */
    ContestApply findContestApplyById(Integer id);

    /**
     * 更新指定申请
     *
     * @param id     申请Id
     * @param status 状态
     * @return
     */
    boolean updateContestApply(Integer id, Integer status);
}
