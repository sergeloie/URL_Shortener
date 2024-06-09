package ru.anseranser.urlshortener.dto.link;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.hibernate.validator.constraints.URL;
import ru.anseranser.urlshortener.model.Link;

import java.io.Serializable;

/**
 * DTO for {@link Link}
 */
@Value
public class LinkCreateDto implements Serializable {
    @URL
    @JsonProperty("original")
    String sourceLink;
}