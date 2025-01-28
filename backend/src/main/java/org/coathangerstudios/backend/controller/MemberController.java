package org.coathangerstudios.backend.controller;

import jakarta.validation.Valid;
import org.coathangerstudios.backend.model.dto.MemberDto;
import org.coathangerstudios.backend.model.payload.JwtResponse;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public UUID signup(@Valid @RequestBody NewMemberRequest newMemberRequest) {
         return memberService.signUp(newMemberRequest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody MemberLoginRequest memberLoginRequest){
        return memberService.login(memberLoginRequest);
    }
}
