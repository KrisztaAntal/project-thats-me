package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
@Getter
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Role role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<Member> members;

}
