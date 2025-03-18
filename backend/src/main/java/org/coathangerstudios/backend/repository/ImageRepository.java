package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImagePublicId(UUID imagePublicId);
}
