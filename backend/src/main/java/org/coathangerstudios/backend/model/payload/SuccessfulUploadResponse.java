package org.coathangerstudios.backend.model.payload;

import java.util.UUID;

public record SuccessfulUploadResponse(String text, UUID publicIdOfSavedFile) {
}
