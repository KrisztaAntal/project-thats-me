package org.coathangerstudios.backend.model.dto;

import java.util.UUID;

public record MonogramDto(UUID monogramId,
                          String characters,
                          String colorCode
) {
}
