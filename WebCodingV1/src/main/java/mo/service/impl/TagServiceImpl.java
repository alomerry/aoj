package mo.service.impl;

import mo.dao.main.TagMapper;
import mo.entity.po.main.Tag;
import mo.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> findTagsByProblemId(Integer problem_id) {
        return tagMapper.findTagsByProblemId(problem_id);
    }

    @Override
    public List<Tag> findTagsByPage(int page, int per_page) {
        return tagMapper.findTagsByPage((page - 1) * per_page, per_page);
    }
}
