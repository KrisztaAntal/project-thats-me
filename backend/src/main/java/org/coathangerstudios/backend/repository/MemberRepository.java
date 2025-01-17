package org.coathangerstudios.backend.repository;

import org.coathangerstudios.backend.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findUserByUsernameOrEmail(String username, String email);

    Optional<Member> findUserByEmail(String email);

    Optional<Member> findUserByUsername(String username);
}
