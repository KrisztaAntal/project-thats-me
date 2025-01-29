package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.model.dto.*;
import org.coathangerstudios.backend.model.entity.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DTOMapperService {

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getMemberPublicId(),
                member.getDateOfRegistry(),
                toMemberRoleDtoSet(member.getRoles()),
                member.getUsername(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getBirthDate(),
                member.getBiography(),
                toMonogramDto(member.getMonogram()),
                member.getAvatar(),
                member.getBannerImage(),
                toExpertiseDtoSet(member.getExpertises()),
                toPastJobDtoSet(member.getPastJobs()),
                toProjectOfMemberDtoSet(member.getProjectsOfMember())
        );
    }

    public MonogramDto toMonogramDto(Monogram monogram) {
        return new MonogramDto(
                monogram.getMonogramPublicId(),
                monogram.getCharacters(),
                monogram.getColorCode()
        );
    }

    public Set<ExpertiseDto> toExpertiseDtoSet(Set<Expertise> expertises) {
        return expertises.stream().map(this::toExpertiseDto).collect(Collectors.toSet());
    }

    public ExpertiseDto toExpertiseDto(Expertise expertise) {
        return new ExpertiseDto(
                expertise.getExpertisePublicId(),
                expertise.getName()
        );
    }

    public Set<PastJobDto> toPastJobDtoSet(Set<PastJob> pastJobs) {
        return pastJobs.stream().map(this::pastJobDto).collect(Collectors.toSet());
    }

    public PastJobDto pastJobDto(PastJob pastJob) {
        return new PastJobDto(
                pastJob.getJobPublicId(),
                pastJob.getCompanyName(),
                pastJob.getJobTitle(),
                pastJob.getStartDate(),
                pastJob.getEndDate(),
                pastJob.getDescription()
        );
    }

    public Set<ProjectOfMemberDto> toProjectOfMemberDtoSet(Set<ProjectOfMember> projectsOfMember) {
        return projectsOfMember.stream().map(this::toProjectOfMemberDto).collect(Collectors.toSet());
    }

    public ProjectOfMemberDto toProjectOfMemberDto(ProjectOfMember projectOfMember) {
        return new ProjectOfMemberDto(
                projectOfMember.getProjectPublicId(),
                projectOfMember.getProjectTitle(),
                projectOfMember.getProjectDescription()
        );
    }

    public Set<MemberRoleDto> toMemberRoleDtoSet(Set<MemberRole> memberRoles) {
        return memberRoles.stream().map(this::toMemberRoleDto).collect(Collectors.toSet());
    }

    public MemberRoleDto toMemberRoleDto(MemberRole memberRole) {
        return new MemberRoleDto(memberRole.getRole().name());
    }


}
