package ru.anseranser.urlshortener.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.anseranser.urlshortener.dto.link.LinkDto;
import ru.anseranser.urlshortener.model.Link;
import ru.anseranser.urlshortener.dto.link.LinkCreateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LinkMapper {
    Link toEntity(LinkCreateDto linkCreateDto);

    LinkDto toDto(Link link);

    @BeforeMapping
    default void addPrefix(Link link) {
        String prefixedShortUrl = "/l/" + link.getShortLink();
        link.setShortLink(prefixedShortUrl);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Link partialUpdate(LinkCreateDto linkCreateDto, @MappingTarget Link link);
}