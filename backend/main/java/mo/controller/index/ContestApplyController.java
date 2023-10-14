package mo.controller.index;

import mo.core.Result;

public interface ContestApplyController {
    /**
     * 新建申请
     *
     * @param contestId 申请竞赛Id
     * @return
     */
    Result contestApply(Integer contestId);
}
