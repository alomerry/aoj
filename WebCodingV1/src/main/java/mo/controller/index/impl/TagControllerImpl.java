package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.index.TagController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Tag;
import mo.service.TagService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TagControllerImpl implements TagController {

    @Resource
    private TagService tagService;

    @Override
    @RequestMapping(value = "/tags/problem/{problem_id}")
    public Result tags(@PathVariable Integer problem_id) {
        JSONObject tags = new JSONObject();
        tags.put("tags", tagService.findTagsByProblemId(problem_id));
        return new Result().setCode(ResultCode.OK).setData(tags);
    }

    @Override
    public Result tags(int page, int per_page) {
        return null;
    }
}
