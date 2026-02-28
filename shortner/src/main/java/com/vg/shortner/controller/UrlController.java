package com.vg.shortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vg.shortner.entity.UrlRequest;
import com.vg.shortner.service.UrlService;

@RestController()
public class UrlController {
    public UrlService service;

    public UrlController(UrlService service){
        this.service=service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createShort(@RequestBody UrlRequest url){
        String shorturl=service.createShortUrl(url.getUrl());
        return ResponseEntity.ok(shorturl);
    }

    @GetMapping("/{url}")
    public ResponseEntity<String> getLongUrl(@PathVariable String url){
        String shorturl=service.getOriginal(url);
        return ResponseEntity.ok(shorturl);
    }
}
