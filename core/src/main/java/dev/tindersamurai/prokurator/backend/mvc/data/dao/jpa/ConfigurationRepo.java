package dev.tindersamurai.prokurator.backend.mvc.data.dao.jpa;

import dev.tindersamurai.prokurator.backend.mvc.data.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepo extends JpaRepository<Configuration, String> {
}
