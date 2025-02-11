package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.DefaultAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultAvatarRepository extends JpaRepository<DefaultAvatar, Long> {
    Optional<DefaultAvatar> findDefaultAvatarByDefaultAvatarId(Long defaultAvatarId);
}
