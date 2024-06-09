package ru.anseranser.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.mapper.LinkMapper;
import ru.anseranser.urlshortener.model.Link;

@SpringBootTest
class UrlShortenerApplicationTests {

	@Autowired
	private LinkMapper linkMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void mapperTest() {
		Link link = new Link();
		link.setShortLink("12345678");
		LinkDto linkDTO = linkMapper.toDto(link);
		System.out.println(linkDTO.getShortLink());
	}

}
