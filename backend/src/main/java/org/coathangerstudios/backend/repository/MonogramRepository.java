package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Monogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonogramRepository extends JpaRepository<Monogram, Long> {
    Optional<Monogram> findByCharactersAndColorCode(String characters, String colorCode);
}
