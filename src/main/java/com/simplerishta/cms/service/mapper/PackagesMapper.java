package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.Packages;
import com.simplerishta.cms.service.dto.PackagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Packages} and its DTO {@link PackagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface PackagesMapper extends EntityMapper<PackagesDTO, Packages> {}
