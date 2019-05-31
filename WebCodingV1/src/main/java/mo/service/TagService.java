package mo.service;

import mo.entity.po.main.Tag;

import java.util.List;

public interface TagService {
    /**
     * 根据题目Id查找标签集合
     *
     * @param problem_id 题目Id
     * @return 标签集合
     */
    List<Tag> findTagsByProblemId(Integer problem_id);

    /**
     * 查询标签
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return
     */
    List<Tag> findTagsByPage(int page, int per_page);

}
