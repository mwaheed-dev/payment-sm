package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserTariff} and its DTO {@link UserTariffDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTariffMapper extends EntityMapper<UserTariffDTO, UserTariff> {}
