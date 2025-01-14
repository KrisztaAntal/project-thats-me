package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private UUID memberId;
    private LocalDate dateOfRegistry;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<MemberRole> roles;

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
