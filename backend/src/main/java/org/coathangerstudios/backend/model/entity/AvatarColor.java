package org.coathangerstudios.backend.model.entity;

import lombok.Getter;

@Getter
public enum AvatarColor {
    A("#c6dbdf"),
    B("#3c6e71"),
    C("#3c6e71");


    private final String hashCode;

    AvatarColor(String hashCode){
        this.hashCode = hashCode;
    }
}
