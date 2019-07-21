package dev.tindersamurai.prokurator.backend.mvc.data.dao.jpa;

import dev.tindersamurai.prokurator.backend.mvc.data.entity.post.MediaPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaPostRepo extends JpaRepository<MediaPost, String> {
}
