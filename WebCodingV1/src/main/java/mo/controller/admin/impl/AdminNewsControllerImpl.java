package mo.controller.admin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import mo.controller.AbstractController;
import mo.controller.admin.AdminNewsController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.News;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AdminNewsControllerImpl extends AbstractController implements AdminNewsController {

    private static final Logger logger = LoggerFactory.getLogger(AdminNewsControllerImpl.class);

    @Resource
    private NewsService newsService;

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @ResponseBody
    @RequestMapping(value = "/admin/news", method = RequestMethod.POST)
    public Result createNews(String news) {

        News topic = JSON.parseObject(news, new TypeReference<News>() {
        });

        if (newsService.createNews(topic, getJWTUserId())) {
            return new Result().setCode(ResultCode.OK);
        } else {
            return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("添加失败!");
        }
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/admin/news", method = RequestMethod.GET)
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    public Result getNews(@RequestParam(value = "page", defaultValue = "1") String page,
                          @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        JSONObject news = new JSONObject();
        news.put("newsLink", newsService.findNews(getJWTUserId(), Integer.valueOf(page), Integer.valueOf(per_page)));
        return new Result().setCode(ResultCode.OK).setData(news);
    }
}
