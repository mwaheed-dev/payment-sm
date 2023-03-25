package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageQuotasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageQuotas.class);
        PackageQuotas packageQuotas1 = new PackageQuotas();
        packageQuotas1.setId(1L);
        PackageQuotas packageQuotas2 = new PackageQuotas();
        packageQuotas2.setId(packageQuotas1.getId());
        assertThat(packageQuotas1).isEqualTo(packageQuotas2);
        packageQuotas2.setId(2L);
        assertThat(packageQuotas1).isNotEqualTo(packageQuotas2);
        packageQuotas1.setId(null);
        assertThat(packageQuotas1).isNotEqualTo(packageQuotas2);
    }
}
