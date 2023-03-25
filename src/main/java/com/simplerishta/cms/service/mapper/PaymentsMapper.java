package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Payments;
import com.simplerishta.cms.service.dto.PaymentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payments} and its DTO {@link PaymentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {}
