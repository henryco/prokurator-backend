package dev.tindersamurai.prokurator.backend.mvc.data.dao;

import dev.tindersamurai.prokurator.backend.mvc.data.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.stream.Stream;

public interface GuildRepo extends JpaRepository<Guild, String> {

	Stream<Guild> findAllByIdIn(Collection<String> id);
}
