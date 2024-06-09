package ru.anseranser.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.anseranser.urlshortener.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByShortLink(@NonNull String shortLink);
}