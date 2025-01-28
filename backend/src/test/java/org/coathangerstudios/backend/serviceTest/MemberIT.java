package org.coathangerstudios.backend.serviceTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coathangerstudios.backend.exception.MemberNotFoundWithGivenCredentialsException;
import org.coathangerstudios.backend.exception.UsernameOrEmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.repository.MemberRoleRepository;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class MemberIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private MonogramRepository monogramRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testSignUp_Success() throws Exception {
        NewMemberRequest newMemberRequest = new NewMemberRequest("username", "firstName", "lastName", "456456456", "email@example.com", LocalDate.parse("2001-11-11"));

        mockMvc.perform(post("/api/signup")
                        .content(jacksonObjectMapper.writeValueAsString(newMemberRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSignUp_FailedWithValidators() throws Exception {
        NewMemberRequest newMemberRequest = new NewMemberRequest("username@", "firstName", "lastName", "4564", "emailexample.com", LocalDate.parse("2021-11-11"));

        mockMvc.perform(post("/api/signup")
                        .content(jacksonObjectMapper.writeValueAsString(newMemberRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+)."))
                .andExpect(jsonPath("$.email").value("Email address must be a valid email format"))
                .andExpect(jsonPath("$.password").value("Password must be 8-20 characters long"))
                .andExpect(jsonPath("$.birthdate").value("User must be at least 14 years old"))
                .andDo(print());
    }

    @Test
    public void testSignUp_FailedBecauseUsernameIsAlreadyInDB() throws Exception {
        NewMemberRequest newMemberRequest = new NewMemberRequest("ankriszt", "Kriszta", "Antal", "456456456456", "Kriszta12@gmail.com", LocalDate.parse("2004-12-10"));

        mockMvc.perform(post("/api/signup")
                        .content(jacksonObjectMapper.writeValueAsString(newMemberRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(UsernameOrEmailAddressAlreadyInUseException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Username or email address is already in use, please choose another one", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testSignUp_FailedBecauseEmailIsAlreadyInDB() throws Exception {
        NewMemberRequest newMemberRequest = new NewMemberRequest("ankriszt12", "Kriszta", "Antal", "456456456456", "Kriszta@gmail.com", LocalDate.parse("2004-12-10"));

        mockMvc.perform(post("/api/signup")
                        .content(jacksonObjectMapper.writeValueAsString(newMemberRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(UsernameOrEmailAddressAlreadyInUseException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Username or email address is already in use, please choose another one", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testSignUp_FailedBecauseEmailAndUsernameIsAlreadyInDB() throws Exception {
        NewMemberRequest newMemberRequest = new NewMemberRequest("ankriszt", "Kriszta", "Antal", "456456456456", "Kriszta@gmail.com", LocalDate.parse("2004-12-10"));

        mockMvc.perform(post("/api/signup")
                        .content(jacksonObjectMapper.writeValueAsString(newMemberRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(UsernameOrEmailAddressAlreadyInUseException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Username or email address is already in use, please choose another one", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testLogin_FailedWithWrongUsernameOrEmail() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("kriszta", "456456456");

        mockMvc.perform(post("/api/login")
                        .content(jacksonObjectMapper.writeValueAsString(memberLoginRequest))
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MemberNotFoundWithGivenCredentialsException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("User cannot be found with given credentials.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testLogin_FailedWithWrongPassword() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("ankriszt", "456");

        mockMvc.perform(post("/api/login")
                        .content(jacksonObjectMapper.writeValueAsString(memberLoginRequest))
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MemberNotFoundWithGivenCredentialsException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("User cannot be found with given credentials.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testLogin_Success() throws Exception{
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("ankriszt", "456456456");

        mockMvc.perform(post("/api/login")
                        .content(jacksonObjectMapper.writeValueAsString(memberLoginRequest))
                        .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ankriszt"));

    }


}
