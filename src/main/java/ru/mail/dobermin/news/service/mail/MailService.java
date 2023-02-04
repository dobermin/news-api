package ru.mail.dobermin.news.service.mail;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mail.dobermin.news.model.News;
import ru.mail.dobermin.news.service.AService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService extends AService implements IMailService {

    private final String domain = "mail.ru/";
    private String url = String.format("%s%s", https, domain);

    @Override
    public List<News> getNews(String link) {

        if (link != null)
            url = switch (link) {
                case "inworld" -> String.format("%s%s.%s/%s", https, "news", domain, link);
                case "lady", "auto", "kino", "hi-tech" -> String.format("%s%s.%s", https, link,
                        domain);
                case "sport" -> String.format("%s%s%s", https, link, domain);
                case "football", "hockey" -> String.format("%s%s%s/%s", https, "sport", domain, link);
                default -> String.format("%s%s", https, domain);
            };

        Document page = getPage(url);

        Elements n = page.select(".news__list__item_simple, .p-feed__item, .list__item, .newsitem, .daynews__item, .daynews__item daynews__item_big");

        List<News> newsList = new ArrayList<>();

        n.forEach(
                ns -> {
                    News news = new News();

                    try {
                        // Изображение
                        String img = ns.select(".photo__pic").first().attr("style");
                        img = img.substring(img.indexOf("(") + 1, img.indexOf(")"));
                        news.setImgLink(img);
                    } catch (Exception ignored) {
                        String img = ns.select("img, .photo__pic").attr("data-lazy-block-src");
                        news.setImgLink(img);
                        if (img.isEmpty())
                            news.setImgLink(ns.select("picture img").attr("src"));
                    }
                    try {
                        // Ссылка
                        String a = ns.select("a").attr("href");
                        a = (a.charAt(0) == 'h') ? a : url + a;
                        news.setLink(a);
                    } catch (Exception ignored) {
                    }

                    try {
                        // Заголовок
                        ns.select(".badge__text").remove();
                        String title = "";
                        try {
                            title = ns.select(".text_fixed, .photo__title").first().text();
                        } catch (Exception e) {

                        }
                        if (title.isEmpty()) title = ns.select("a").first().text();
                        news.setTitle(
                                title
                        );
                        newsList.add(news);
                    } catch (Exception ignored) {
                    }
                }
        );

        return getNewsSorted(newsList);
    }
}
