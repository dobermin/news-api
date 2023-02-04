package ru.mail.dobermin.news.model;

import lombok.Data;

@Data
public class News {

    private Long date;
    private String title;
    private String link;
    private String imgLink;
    private int view;
}
