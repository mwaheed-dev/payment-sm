package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageQuotasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageQuotasDTO.class);
        PackageQuotasDTO packageQuotasDTO1 = new PackageQuotasDTO();
        packageQuotasDTO1.setId(1L);
        PackageQuotasDTO packageQuotasDTO2 = new PackageQuotasDTO();
        assertThat(packageQuotasDTO1).isNotEqualTo(packageQuotasDTO2);
        packageQuotasDTO2.setId(packageQuotasDTO1.getId());
        assertThat(packageQuotasDTO1).isEqualTo(packageQuotasDTO2);
        packageQuotasDTO2.setId(2L);
        assertThat(packageQuotasDTO1).isNotEqualTo(packageQuotasDTO2);
        packageQuotasDTO1.setId(null);
        assertThat(packageQuotasDTO1).isNotEqualTo(packageQuotasDTO2);
    }
}
