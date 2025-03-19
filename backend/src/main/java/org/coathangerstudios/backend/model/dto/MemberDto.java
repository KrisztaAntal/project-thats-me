package org.coathangerstudios.backend.model.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record MemberDto(UUID memberId,
                        LocalDate dateOfRegistry,
                        Set<MemberRoleDto> roles,
                        String username,
                        String firstName,
                        String lastName,
                        String email,
                        LocalDate birthDate,
                        String biography,
                        DefaultAvatarDto monogram,
                        Set<ExpertiseDto> expertises,
                        Set<PastJobDto> pastJobs,
                        Set<ProjectOfMemberDto> projectsOfMember
) {
}
