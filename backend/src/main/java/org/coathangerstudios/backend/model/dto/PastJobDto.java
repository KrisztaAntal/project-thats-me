package org.coathangerstudios.backend.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PastJobDto(UUID jobId,
                         String companyName,
                         String jobTitle,
                         LocalDate startDate,
                         LocalDate endDate,
                         String description
) {
}
