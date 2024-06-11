package ru.anseranser.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.mapper.LinkMapper;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.repository.LinkRepository;
import ru.anseranser.urlshortener.utils.RandomStringGenerator;

@RequiredArgsConstructor
@Service
public class LinkService {

    @Value("${short.link.size}")
    private int shortLinkSize;

    @Value("${short.link.cache.ttl.ms}")
    private int shortLinkCacheTtlMs;

    private final RandomStringGenerator randomStringGenerator;
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public LinkDto create(LinkCreateDto linkCreateDto) {
        String shortLink;
        do {
            shortLink = randomStringGenerator.generate(shortLinkSize);
        } while (linkRepository.existsByShortLink(shortLink));
        Link link = linkMapper.toEntity(linkCreateDto);
        link.setShortLink(shortLink);
        linkRepository.save(link);
        return linkMapper.toDto(link);
    }

    public String redirect(String shortlink) {
        String sourceLink = stringRedisTemplate.opsForValue().get(shortlink);
        if (sourceLink != null) {
            stringRedisTemplate.opsForZSet().incrementScore("topshort", shortlink, 1);
            return sourceLink;
        }
        Link link = linkRepository.findByShortLink(shortlink)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortlink with id `%s` not found".formatted(shortlink)));
        saveShortLinkToCash(link);
        return link.getSourceLink();
    }

    public void saveShortLinkToCash(Link link) {
        stringRedisTemplate.opsForValue().set(link.getShortLink(), link.getSourceLink(), shortLinkCacheTtlMs);
        stringRedisTemplate.opsForZSet().incrementScore("topshort", link.getShortLink(), 1);
    }
}
