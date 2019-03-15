package mo.service;

import mo.entity.po.Tag;

import java.util.List;

public interface TagService {
    /**
     * 根据题目Id查找标签集合
     *
     * @param problem_id 题目Id
     * @return 标签集合
     */
    List<Tag> findTagsByProblemId(Integer problem_id);
}
