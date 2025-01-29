package org.coathangerstudios.backend.security.service;

import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.exception.MemberNotFoundWithGivenCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {

        Member member = getMemberFromDatabase(usernameOrEmail);

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (MemberRole role : member.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole().name()));
        }

        return new User(member.getUsername(), member.getPassword(), roles);
    }

    private Member getMemberFromDatabase(String usernameOrEmail) {
        if (usernameOrEmail.contains("@")) {
            return memberRepository.findUserByEmail(usernameOrEmail).orElseThrow(MemberNotFoundWithGivenCredentialsException::new);
        }
        return memberRepository.findUserByUsername(usernameOrEmail).orElseThrow(MemberNotFoundWithGivenCredentialsException::new);
    }
}
