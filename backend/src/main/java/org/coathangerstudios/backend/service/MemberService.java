package org.coathangerstudios.backend.service;

import jakarta.transaction.Transactional;
import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.exception.MemberNotFoundWithGivenCredentialsException;
import org.coathangerstudios.backend.exception.UsernameOrEmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Monogram;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.model.payload.JwtResponse;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.coathangerstudios.backend.repository.MemberRoleRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MemberRoleRepository memberRoleRepository;
    private final MonogramRepository monogramRepository;
    private final DTOMapperService dtoMapperService;


    public MemberService(AuthenticationManager authenticationManager, MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, MemberRoleRepository memberRoleRepository, MonogramRepository monogramRepository, DTOMapperService dtoMapperService) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.memberRoleRepository = memberRoleRepository;
        this.monogramRepository = monogramRepository;
        this.dtoMapperService = dtoMapperService;
    }

    @Transactional
    public UUID signUp(NewMemberRequest newMemberRequest) {
        validateMemberUniqueness(newMemberRequest.getUsername(), newMemberRequest.getEmail());
        saveNewMember(newMemberRequest);
        Member savedMember = memberRepository.findUserByUsername(newMemberRequest.getUsername()).orElseThrow(() -> new IllegalStateException("Member was not found after saving. This should not happen."));
        return savedMember.getMemberPublicId();
    }

    public JwtResponse login(MemberLoginRequest memberLoginRequest) {
        String usernameOrEmail = memberLoginRequest.getUsernameOrEmail();
        String password = memberLoginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateJwtToken(authentication);

            User userDetails = (User) authentication.getPrincipal();
            Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            return new JwtResponse(token, userDetails.getUsername(), roles);
        } catch (Exception e) {
            throw new MemberNotFoundWithGivenCredentialsException();
        }
    }

    private void validateMemberUniqueness(String username, String email) {
        if (memberRepository.existsByUsernameOrEmail(username, email)) {
            throw new UsernameOrEmailAddressAlreadyInUseException();
        }
    }

    private void saveNewMember(NewMemberRequest newMemberRequest) {
        try {
            Monogram monogramOfNewMember = new Monogram(newMemberRequest.getFirstName().substring(0, 1).concat(newMemberRequest.getLastName().substring(0, 1)), "#000000");
            monogramRepository.save(monogramOfNewMember);
            Member newMember = new Member(newMemberRequest.getUsername(), newMemberRequest.getFirstName(), newMemberRequest.getLastName(), passwordEncoder.encode(newMemberRequest.getPassword()), newMemberRequest.getEmail(), newMemberRequest.getBirthdate(), null, monogramOfNewMember, null, null);
            addUserRoleToMember(newMember);
            memberRepository.save(newMember);
        } catch (DataAccessException ex) {
            throw new DatabaseSaveException("Failed to save new user: " + ex.getMessage());
        }
    }

    private void addUserRoleToMember(Member member) {
        MemberRole memberRole = memberRoleRepository.findByRole(Role.ROLE_USER).orElseThrow(() -> new IllegalStateException("Role cannot be found"));
        member.addRole(memberRole);
    }
}
