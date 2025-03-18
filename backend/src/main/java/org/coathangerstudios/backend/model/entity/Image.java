package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @UuidGenerator
    private UUID imagePublicId;

    private String name;

    private String contentType;

    @Lob
    private byte[] imageData;

    public Image(String name, String contentType, byte[] imageData) {
        this.name = name;
        this.contentType = contentType;
        this.imageData = imageData;
    }

    public Image() {
    }
}
