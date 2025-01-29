package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
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
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Member> members;

    public MemberRole(Long roleId, Role role) {
        this.roleId = roleId;
        this.role = role;
        this.members = new HashSet<>();
    }

    public MemberRole(Role role){
        this.role = role;
        this.members = new HashSet<>();
    }

    public MemberRole() {
    }
}
