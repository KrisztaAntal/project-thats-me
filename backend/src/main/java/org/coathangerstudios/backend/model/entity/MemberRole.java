package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

public class MemberRole {
    @Id
    @GeneratedValue
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<Member> members;
}
