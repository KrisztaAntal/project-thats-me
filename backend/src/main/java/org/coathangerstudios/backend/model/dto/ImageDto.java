package org.coathangerstudios.backend.model.dto;

import java.util.UUID;

public record ImageDto(UUID imagePublicId,
                       String name,
                       String contentType,
                       byte[] imageData) {
}
