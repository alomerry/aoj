package mo.service;


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
    List<ContestApplyLink> getContestApplyByContestId(Integer contestId, int page, int per_page);
}
