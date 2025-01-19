package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<MemberRole, Long> {
    Optional<MemberRole> findByRole(Role role);
}
