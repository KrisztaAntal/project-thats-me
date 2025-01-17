package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UuidGenerator
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
    private String bannerImage;

    @ManyToMany
    private Set<Expertise> expertises;
    @ManyToMany
    private Set<PastJob> pastJobs;
    @ManyToMany
    private Set<ProjectOfMember> projectsOfMember;

    public Member(String username, String firstName, String lastName, String password, String email, LocalDate birthDate, String biography, String avatar, String bannerImage) {
        this.dateOfRegistry = LocalDate.now();
        this.roles = new HashSet<>();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.biography = biography;
        this.monogram = new Monogram(firstName.substring(0,1).concat(lastName.substring(0,1)), "#000000");
        this.avatar = avatar;
        this.bannerImage = bannerImage;
        this.expertises = new HashSet<>();
        this.pastJobs = new HashSet<>();
        this.projectsOfMember = new HashSet<>();
    }

    public Member() {
    }
}
