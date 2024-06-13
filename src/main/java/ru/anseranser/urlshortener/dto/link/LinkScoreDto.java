package ru.anseranser.urlshortener.dto.link;

import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * DTO for {@link ru.anseranser.urlshortener.model.Link}
 */
@Value
public class LinkScoreDto implements Serializable {
    String shortLink;
    String sourceLink;
    long rank;
    double score;
}