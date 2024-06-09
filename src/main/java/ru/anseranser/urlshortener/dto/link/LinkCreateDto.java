package ru.anseranser.urlshortener.dto.link;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.URL;
import ru.anseranser.urlshortener.model.Link;

import java.io.Serializable;

/**
 * DTO for {@link Link}
 */
@Value
public class LinkCreateDto implements Serializable {
    @URL(message = "Not valid URL format")
    @JsonProperty("original")
    String sourceLink;

    @JsonCreator
    public LinkCreateDto(String sourceLink) {
        this.sourceLink = sourceLink;
    }
}