package mo.service.impl;

import mo.entity.po.News;
import mo.service.NewsService;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Override
    public boolean createNews(News news) {

        return false;
    }
}
