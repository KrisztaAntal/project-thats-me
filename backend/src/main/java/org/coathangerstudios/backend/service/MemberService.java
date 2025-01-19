package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.exception.EmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.exception.UsernameIsAlreadyInUseException;
import org.coathangerstudios.backend.model.dto.DTOMapper;
import org.coathangerstudios.backend.model.dto.MemberDto;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Monogram;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.model.payload.JwtResponse;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.coathangerstudios.backend.repository.RoleRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final MonogramRepository monogramRepository;


    public MemberService(AuthenticationManager authenticationManager, MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleRepository roleRepository, MonogramRepository monogramRepository) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.monogramRepository = monogramRepository;
    }


    public MemberDto signUp(NewMemberRequest newMemberRequest) {
        try {
            if (!alreadyInDB(newMemberRequest.getUsername(), newMemberRequest.getEmail())) {
                saveNewMember(newMemberRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Member savedMember = findMemberByUsername(newMemberRequest.getUsername());

        return DTOMapper.toMemberDto(savedMember);
    }

    public JwtResponse login(MemberLoginRequest memberLoginRequest) {
        String usernameOrEmail = memberLoginRequest.getUsernameOrEmail();
        String password = memberLoginRequest.getPassword();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new JwtResponse(token, userDetails.getUsername(), roles);
    }

    private Member findMemberByUsername(String username) {
        return memberRepository.findUserByUsername(username).orElseThrow();
    }

    private boolean alreadyInDB(String username, String email) {
        if (memberRepository.findUserByUsername(username).isPresent()) {
            throw new UsernameIsAlreadyInUseException("Username is already in use.");
        }
        if (memberRepository.findUserByEmail(email).isPresent()) {
            throw new EmailAddressAlreadyInUseException("Email is already in use.");
        }
        return false;
    }

    private void saveNewMember(NewMemberRequest newMemberRequest) {
        Monogram newMemberMonogram = new Monogram(newMemberRequest.getFirstName().substring(0, 1).concat(newMemberRequest.getLastName().substring(0, 1)), "#000000");
        monogramRepository.save(newMemberMonogram);

        Member newMember = new Member(newMemberRequest.getUsername(),
                newMemberRequest.getFirstName(),
                newMemberRequest.getLastName(),
                passwordEncoder.encode(newMemberRequest.getPassword()),
                newMemberRequest.getEmail(),
                newMemberRequest.getBirthdate(),
                null,
                newMemberMonogram,
                null,
                null
        );

        MemberRole memberRole = roleRepository.findByRole(Role.ROLE_USER).orElseThrow();
        newMember.addRole(memberRole);

        memberRepository.save(newMember);
    }
}
