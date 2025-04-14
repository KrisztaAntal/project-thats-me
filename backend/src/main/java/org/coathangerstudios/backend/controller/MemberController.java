package org.coathangerstudios.backend.controller;

import jakarta.validation.Valid;
import org.coathangerstudios.backend.exception.UnauthorizedChangeException;
import org.coathangerstudios.backend.model.dto.MemberDto;
import org.coathangerstudios.backend.model.entity.Image;
import org.coathangerstudios.backend.model.entity.ImageType;
import org.coathangerstudios.backend.model.payload.JwtResponse;
import org.coathangerstudios.backend.model.payload.MemberLoginRequest;
import org.coathangerstudios.backend.model.payload.NewMemberRequest;
import org.coathangerstudios.backend.model.payload.SuccessfulUploadResponse;
import org.coathangerstudios.backend.security.jwt.JwtUtils;
import org.coathangerstudios.backend.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    public MemberController(MemberService memberService, JwtUtils jwtUtils) {
        this.memberService = memberService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/member/me")
    public MemberDto getMember(@RequestHeader String authorization) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.getMemberInfo(username);
    }

    @GetMapping("/member/{username}")
    public MemberDto getMemberByUsername(@PathVariable String username) {
        return memberService.getMemberInfo(username);
    }

    @GetMapping("/member/{username}/avatar")
    public ResponseEntity<byte[]> getAvatarOfMember(@PathVariable String username) {
        Image image = memberService.getImageOfMember(username, ImageType.AVATAR);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getImageData());
    }
    @GetMapping("/member/{username}/banner")
    public ResponseEntity<byte[]> getBannerOfMember(@PathVariable String username) {
        Image image = memberService.getImageOfMember(username, ImageType.BANNER);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getImageData());
    }

    @PostMapping("/signup")
    public UUID signup(@Valid @RequestBody NewMemberRequest newMemberRequest) {
        return memberService.signUp(newMemberRequest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return memberService.login(memberLoginRequest);
    }

    @PostMapping("/member/update/avatar")
    public SuccessfulUploadResponse updateAvatar(@RequestHeader String authorization, @RequestParam("file") MultipartFile file) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.updateAvatar(username, file);
    }

    private String checkIfInitiatedByLoggedInMember(String authorization) {
        String token = authorization.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(loggedInUsername)) {
            throw new UnauthorizedChangeException();
        }
        return username;
    }
}
