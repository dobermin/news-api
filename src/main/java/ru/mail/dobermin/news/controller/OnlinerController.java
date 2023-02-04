package ru.mail.dobermin.news.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.dobermin.news.service.onliner.IOnlinerService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/onliner")
public class OnlinerController extends MainController {

    private final IOnlinerService onlinerService;

    @GetMapping({"/{link}", "/", ""})
    public ResponseEntity<?> getOnliner(@PathVariable(required = false) String link) {
        try {
            return ResponseEntity.ok(onlinerService.getNews(link));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
