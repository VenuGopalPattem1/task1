package com.vg.shortner.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vg.shortner.entity.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url,Long> {
    
    public Optional<Url> findByShortCode(String shortCode);
    public Optional<Url> findByOriginalUrl(String shortCode);
}