package org.coathangerstudios.backend.serviceTest;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.exception.MemberNotFoundWithGivenCredentialsException;
import org.coathangerstudios.backend.exception.UsernameOrEmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.model.entity.DefaultAvatar;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.model.payload.JwtResponse;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.coathangerstudios.backend.service.DTOMapperService;
import org.coathangerstudios.backend.service.DefaultAvatarService;
import org.coathangerstudios.backend.service.MemberRoleService;
import org.coathangerstudios.backend.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    private AuthenticationManager authenticationManagerMock;
    private MemberRepository memberRepositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private JwtUtils jwtUtilsMock;
    private MemberRoleService memberRoleServiceMock;
    private DefaultAvatarService defaultAvatarServiceMock;
    private DTOMapperService dtoMapperServiceMock;

    @BeforeEach
    public void setUp() {
        authenticationManagerMock = mock(AuthenticationManager.class);
        memberRepositoryMock = mock(MemberRepository.class);
        passwordEncoderMock = mock(PasswordEncoder.class);
        jwtUtilsMock = mock(JwtUtils.class);
        memberRoleServiceMock = mock(MemberRoleService.class);
        defaultAvatarServiceMock = mock(DefaultAvatarService.class);
        dtoMapperServiceMock = mock(DTOMapperService.class);
    }

    @Test
    public void testSignUp_MemberWithUsernameOrEmailAlreadyExists() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "password", "email@example.com", LocalDate.parse("2001-11-11"));
        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(true);

        assertThrows(UsernameOrEmailAddressAlreadyInUseException.class, () -> memberService.signUp(newMemberRequest));
        verifyNoInteractions(defaultAvatarServiceMock);
        verifyNoInteractions(memberRoleServiceMock);
        verify(memberRepositoryMock, never()).save(any(Member.class));
    }

    @Test
    public void testSignUp_SavingFails() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "password", "email@example.com", LocalDate.parse("2001-11-11"));
        MemberRole userRole = new MemberRole(1L, Role.ROLE_USER);
        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(false);
        when(passwordEncoderMock.encode(newMemberRequest.getPassword())).thenReturn("$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC");
        when(memberRoleServiceMock.getUserRole()).thenReturn(userRole);
        when(defaultAvatarServiceMock.saveDefaultAvatar(newMemberRequest.getUsername().substring(0, 1))).thenThrow(new DatabaseSaveException("Failed to save Monogram"));

        DatabaseSaveException exception = assertThrows(DatabaseSaveException.class, () -> memberService.signUp(newMemberRequest));

        assertEquals("Failed to save Monogram", exception.getMessage());
    }


    @Test
    public void testSignUp_Success() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "456456456", "email@example.com", LocalDate.parse("2001-11-11"));
        MemberRole userRole = new MemberRole(1L, Role.ROLE_USER);
        DefaultAvatar defaultAvatar = new DefaultAvatar("U", "#303d3f");
        Member savedMember = new Member("username", "", "", "$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC", "email@example.com", LocalDate.parse("2001-11-11"), "", defaultAvatar, null, null);

        when(defaultAvatarServiceMock.saveDefaultAvatar(newMemberRequest.getUsername().substring(0, 1))).thenReturn(defaultAvatar);
        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(false);
        when(passwordEncoderMock.encode(newMemberRequest.getPassword())).thenReturn("$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC");
        when(memberRoleServiceMock.getUserRole()).thenReturn(userRole);
        when(memberRepositoryMock.findUserByUsername(newMemberRequest.getUsername())).thenReturn(Optional.of(savedMember));

        memberService.signUp(newMemberRequest);

        verify(memberRepositoryMock).save(any(Member.class));
        verify(memberRepositoryMock).findUserByUsername(newMemberRequest.getUsername());
    }

    @Test
    public void testLogin_WrongPassword() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("username", "123");

        when(authenticationManagerMock.authenticate(new UsernamePasswordAuthenticationToken("username", "123"))).thenThrow(BadCredentialsException.class);

        assertThrows(MemberNotFoundWithGivenCredentialsException.class, () -> memberService.login(memberLoginRequest));
    }

    @Test
    public void testLogin_WrongUsernameOrEmail() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("usernameOrEmail", "123123123");

        when(authenticationManagerMock.authenticate(new UsernamePasswordAuthenticationToken("usernameOrEmail", "123123123"))).thenThrow(MemberNotFoundWithGivenCredentialsException.class);

        assertThrows(MemberNotFoundWithGivenCredentialsException.class, () -> memberService.login(memberLoginRequest));
    }

    @Test
    public void testLogin_Success() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleServiceMock, defaultAvatarServiceMock, dtoMapperServiceMock);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("testUser", "123123123");
        Authentication authentication = mock(Authentication.class);
        User userDetails = new User("testUser", "123123123", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(authenticationManagerMock.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtilsMock.generateJwtToken(authentication)).thenReturn("mocked-JWT-token");

        JwtResponse jwtResponse = memberService.login(memberLoginRequest);

        assertEquals("mocked-JWT-token", jwtResponse.getToken());
        assertEquals("testUser", jwtResponse.getUsername());
        assertTrue(jwtResponse.getRoles().contains("ROLE_USER"));

        verify(authenticationManagerMock).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtilsMock).generateJwtToken(authentication);
    }
}
