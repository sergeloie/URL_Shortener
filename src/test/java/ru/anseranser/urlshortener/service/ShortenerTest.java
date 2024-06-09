package ru.anseranser.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShortenerTest {

    @Autowired
    private Shortener shortener;

    @Test
    void makeShort() {
        String url1 = "https://duckduckgo.com/?q=%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC+%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F+%D1%81%D1%81%D1%8B%D0%BB%D0%BE%D0%BA&t=ffab&ia=web";
        String url2 = "https://duckduckgo.com/?q=%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC+%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F+%D1%81%D1%81%D1%8B%D0%BB%D0%BE%D0%BA&t=ffab&ia=web";
        String url3 = "https://hub.docker.com/layers/bellsoft/liberica-openjdk-debian/21/images/sha256-c0890f9a97fd13d8ecaa82541c7f77fa269e9b95d2e3a76040509a7c74949fe6?context=explore";

        System.out.println(shortener.makeShort(url1));
        System.out.println(shortener.makeShort(url2));
        System.out.println(shortener.makeShort(url3));
    }
}