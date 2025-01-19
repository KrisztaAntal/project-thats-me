package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Monogram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonogramRepository extends JpaRepository<Monogram, Long> {
}
