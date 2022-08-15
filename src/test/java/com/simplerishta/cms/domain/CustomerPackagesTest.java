package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerPackagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPackages.class);
        CustomerPackages customerPackages1 = new CustomerPackages();
        customerPackages1.setId(1L);
        CustomerPackages customerPackages2 = new CustomerPackages();
        customerPackages2.setId(customerPackages1.getId());
        assertThat(customerPackages1).isEqualTo(customerPackages2);
        customerPackages2.setId(2L);
        assertThat(customerPackages1).isNotEqualTo(customerPackages2);
        customerPackages1.setId(null);
        assertThat(customerPackages1).isNotEqualTo(customerPackages2);
    }
}
