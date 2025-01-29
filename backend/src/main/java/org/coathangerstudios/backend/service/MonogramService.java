package org.coathangerstudios.backend.service;

import jakarta.persistence.EntityNotFoundException;
import org.coathangerstudios.backend.model.entity.Monogram;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.springframework.stereotype.Service;

@Service
public class MonogramService {
    private final MonogramRepository monogramRepository;

    public MonogramService(MonogramRepository monogramRepository) {
        this.monogramRepository = monogramRepository;
    }

    public Monogram saveMonogram(String characters) {
        String colorCode = "#000000";
        Monogram monogramOfMember = new Monogram(characters, colorCode);
        monogramRepository.save(monogramOfMember);
        return monogramRepository.findByCharactersAndColorCode(characters, colorCode).orElseThrow(() -> new IllegalStateException("Saved Monogram cannot be found. This should not happen."));
    }
}
