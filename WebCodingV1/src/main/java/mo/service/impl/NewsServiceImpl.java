package mo.service.impl;

import mo.dao.NewsMapper;
import mo.entity.po.News;
import mo.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public boolean createNews(News news, Integer user_id) {
        return newsMapper.insertNews(news, user_id) > 0;
    }
}
