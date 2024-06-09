package ru.anseranser.urlshortener.service;

import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import java.util.List;


@Service
public class Shortener {

    public String makeShort(String url) {
        Sqids sqids = Sqids.builder()
                .build();
            return sqids.encode(prepareToHash(url)).substring(0, 8);
    }

    public List<Long> prepareToHash(String url) {
        return url.chars()
                .mapToLong(c -> (long) c)
                .boxed()
                .toList();
    }
}
