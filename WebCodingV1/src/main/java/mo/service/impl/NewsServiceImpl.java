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
    public boolean updateNews(News news) {
        news.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        return newsMapper.updateNewsByNewsId(news) > 0;
    }

    @Override
    public List<NewsUserLink> findNews(Integer user_id, int page, int per_page) {
        List<News> newsList = newsMapper.findNewsByPageAndCreatorId(user_id, (page - 1) * per_page, per_page);
        return makeLinkUser(newsList);
    }

    @Override
    public List<NewsUserLink> findNews(int page, int per_page) {
        List<News> newsList = newsMapper.findNewsByPage((page - 1) * per_page, per_page);
        return makeLinkUser(newsList);
    }

    @Override
    public boolean deleteNews(Integer news_id) {
        return newsMapper.deleteNewsByNewsId(news_id) > 0;
    }

    @Override
    public News findNews(Integer news_id) {
        return newsMapper.findNewsByNewsId(news_id);
    }

    @Override
    public List<NewsUserLink> findNewsByPageAndDefunct(int page, int per_page, String defunct) {
        return makeLinkUser(newsMapper.findNewsByPageAndDefunct(defunct, (page - 1) * per_page, per_page));
    }

    private List<NewsUserLink> makeLinkUser(List<News> newsList) {
        List<NewsUserLink> newsUserLinks = new ArrayList<>(newsList.size() + 3);
        for (News news : newsList) {
            newsUserLinks.add(new NewsUserLink(userMapper.findUserByUserId(news.getUser_id()), news));
        }
        return newsUserLinks;
    }
}
