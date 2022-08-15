package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.PaymentType;
import com.simplerishta.cms.service.dto.PaymentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentType} and its DTO {@link PaymentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentTypeMapper extends EntityMapper<PaymentTypeDTO, PaymentType> {}
