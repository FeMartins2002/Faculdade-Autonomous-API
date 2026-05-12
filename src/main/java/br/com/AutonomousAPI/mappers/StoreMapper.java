package br.com.AutonomousAPI.mappers;

import br.com.AutonomousAPI.dtos.request.store.CreateStoreDTO;
import br.com.AutonomousAPI.dtos.response.store.StoreOptions;
import br.com.AutonomousAPI.dtos.response.store.StoreResponseDTO;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "phone", source = "dto.phone")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "manager", source = "manager")
    Store toEntity(CreateStoreDTO dto, Manager manager);

    StoreResponseDTO toResponse(Store store);

    List<StoreResponseDTO> toResponseList(List<Store> stores);

    List<StoreOptions> toOptionsList(List<Store> stores);
}
