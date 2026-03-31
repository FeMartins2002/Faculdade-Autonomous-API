package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.response.point.PointResponseDTO;
import br.com.AutonomousAPI.entities.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    PointResponseDTO toResponse(Point point);
}
