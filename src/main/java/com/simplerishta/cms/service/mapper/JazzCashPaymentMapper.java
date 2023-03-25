package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.JazzCashPayment;
import com.simplerishta.cms.domain.Payments;
import com.simplerishta.cms.service.dto.JazzCashPaymentDTO;
import com.simplerishta.cms.service.dto.PaymentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JazzCashPayment} and its DTO {@link JazzCashPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface JazzCashPaymentMapper extends EntityMapper<JazzCashPaymentDTO, JazzCashPayment> {
    @Mapping(target = "payments", source = "payments", qualifiedByName = "paymentsId")
    JazzCashPaymentDTO toDto(JazzCashPayment s);

    @Named("paymentsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentsDTO toDtoPaymentsId(Payments payments);
}
