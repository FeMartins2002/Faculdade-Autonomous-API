package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.response.manager.ManagerResponseDTO;
import br.com.AutonomousAPI.entities.Manager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerResponseDTO toResponse(Manager manager);
}
