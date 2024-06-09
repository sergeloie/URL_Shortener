package ru.anseranser.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.anseranser.urlshortener.utils.RandomStringGenerator;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {

        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Bean
    public RandomStringGenerator getRandomStringGenerator() {
        return new RandomStringGenerator();
    }

}
