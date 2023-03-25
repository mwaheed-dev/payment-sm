package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Payments;
import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.service.dto.PaymentsDTO;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payments} and its DTO {@link PaymentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {
    @Mapping(target = "payments", source = "payments", qualifiedByName = "userTariffId")
    PaymentsDTO toDto(Payments s);

    @Named("userTariffId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserTariffDTO toDtoUserTariffId(UserTariff userTariff);
}
