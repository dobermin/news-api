package ru.mail.dobermin.news.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.dobermin.news.service.lenta.ILentaService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/lenta")
public class LentaController extends MainController {

    private final ILentaService lentaService;

    @GetMapping({"/{link}", "/", ""})
    public ResponseEntity<?> getLenta(@PathVariable(required = false) String link) {
        try {
            return ResponseEntity.ok(lentaService.getNews(link));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
