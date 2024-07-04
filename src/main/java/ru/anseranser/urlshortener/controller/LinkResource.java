package ru.anseranser.urlshortener.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.dto.link.LinkScoreDto;
import ru.anseranser.urlshortener.mapper.LinkMapper;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.repository.LinkRepository;
import ru.anseranser.urlshortener.service.LinkService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LinkResource {

    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;
    private final LinkService linkService;

    private final ObjectMapper objectMapper;

    @PostMapping("/generate")
    public LinkDto create(@RequestBody @Valid LinkCreateDto linkCreateDto) {
        return linkService.create(linkCreateDto);
    }

    @GetMapping("/l/{shortlink}")
    public RedirectView redirect(@PathVariable String shortlink) {
        return new RedirectView(linkService.redirect(shortlink));
    }

    @GetMapping("/stats/{shortlink}")
    public LinkScoreDto getScore(@PathVariable String shortlink) {
        return linkService.getLinkFromTop(shortlink);
    }

    @GetMapping("/stats")
    public List<LinkScoreDto> getScores(@RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "100") long count) {
        return linkService.getLinksFromTop(page, count);
    }


}
