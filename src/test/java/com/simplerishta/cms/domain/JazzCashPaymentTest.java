package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JazzCashPaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JazzCashPayment.class);
        JazzCashPayment jazzCashPayment1 = new JazzCashPayment();
        jazzCashPayment1.setId(1L);
        JazzCashPayment jazzCashPayment2 = new JazzCashPayment();
        jazzCashPayment2.setId(jazzCashPayment1.getId());
        assertThat(jazzCashPayment1).isEqualTo(jazzCashPayment2);
        jazzCashPayment2.setId(2L);
        assertThat(jazzCashPayment1).isNotEqualTo(jazzCashPayment2);
        jazzCashPayment1.setId(null);
        assertThat(jazzCashPayment1).isNotEqualTo(jazzCashPayment2);
    }
}
