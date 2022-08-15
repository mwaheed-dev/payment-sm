package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerPackagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPackagesDTO.class);
        CustomerPackagesDTO customerPackagesDTO1 = new CustomerPackagesDTO();
        customerPackagesDTO1.setId(1L);
        CustomerPackagesDTO customerPackagesDTO2 = new CustomerPackagesDTO();
        assertThat(customerPackagesDTO1).isNotEqualTo(customerPackagesDTO2);
        customerPackagesDTO2.setId(customerPackagesDTO1.getId());
        assertThat(customerPackagesDTO1).isEqualTo(customerPackagesDTO2);
        customerPackagesDTO2.setId(2L);
        assertThat(customerPackagesDTO1).isNotEqualTo(customerPackagesDTO2);
        customerPackagesDTO1.setId(null);
        assertThat(customerPackagesDTO1).isNotEqualTo(customerPackagesDTO2);
    }
}
