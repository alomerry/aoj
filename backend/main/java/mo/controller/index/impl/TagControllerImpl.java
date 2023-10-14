package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.index.TagController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TagControllerImpl implements TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagControllerImpl.class);

    @Resource
    private TagService tagService;

    @Override
    @ResponseBody
    @RequestMapping(value = "/tags/problem/{problem_id}", method = RequestMethod.GET)
    public Result tags(@PathVariable Integer problem_id) {
        JSONObject tags = new JSONObject();
        tags.put("tags", tagService.findTagsByProblemId(problem_id));
        return new Result().setCode(ResultCode.OK).setData(tags);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public Result tags(@RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        logger.info("page[{}],per_page[{}]", page, per_page);
        JSONObject tags = new JSONObject();
        tags.put("tags", tagService.findTagsByPage(Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setCode(ResultCode.OK).setData(tags);
    }
}
