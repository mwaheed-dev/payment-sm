package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFrequencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFrequency.class);
        PaymentFrequency paymentFrequency1 = new PaymentFrequency();
        paymentFrequency1.setId(1L);
        PaymentFrequency paymentFrequency2 = new PaymentFrequency();
        paymentFrequency2.setId(paymentFrequency1.getId());
        assertThat(paymentFrequency1).isEqualTo(paymentFrequency2);
        paymentFrequency2.setId(2L);
        assertThat(paymentFrequency1).isNotEqualTo(paymentFrequency2);
        paymentFrequency1.setId(null);
        assertThat(paymentFrequency1).isNotEqualTo(paymentFrequency2);
    }
}
