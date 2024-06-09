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
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.mapper.LinkMapper;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.repository.LinkRepository;
import ru.anseranser.urlshortener.service.LinkService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/admin-ui/links")
@RequiredArgsConstructor
public class LinkResource {

    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;
    private final LinkService linkService;

    private final ObjectMapper objectMapper;

    @GetMapping
    public Page<Link> getList(@ParameterObject Pageable pageable) {
        return linkRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Link getOne(@PathVariable Long id) {
        Optional<Link> linkOptional = linkRepository.findById(id);
        return linkOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Link> getMany(@RequestParam List<Long> ids) {
        return linkRepository.findAllById(ids);
    }

    @PostMapping
    public LinkDto create(@RequestBody @Valid LinkCreateDto linkCreateDto) {
        return linkService.create(linkCreateDto);
    }

    @PatchMapping("/{id}")
    public Link patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Link link = linkRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(link).readValue(patchNode);

        return linkRepository.save(link);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Link> links = linkRepository.findAllById(ids);

        for (Link link : links) {
            objectMapper.readerForUpdating(link).readValue(patchNode);
        }

        List<Link> resultLinks = linkRepository.saveAll(links);
        List<Long> ids1 = resultLinks.stream()
                .map(Link::getId)
                .toList();
        return ids1;
    }

    @DeleteMapping("/{id}")
    public Link delete(@PathVariable Long id) {
        Link link = linkRepository.findById(id).orElse(null);
        if (link != null) {
            linkRepository.delete(link);
        }
        return link;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        linkRepository.deleteAllById(ids);
    }
}
