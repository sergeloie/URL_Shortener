package ru.anseranser.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.dto.link.LinkScoreDto;
import ru.anseranser.urlshortener.mapper.LinkMapper;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.repository.LinkRepository;
import ru.anseranser.urlshortener.utils.RandomStringGenerator;

import java.util.List;

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
    private final RedisService redisService;

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

    public String redirect(String shortLink) {
        String sourceLink = stringRedisTemplate.opsForValue().get(shortLink);
        if (sourceLink != null) {
            redisService.incrementShortLinkRating(shortLink);
            return sourceLink;
        }
        Link link = linkRepository.findByShortLink(shortLink)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortlink with id `%s` not found".formatted(shortLink)));
        redisService.saveLinkToCash(link);
        redisService.incrementShortLinkRating(link.getShortLink());
        return link.getSourceLink();
    }

    public LinkScoreDto getLinkFromTop(String shortLink) {
        LinkScoreDto linkScoreDto = redisService.getLinkFromTop(shortLink);
        linkScoreDto.setShortLink("/l/" + linkScoreDto.getShortLink());
        return linkScoreDto;
    }

    public List<LinkScoreDto> getLinksFromTop(long page, long count) {
        List<LinkScoreDto> linkScoreDtos = redisService.getLinksFromTop(page, count);
        for (LinkScoreDto linkScoreDto : linkScoreDtos) {
            linkScoreDto.setShortLink("/l/" + linkScoreDto.getShortLink());
        }
        return linkScoreDtos;
    }

}
