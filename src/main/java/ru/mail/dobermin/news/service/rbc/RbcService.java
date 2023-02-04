package ru.mail.dobermin.news.service.rbc;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mail.dobermin.news.model.News;
import ru.mail.dobermin.news.service.AService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RbcService extends AService implements IRbcService {

    private final String domain = "rbc.ru";

    @Override
    public List<News> getNews(String link) {

        link = link != null ? "/" + link : "";
        String u = String.format("%s%s%s", https, domain, link);

        Document page = getPage(u);

        Elements n = page.select(".main__feed, .item_image-mob");

        List<News> newsList = new ArrayList<>();

        n.forEach(
                ns -> {
                    News news = new News();

                    try {
                        // Изображение
                        String img = ns.select("img").attr("src");
                        if (img.endsWith("svg")) img = "";
                        news.setImgLink(
                                img
                        );
                    } catch (Exception ignored) {
                    }
                    try {
                        // Ссылка
                        String url = ns.select("a").attr("href");
                        url = (url.charAt(0) != 'h') ? https + domain + url : url;
                        news.setLink(url);
                    } catch (Exception ignored) {
                    }

                    try {
                        // Заголовок
                        news.setTitle(
                                ns.select(".main__feed__title, .item__title").first().text()
                        );
                        newsList.add(news);
                    } catch (Exception ignored) {
                    }
                }
        );

        return getNewsSorted(newsList);
    }
}
