package ru.mail.dobermin.news.service.lenta;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mail.dobermin.news.model.News;
import ru.mail.dobermin.news.service.AService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LentaService extends AService implements ILentaService {

    private final String domain = "lenta.ru/";
    private String url = String.format("%s%s", https, domain);

    @Override
    public List<News> getNews(String link) {

        if (link != null)
            url = switch (link) {
                case "news" -> String.format("%s%s/%s", https, domain, "parts/news/");
                case "football", "hockey" -> String.format("%s%s%s/%s/%s/", https, domain, "rubrics",
                        "sport", link);
                case "science", "sport" -> String.format("%s%s%s/%s/", https, domain, "rubrics", link);
                default -> String.format("%s%s", https, domain);
            };

        Document page = getPage(url);

        Elements n = page.select(
                ".rubric-page__item, .card-mini"
        );

        List<News> newsList = new ArrayList<>();

        n.forEach(
                ns -> {
                    News news = new News();

                    try {
                        // Изображение
                        news.setImgLink(ns.select("img").first().attr("src"));
                    } catch (Exception ignored) {
                    }
                    try {
                        // Ссылка
                        String url = ns.select("a").first().attr("href");
                        url = (url.charAt(0) != 'h') ? https + domain + url : url;
                        news.setLink(url);
                    } catch (Exception ignored) {
                    }

                    try {
                        // Заголовок
                        ns.select("time").remove();
                        news.setTitle(
                                ns.select(".titles, a").first().text()
                        );
                        if (!news.getTitle().isEmpty() && !news.getLink().contains("rubrics"))
                            newsList.add(news);
                    } catch (Exception ignored) {
                    }
                }
        );

        return getNewsSorted(newsList);
    }
}
