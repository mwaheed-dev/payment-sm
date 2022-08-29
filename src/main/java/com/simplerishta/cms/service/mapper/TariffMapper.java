package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Country;
import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.service.dto.CountryDTO;
import com.simplerishta.cms.service.dto.TariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tariff} and its DTO {@link TariffDTO}.
 */
@Mapper(componentModel = "spring")
public interface TariffMapper extends EntityMapper<TariffDTO, Tariff> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryName")
    TariffDTO toDto(Tariff s);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);
}
