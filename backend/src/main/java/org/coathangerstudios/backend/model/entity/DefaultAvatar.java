package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@Getter
@Entity
public class DefaultAvatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defaultAvatarId;
    @UuidGenerator
    private UUID defaultAvatarPublicId;
    private String firstCharacter;
    private String colorCode;
    @OneToOne(mappedBy = "defaultAvatar", cascade = CascadeType.ALL)
    private Member member;

    public DefaultAvatar(String firstCharacter, String colorCode) {
        this.firstCharacter = firstCharacter;
        this.colorCode = colorCode;
    }

    public DefaultAvatar() {
    }
}
