package dev.tindersamurai.prokurator.backend.mvc.service.storage.lfs.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LFSRepository extends JpaRepository<LFSEntity, String> {

}
