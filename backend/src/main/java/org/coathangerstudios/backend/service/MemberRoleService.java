package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.model.entity.MemberRole;
import org.coathangerstudios.backend.model.entity.Role;
import org.coathangerstudios.backend.repository.MemberRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    public MemberRoleService(MemberRoleRepository memberRoleRepository) {
        this.memberRoleRepository = memberRoleRepository;
    }

    public MemberRole getUserRole(){
        return memberRoleRepository.findByRole(Role.ROLE_USER).orElseThrow(() -> new IllegalStateException("Role cannot be found"));
    }
}
