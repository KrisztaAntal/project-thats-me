package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsernameOrEmail(String username, String email);

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<Member> findMemberByMemberPublicId(UUID memberPublicId);
}
