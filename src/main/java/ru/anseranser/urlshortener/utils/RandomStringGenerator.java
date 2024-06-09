package ru.anseranser.urlshortener.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class RandomStringGenerator {

    public String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
