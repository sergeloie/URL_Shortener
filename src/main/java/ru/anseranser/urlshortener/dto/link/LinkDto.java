package ru.anseranser.urlshortener.dto.link;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.anseranser.urlshortener.model.Link}
 */
@Value
public class LinkDto implements Serializable {
    @JsonProperty("link")
    String shortLink;
}