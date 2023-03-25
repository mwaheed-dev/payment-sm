package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Country;
import com.simplerishta.cms.domain.Packages;
import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.service.dto.CountryDTO;
import com.simplerishta.cms.service.dto.PackagesDTO;
import com.simplerishta.cms.service.dto.TariffDTO;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tariff} and its DTO {@link TariffDTO}.
 */
@Mapper(componentModel = "spring")
public interface TariffMapper extends EntityMapper<TariffDTO, Tariff> {
    @Mapping(target = "tariff", source = "tariff", qualifiedByName = "packagesId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryName")
    @Mapping(target = "tariff", source = "tariff", qualifiedByName = "userTariffId")
    TariffDTO toDto(Tariff s);

    @Named("packagesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PackagesDTO toDtoPackagesId(Packages packages);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);

    @Named("userTariffId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserTariffDTO toDtoUserTariffId(UserTariff userTariff);
}
