package ru.mail.dobermin.news.service.onliner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mail.dobermin.news.model.News;
import ru.mail.dobermin.news.service.AService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlinerService extends AService implements IOnlinerService {

    private String peopleUrl;

    @Override
    public List<News> getNews(String link) {
        link = link != null ? link + "." : "";
        peopleUrl = String.format("%s%s%s", https, link, "onliner.by");
        Document page = getPage(peopleUrl);

        Elements news = page.select(".b-tiles, .news-tidings").select(".b-tile, .news-tidings__item");

        List<News> newsList = new ArrayList<>();

        news.forEach(
                n -> {
                    News ns = new News();
                    try {
                        //Ссылка на изображение
                        String img =
                                n.select(".news-tiles__image, .news-tidings__image").first().attr(
                                        "style");
                        img = img.substring(img.indexOf("(") + 1, img.indexOf(")"));
                        ns.setImgLink(img);
                    } catch (Exception ignored) {
                        String img =
                                n.select("picture img").attr("src");
                        ns.setImgLink(img);
                    }
                    try {
                        //Ссылка на статью
                        String url = n.select("a").first().attr("href");
                        ns.setLink(url.charAt(0) == '/' ? peopleUrl + url : url);
                    } catch (Exception ignored) {
                    }
                    try {
                        //Количество просмотров
                        String v = n.select(".news-tiles__button_views, .button-style_obverse, " +
                                ".news-tidings__button_views_popular").text().replaceAll(" ", "");
                        ns.setView(Integer.parseInt(v));
                    } catch (Exception ignored) {
                    }

                    try {
                        // Заголовок
                        ns.setTitle(
                                n.select(".b-tile-header .txt, .news-tidings__link span:first-child, .news-tiles__subtitle").first().text().trim());
                        newsList.add(ns);
                    } catch (Exception ignored) {
                    }
                }
        );

        return getNewsSorted(newsList);
    }
}
