package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.CustomerPackages;
import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
import com.simplerishta.cms.service.dto.TariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPackages} and its DTO {@link CustomerPackagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerPackagesMapper extends EntityMapper<CustomerPackagesDTO, CustomerPackages> {
    @Mapping(target = "tariff", source = "tariff", qualifiedByName = "tariffId")
    CustomerPackagesDTO toDto(CustomerPackages s);

    @Named("tariffId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TariffDTO toDtoTariffId(Tariff tariff);
}
