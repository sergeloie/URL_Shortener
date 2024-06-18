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
    @Value("${short.link.top.name}")
    private String topShort;

    private final StringRedisTemplate stringRedisTemplate;

    public void saveLinkToCash(Link link) {
        stringRedisTemplate
                .opsForValue()
                .set(link.getShortLink(), link.getSourceLink(), shortLinkCacheTtlMs, TimeUnit.MILLISECONDS);
    }

    public void incrementShortLinkRating(String shortLink) {
        stringRedisTemplate
                .opsForZSet()
                .incrementScore(topShort, shortLink, 1);
    }

    public LinkScoreDto getLinkFromTop(String shortLink) {
        long rank = stringRedisTemplate
                .opsForZSet()
                .rank(topShort, shortLink);
        double score = stringRedisTemplate
                .opsForZSet()
                .score(topShort, shortLink);
        return new LinkScoreDto(shortLink, linkRepository.findSourceLinkByShortLink(shortLink), rank, score);
    }

    public List<LinkScoreDto> getLinksFromTop(long page, long count) {
        Set<ZSetOperations.TypedTuple<String>> tupleSet = stringRedisTemplate
                .opsForZSet()
                .rangeWithScores(topShort, count * (page - 1), count * page - 1);
        List<LinkScoreDto> result = new ArrayList<>();
        for (var tuple : tupleSet) {
            String shortLink = tuple.getValue();
            String sourceLink = linkRepository.findSourceLinkByShortLink(shortLink);
            double score = tuple.getScore();
            long rank = stringRedisTemplate.opsForZSet().rank(topShort, shortLink);
            LinkScoreDto linkScoreDto = new LinkScoreDto(shortLink, sourceLink,rank, score);
            result.add(linkScoreDto);
        }
        return result;
    }
}
