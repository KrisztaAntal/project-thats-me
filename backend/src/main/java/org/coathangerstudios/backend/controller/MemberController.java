package org.coathangerstudios.backend.controller;

import jakarta.validation.Valid;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class MemberController {

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody NewMemberRequest newMemberRequest) {
    }
}
