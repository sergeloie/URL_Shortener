package ru.anseranser.urlshortener.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.model.Link;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface LinkMapper {
    Link toEntity(LinkCreateDto linkCreateDto);

    LinkDto toDto(Link link);

    @BeforeMapping
    default void addPrefix(Link link) {
        String prefixedShortUrl = "/l/" + link.getShortLink();
        link.setShortLink(prefixedShortUrl);
    }
}