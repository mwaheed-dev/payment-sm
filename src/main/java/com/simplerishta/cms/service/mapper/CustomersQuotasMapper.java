package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.CustomerPackages;
import com.simplerishta.cms.domain.CustomersQuotas;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
import com.simplerishta.cms.service.dto.CustomersQuotasDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomersQuotas} and its DTO {@link CustomersQuotasDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomersQuotasMapper extends EntityMapper<CustomersQuotasDTO, CustomersQuotas> {
    @Mapping(target = "customerQuotas", source = "customerQuotas", qualifiedByName = "customerPackagesId")
    CustomersQuotasDTO toDto(CustomersQuotas s);

    @Named("customerPackagesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerPackagesDTO toDtoCustomerPackagesId(CustomerPackages customerPackages);
}
