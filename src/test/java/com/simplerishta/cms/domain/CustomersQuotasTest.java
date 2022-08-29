package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersQuotasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersQuotas.class);
        CustomersQuotas customersQuotas1 = new CustomersQuotas();
        customersQuotas1.setId(1L);
        CustomersQuotas customersQuotas2 = new CustomersQuotas();
        customersQuotas2.setId(customersQuotas1.getId());
        assertThat(customersQuotas1).isEqualTo(customersQuotas2);
        customersQuotas2.setId(2L);
        assertThat(customersQuotas1).isNotEqualTo(customersQuotas2);
        customersQuotas1.setId(null);
        assertThat(customersQuotas1).isNotEqualTo(customersQuotas2);
    }
}
