package dev.tindersamurai.prokurator.backend.mvc.data.dao;

import dev.tindersamurai.prokurator.backend.mvc.data.entity.TextChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextChannelRepo extends JpaRepository<TextChannel, String> {
}
