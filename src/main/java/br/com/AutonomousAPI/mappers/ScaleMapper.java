package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.request.scale.CreateScaleDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.entities.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScaleMapper {
    @Mapping(target = "scaleStatus", source = "dto.scaleStatus")
    @Mapping(target = "scaleValue", source = "dto.scaleValue")
    @Mapping(target = "scaleDateTime", source = "dto.scaleDateTime")
    @Mapping(target = "scaleObservation", ignore = true)
    @Mapping(target = "freelancer", source = "freelancer")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "store", source = "store")
    Scale toEntity(CreateScaleDTO dto, Manager manager, Freelancer freelancer, Store store);
}
