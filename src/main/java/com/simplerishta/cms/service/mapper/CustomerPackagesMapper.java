package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.CustomerPackages;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPackages} and its DTO {@link CustomerPackagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerPackagesMapper extends EntityMapper<CustomerPackagesDTO, CustomerPackages> {}
