package ru.mail.dobermin.news.service;

import org.springframework.stereotype.Service;
import ru.mail.dobermin.news.model.News;

import java.util.List;

@Service
public interface IService {
    List<News> getNews(String url);
}
