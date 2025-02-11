package org.coathangerstudios.backend.model.dto;

import java.util.UUID;

public record DefaultAvatarDto(UUID monogramId,
                               String characters,
                               String colorCode
) {
}
