package ru.anseranser.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import ru.anseranser.urlshortener.dto.link.LinkScoreDto;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final LinkRepository linkRepository;
    @Value("${short.link.cache.ttl.ms}")
    private int shortLinkCacheTtlMs;

    private final StringRedisTemplate stringRedisTemplate;

    public void saveLinkToCash(Link link) {
        stringRedisTemplate
                .opsForValue()
                .set(link.getShortLink(), link.getSourceLink(), shortLinkCacheTtlMs, TimeUnit.MILLISECONDS);
    }

    public void incrementShortLinkRating(String shortLink) {
        stringRedisTemplate
                .opsForZSet()
                .incrementScore("topshort", shortLink, 1);
    }

    public LinkScoreDto getLinkFromTop(Link link) {
        long rank = stringRedisTemplate
                .opsForZSet()
                .rank("topshort", link.getShortLink());
        double score = stringRedisTemplate
                .opsForZSet()
                .score("topshort", link.getShortLink());
        return new LinkScoreDto(link.getShortLink(), link.getSourceLink(), rank, score);
    }

    public List<LinkScoreDto> getLinksFromTop(long page, long count) {
        Set<ZSetOperations.TypedTuple<String>> result = stringRedisTemplate
                .opsForZSet()
                .rangeWithScores("topshot", count * (page - 1), count * page - 1);
        return new ArrayList<>();
    }
}
