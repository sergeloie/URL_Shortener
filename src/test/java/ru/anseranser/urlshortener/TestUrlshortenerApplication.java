package ru.anseranser.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestUrlshortenerApplication {

	public static void main(String[] args) {
		SpringApplication.from(UrlShortenerApplication::main).with(TestUrlshortenerApplication.class).run(args);
	}

}
