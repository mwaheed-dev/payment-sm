package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Country;
import com.simplerishta.cms.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {}
