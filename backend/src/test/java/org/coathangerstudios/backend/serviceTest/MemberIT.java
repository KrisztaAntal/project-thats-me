package org.coathangerstudios.backend.serviceTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Monogram;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.repository.MemberRepository;
import org.coathangerstudios.backend.repository.MonogramRepository;
import org.coathangerstudios.backend.repository.MemberRoleRepository;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
/*        monogramRepository.deleteAll();
        memberRepository.deleteAll();
        memberRoleRepository.deleteAll();*/
        //MemberRole userRole = new MemberRole(Role.ROLE_USER);
        //memberRoleRepository.save(userRole);
        //Monogram monogram = new Monogram("FL", "#000000");
        //Member savedMember = new Member("username", "firstName", "lastName", "$2a$10$AzhoXWmxaTrVM02m2NqM9..NHHQubT9JVFQLMQHXJasxLqFbgyKVC", "email@example.com", LocalDate.parse("2001-11-11"), "", monogram, "", "");
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
}
