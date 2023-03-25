package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTariffDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTariffDTO.class);
        UserTariffDTO userTariffDTO1 = new UserTariffDTO();
        userTariffDTO1.setId(1L);
        UserTariffDTO userTariffDTO2 = new UserTariffDTO();
        assertThat(userTariffDTO1).isNotEqualTo(userTariffDTO2);
        userTariffDTO2.setId(userTariffDTO1.getId());
        assertThat(userTariffDTO1).isEqualTo(userTariffDTO2);
        userTariffDTO2.setId(2L);
        assertThat(userTariffDTO1).isNotEqualTo(userTariffDTO2);
        userTariffDTO1.setId(null);
        assertThat(userTariffDTO1).isNotEqualTo(userTariffDTO2);
    }
}
