package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Expertise {

    @Id
    @GeneratedValue
    private Long id;
    private UUID expertiseId;
    private String name;

}
