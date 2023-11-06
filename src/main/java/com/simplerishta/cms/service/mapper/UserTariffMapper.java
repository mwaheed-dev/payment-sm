package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Payments;
import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.service.dto.PaymentsDTO;
import com.simplerishta.cms.service.dto.TariffDTO;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserTariff} and its DTO {@link UserTariffDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTariffMapper extends EntityMapper<UserTariffDTO, UserTariff> {
    @Mapping(target = "tariff", source = "tariff", qualifiedByName = "tariffId")
    @Mapping(target = "payments", source = "payments", qualifiedByName = "paymentsId")
    UserTariffDTO toDto(UserTariff s);

    @Named("tariffId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TariffDTO toDtoTariffId(Tariff tariff);

    @Named("paymentsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentsDTO toDtoPaymentsId(Payments payments);
}
