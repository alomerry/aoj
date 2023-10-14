package mo.controller.index.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.index.NewsController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class NewsControllerImpl extends AbstractController implements NewsController {

    @Resource
    private NewsService newsService;

    @Override
    @ResponseBody
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public Result getNews(@RequestParam(value = "page", defaultValue = "1") String page,
                          @RequestParam(value = "per_page", defaultValue = "10") String per_page) {

        JSONObject news = new JSONObject();
        news.put("newsLink", newsService.findNewsByPageAndDefunct(Integer.valueOf(page), Integer.valueOf(per_page), "1"));
        news.put("total", newsService.getPublicNewsTotalNum());
        return new Result().setCode(ResultCode.OK).setData(news);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/contest/{contestId}/news", method = RequestMethod.GET)
    public Result getNews(@PathVariable Integer contestId,
                          @RequestParam(value = "page", defaultValue = "1") String page,
                          @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject news = new JSONObject();
        news.put("newsLink", newsService.findNewsByContestIdAndDefunct(contestId, Integer.valueOf(page), Integer.valueOf(per_page), "1"));
        news.put("total", newsService.getContestNewsTotalNum(contestId));
        return new Result().setCode(ResultCode.OK).setData(news);
    }
}
