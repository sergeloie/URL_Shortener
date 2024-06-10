package ru.anseranser.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final RandomStringGenerator randomStringGenerator;
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

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
        Link link = linkRepository.findByShortLink(shortlink)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortlink with id `%s` not found".formatted(shortlink)));
        return link.getSourceLink();
    }
}
