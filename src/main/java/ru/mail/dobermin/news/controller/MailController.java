package ru.mail.dobermin.news.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.dobermin.news.service.mail.IMailService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController extends MainController {

    private final IMailService mailService;

    @GetMapping({"/{link}", "/", ""})
    public ResponseEntity<?> getMail(@PathVariable(required = false) String link) {
        try {
            return ResponseEntity.ok(mailService.getNews(link));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
