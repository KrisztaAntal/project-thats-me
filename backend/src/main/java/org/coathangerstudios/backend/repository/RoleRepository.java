package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<MemberRole, Long> {
}
