package mo.service.impl;

import mo.dao.NewsMapper;
import mo.dao.UserMapper;
import mo.entity.po.News;
import mo.entity.vo.NewsUserLink;
import mo.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean createNews(News news, Integer user_id) {
        news.setCreate_at(new Timestamp(System.currentTimeMillis()));
        news.setUpdate_time(news.getCreate_at());
        return newsMapper.insertNews(news, user_id) > 0;
    }

    @Override
    public List<NewsUserLink> findNews(Integer user_id, int page, int per_page) {
        List<News> newsList = newsMapper.findNewsByPage(user_id, (page - 1) * per_page, per_page);
        List<NewsUserLink> newsUserLinks = new ArrayList<>(newsList.size() + 3);
        for (News news : newsList) {
            newsUserLinks.add(new NewsUserLink(userMapper.findUserByUserId(news.getUser_id()), news));
        }
        return newsUserLinks;
    }
}
