package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
public class ProjectOfMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    @UuidGenerator
    private UUID projectPublicId;
    private String projectTitle;
    private String projectDescription;

    public ProjectOfMember(String projectTitle, String projectDescription) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
    }

    public ProjectOfMember() {

    }
}
