package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Monogram {
    @Id
    @GeneratedValue
    private Long id;
    private String characters;
    private String colorCode;

}
