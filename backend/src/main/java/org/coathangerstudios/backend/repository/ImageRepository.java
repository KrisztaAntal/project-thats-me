package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Image;
import org.coathangerstudios.backend.model.entity.ImageType;
import org.coathangerstudios.backend.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImagePublicId(UUID imagePublicId);

    Optional<Image> findByMemberAndImageTypesContaining(Member member, ImageType imageType);

}
