package org.coathangerstudios.backend.model.dto;

import java.util.UUID;

public record ProjectOfMemberDto(UUID projectId,
                                 String projectTitle,
                                 String projectDescription
) {
}
