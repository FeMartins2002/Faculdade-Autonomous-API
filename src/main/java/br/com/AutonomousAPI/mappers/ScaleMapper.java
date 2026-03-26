package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.request.scale.CreateScaleDTO;
import br.com.AutonomousAPI.dtos.response.scale.ScaleResponseDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.entities.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScaleMapper {
    @Mapping(source = "freelancer.name", target = "freelancerName")
    @Mapping(source = "manager.name", target = "managerName")
    @Mapping(source = "store.name", target = "storeName")
    ScaleResponseDTO toResponse(Scale scale);

    List<ScaleResponseDTO> toResponseList(List<Scale> scales);

    @Mapping(target = "scaleStatus", source = "dto.scaleStatus")
    @Mapping(target = "scaleValue", source = "dto.scaleValue")
    @Mapping(target = "scaleDateTime", source = "dto.scaleDateTime")
    @Mapping(target = "scaleObservation", ignore = true)
    @Mapping(target = "freelancer", source = "freelancer")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "store", source = "store")
    Scale toEntity(CreateScaleDTO dto, Manager manager, Freelancer freelancer, Store store);
}
