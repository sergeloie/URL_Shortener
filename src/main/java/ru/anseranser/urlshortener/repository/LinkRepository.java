package ru.anseranser.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.anseranser.urlshortener.model.Link;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByShortLink(@NonNull String shortLink);
    Optional<Link> findByShortLink(String shortLink);

    @Query("SELECT l.sourceLink FROM Link l WHERE l.shortLink = :shortLink")
    String findSourceLinkByShortLink(@Param("shortLink") String shortLink);

}