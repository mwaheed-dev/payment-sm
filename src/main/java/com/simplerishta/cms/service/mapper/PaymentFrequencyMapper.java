package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.PaymentFrequency;
import com.simplerishta.cms.service.dto.PaymentFrequencyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentFrequency} and its DTO {@link PaymentFrequencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentFrequencyMapper extends EntityMapper<PaymentFrequencyDTO, PaymentFrequency> {}
