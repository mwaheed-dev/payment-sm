package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFrequencyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFrequencyDTO.class);
        PaymentFrequencyDTO paymentFrequencyDTO1 = new PaymentFrequencyDTO();
        paymentFrequencyDTO1.setId(1L);
        PaymentFrequencyDTO paymentFrequencyDTO2 = new PaymentFrequencyDTO();
        assertThat(paymentFrequencyDTO1).isNotEqualTo(paymentFrequencyDTO2);
        paymentFrequencyDTO2.setId(paymentFrequencyDTO1.getId());
        assertThat(paymentFrequencyDTO1).isEqualTo(paymentFrequencyDTO2);
        paymentFrequencyDTO2.setId(2L);
        assertThat(paymentFrequencyDTO1).isNotEqualTo(paymentFrequencyDTO2);
        paymentFrequencyDTO1.setId(null);
        assertThat(paymentFrequencyDTO1).isNotEqualTo(paymentFrequencyDTO2);
    }
}
