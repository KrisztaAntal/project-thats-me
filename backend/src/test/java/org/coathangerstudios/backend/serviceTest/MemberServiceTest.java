package org.coathangerstudios.backend.serviceTest;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.exception.UsernameOrEmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Monogram;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.coathangerstudios.backend.repository.MemberRoleRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.coathangerstudios.backend.service.DTOMapperService;
import org.coathangerstudios.backend.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    private AuthenticationManager authenticationManagerMock;
    private MemberRepository memberRepositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private JwtUtils jwtUtilsMock;
    private MemberRoleRepository memberRoleRepositoryMock;
    private MonogramRepository monogramRepositoryMock;
    private DTOMapperService dtoMapperServiceMock;

    @BeforeEach
    public void setUp() {
        authenticationManagerMock = mock(AuthenticationManager.class);
        memberRepositoryMock = mock(MemberRepository.class);
        passwordEncoderMock = mock(PasswordEncoder.class);
        jwtUtilsMock = mock(JwtUtils.class);
        memberRoleRepositoryMock = mock(MemberRoleRepository.class);
        monogramRepositoryMock = mock(MonogramRepository.class);
        dtoMapperServiceMock = mock(DTOMapperService.class);
    }

    @Test
    public void testSignUp_MemberWithUsernameOrEmailAlreadyExists() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleRepositoryMock, monogramRepositoryMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "firstName", "Lastname", "password", "email@example.com", LocalDate.parse("2001-11-11"));
        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(true);

        assertThrows(UsernameOrEmailAddressAlreadyInUseException.class, () -> memberService.signUp(newMemberRequest));
        verifyNoInteractions(monogramRepositoryMock);
        verify(memberRepositoryMock, never()).save(any(Member.class));
    }

    @Test
    public void testSignUp_SavingFails() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleRepositoryMock, monogramRepositoryMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "firstName", "Lastname", "password", "email@example.com", LocalDate.parse("2001-11-11"));
        MemberRole userRole = new MemberRole(1L, Role.ROLE_USER);
        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(false);
        when(passwordEncoderMock.encode(newMemberRequest.getPassword())).thenReturn("$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC");
        when(memberRoleRepositoryMock.findByRole(Role.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(monogramRepositoryMock.save(any(Monogram.class))).thenThrow(new DatabaseSaveException("Failed to save Monogram"));

        DatabaseSaveException exception = assertThrows(DatabaseSaveException.class, () -> memberService.signUp(newMemberRequest));

        assertEquals("Failed to save Monogram", exception.getMessage());
        verify(monogramRepositoryMock).save(any(Monogram.class));
    }


    @Test
    public void testSignUp_Success() {
        MemberService memberService = new MemberService(authenticationManagerMock, memberRepositoryMock, passwordEncoderMock, jwtUtilsMock, memberRoleRepositoryMock, monogramRepositoryMock, dtoMapperServiceMock);
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "firstName", "lastName", "456456456", "email@example.com", LocalDate.parse("2001-11-11"));
        MemberRole userRole = new MemberRole(1L, Role.ROLE_USER);
        Monogram monogram = new Monogram("FL", "#000000");
        Member savedMember = new Member("username", "firstName", "lastName", "$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC", "email@example.com", LocalDate.parse("2001-11-11"), "", monogram, "", "");

        when(memberRepositoryMock.existsByUsernameOrEmail(newMemberRequest.getUsername(), newMemberRequest.getEmail())).thenReturn(false);
        when(passwordEncoderMock.encode(newMemberRequest.getPassword())).thenReturn("$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC");
        when(memberRoleRepositoryMock.findByRole(Role.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(memberRepositoryMock.findUserByUsername(newMemberRequest.getUsername())).thenReturn(Optional.of(savedMember));

        memberService.signUp(newMemberRequest);

        verify(monogramRepositoryMock).save(any(Monogram.class));
        verify(memberRepositoryMock).save(any(Member.class));
        verify(memberRepositoryMock).findUserByUsername(newMemberRequest.getUsername());
    }
}
