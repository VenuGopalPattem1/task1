package com.vg.shortner.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vg.shortner.advice.UrlNotFound;
import com.vg.shortner.entity.Url;
import com.vg.shortner.repo.UrlRepository;
import com.vg.shortner.util.Base64Algo;

import jakarta.transaction.Transactional;

@Service
public class UrlService {

    public UrlRepository repo;
    public Base64Algo base64;

    public UrlService(UrlRepository repo, Base64Algo base64) {
        this.repo = repo;
        this.base64 = base64;
    }

    @Transactional
    public String createShortUrl(String longUrl) {
        if (repo.findByOriginalUrl(longUrl).isEmpty()) {
            Url url = new Url();
            url.setOriginalUrl(longUrl);
            url = repo.save(url);
            String shortUrl = base64.encode(url.getId());
            url.setShortCode(shortUrl);
            repo.save(url);
            return shortUrl;
        }else{
            throw new UrlNotFound("Long url is already present");
        }
       
    }

    public String getOriginal(String shortUrl) {
        Optional<Url> url = repo.findByShortCode(shortUrl);
        if (url.isEmpty()) {
            return "no Long url present";
        }
        return url.get().getOriginalUrl();
    }
}
