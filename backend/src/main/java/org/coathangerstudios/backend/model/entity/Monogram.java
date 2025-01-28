package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Monogram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monogramId;
    @UuidGenerator
    private UUID monogramPublicId;
    private String characters;
    private String colorCode;
    @OneToOne(mappedBy = "monogram", cascade = CascadeType.ALL)
    private Member member;

    public Monogram(String characters, String colorCode) {
        this.characters = characters;
        this.colorCode = colorCode;
    }

    public Monogram() {
    }
}
