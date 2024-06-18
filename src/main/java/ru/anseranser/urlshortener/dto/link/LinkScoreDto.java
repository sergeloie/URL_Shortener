package ru.anseranser.urlshortener.dto.link;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * DTO for {@link ru.anseranser.urlshortener.model.Link}
 */
@Data
@AllArgsConstructor
public class LinkScoreDto implements Serializable {
    @JsonProperty("link")
    String shortLink;
    @JsonProperty("original")
    String sourceLink;
    long rank;
    @JsonProperty("count")
    double score;
}