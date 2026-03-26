package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.request.freelancer.CreateFreelancerDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.FreelancerResponseDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FreelancerMapper {
    @Mapping(target = "cpf", source = "dto.cpf")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "phone", source = "dto.phone")
    @Mapping(target = "password", source = "defaultPassword")
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "manager", source = "manager")
    Freelancer toEntity(CreateFreelancerDTO dto, Manager manager, String defaultPassword);

    @Mapping(target = "managerName", source = "freelancer.manager.name")
    FreelancerResponseDTO toResponse(Freelancer freelancer);

    List<FreelancerResponseDTO> toResponseList(List<Freelancer> freelancers);
}
