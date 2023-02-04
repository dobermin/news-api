package ru.mail.dobermin.news.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.dobermin.news.service.rbc.IRbcService;

@RestController
@RequestMapping("/api/v1/rbc")
@AllArgsConstructor
public class RbcController extends MainController {

    private final IRbcService rbcService;

    @GetMapping({"/{link}", "/", ""})
    public ResponseEntity<?> getRbc(@PathVariable(required = false) String link) {
        try {
            return ResponseEntity.ok(rbcService.getNews(link));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
