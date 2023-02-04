package ru.mail.dobermin.news.service;

import org.jsoup.nodes.Document;
import ru.mail.dobermin.jsoup.Jsoup;
import ru.mail.dobermin.news.model.News;

import java.io.File;
import java.util.List;

abstract public class AService {

	protected String https = "https://";

	protected Document getPage(String url) {
		Jsoup.getFromUri(url);
		return Jsoup.getDocument();
	}

	protected Document getFromFile(String file) {
		File f = new File(file);
		Jsoup.getFromFile(file);
		return Jsoup.getDocument();
	}

	protected List<News> getNewsSorted(List<News> news) {
		try {
			news.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
		} catch (Exception ignored) {
		}
		return news;
	}
}
