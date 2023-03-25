package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.PackageQuotas;
import com.simplerishta.cms.domain.Packages;
import com.simplerishta.cms.service.dto.PackageQuotasDTO;
import com.simplerishta.cms.service.dto.PackagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackageQuotas} and its DTO {@link PackageQuotasDTO}.
 */
@Mapper(componentModel = "spring")
public interface PackageQuotasMapper extends EntityMapper<PackageQuotasDTO, PackageQuotas> {
    @Mapping(target = "customerQuotas", source = "customerQuotas", qualifiedByName = "packagesId")
    PackageQuotasDTO toDto(PackageQuotas s);

    @Named("packagesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PackagesDTO toDtoPackagesId(Packages packages);
}
