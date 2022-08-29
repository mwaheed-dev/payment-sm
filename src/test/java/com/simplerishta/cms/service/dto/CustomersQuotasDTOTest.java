package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersQuotasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersQuotasDTO.class);
        CustomersQuotasDTO customersQuotasDTO1 = new CustomersQuotasDTO();
        customersQuotasDTO1.setId(1L);
        CustomersQuotasDTO customersQuotasDTO2 = new CustomersQuotasDTO();
        assertThat(customersQuotasDTO1).isNotEqualTo(customersQuotasDTO2);
        customersQuotasDTO2.setId(customersQuotasDTO1.getId());
        assertThat(customersQuotasDTO1).isEqualTo(customersQuotasDTO2);
        customersQuotasDTO2.setId(2L);
        assertThat(customersQuotasDTO1).isNotEqualTo(customersQuotasDTO2);
        customersQuotasDTO1.setId(null);
        assertThat(customersQuotasDTO1).isNotEqualTo(customersQuotasDTO2);
    }
}
