package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private UUID publicId;
    private LocalDate dateOfRegistry;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    private LocalDate birthDate;
    private String biography;
    @OneToOne
    private Monogram monogram;
    private String avatar;
    private String bannerPic;

    @ManyToMany
    private Set<Expertise> expertises;
    @ManyToMany
    private Set<PastJob> pastJobs;
    @ManyToMany
    private Set<ProjectOfMember> projectsOfMember;

}
