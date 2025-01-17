package org.coathangerstudios.backend.model.dto;

import org.coathangerstudios.backend.model.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

public class DTOMapper {

    public static MemberDto toMemberDto(Member member){
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

    public static MonogramDto toMonogramDto(Monogram monogram) {
        return new MonogramDto(
                monogram.getMonogramPublicId(),
                monogram.getCharacters(),
                monogram.getColorCode()
        );
    }

    public static Set<ExpertiseDto> toExpertiseDtoSet(Set<Expertise> expertises){
        return expertises.stream().map(DTOMapper::toExpertiseDto).collect(Collectors.toSet());
    }

    public static ExpertiseDto toExpertiseDto(Expertise expertise) {
        return new ExpertiseDto(
                expertise.getExpertisePublicId(),
                expertise.getName()
        );
    }

    public static Set<PastJobDto> toPastJobDtoSet(Set<PastJob> pastJobs) {
        return pastJobs.stream().map(DTOMapper::pastJobDto).collect(Collectors.toSet());
    }

    public static PastJobDto pastJobDto(PastJob pastJob) {
        return new PastJobDto(
                pastJob.getJobPublicId(),
                pastJob.getCompanyName(),
                pastJob.getJobTitle(),
                pastJob.getStartDate(),
                pastJob.getEndDate(),
                pastJob.getDescription()
        );
    }

    public static Set<ProjectOfMemberDto> toProjectOfMemberDtoSet(Set<ProjectOfMember> projectsOfMember) {
        return projectsOfMember.stream().map(DTOMapper::toProjectOfMemberDto).collect(Collectors.toSet());
    }

    public static ProjectOfMemberDto toProjectOfMemberDto(ProjectOfMember projectOfMember) {
        return new ProjectOfMemberDto(
                projectOfMember.getProjectPublicId(),
                projectOfMember.getProjectTitle(),
                projectOfMember.getProjectDescription()
        );
    }

    public static Set<MemberRoleDto> toMemberRoleDtoSet(Set<MemberRole> memberRoles) {
        return memberRoles.stream().map(DTOMapper::toMemberRoleDto).collect(Collectors.toSet());
    }

    public static MemberRoleDto toMemberRoleDto(MemberRole memberRole) {
        return new MemberRoleDto(memberRole.getRole().name());
    }


}
