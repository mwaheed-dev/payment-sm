package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTariffTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTariff.class);
        UserTariff userTariff1 = new UserTariff();
        userTariff1.setId(1L);
        UserTariff userTariff2 = new UserTariff();
        userTariff2.setId(userTariff1.getId());
        assertThat(userTariff1).isEqualTo(userTariff2);
        userTariff2.setId(2L);
        assertThat(userTariff1).isNotEqualTo(userTariff2);
        userTariff1.setId(null);
        assertThat(userTariff1).isNotEqualTo(userTariff2);
    }
}
