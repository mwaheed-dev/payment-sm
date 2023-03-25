package com.simplerishta.cms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JazzCashPaymentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JazzCashPaymentDTO.class);
        JazzCashPaymentDTO jazzCashPaymentDTO1 = new JazzCashPaymentDTO();
        jazzCashPaymentDTO1.setId(1L);
        JazzCashPaymentDTO jazzCashPaymentDTO2 = new JazzCashPaymentDTO();
        assertThat(jazzCashPaymentDTO1).isNotEqualTo(jazzCashPaymentDTO2);
        jazzCashPaymentDTO2.setId(jazzCashPaymentDTO1.getId());
        assertThat(jazzCashPaymentDTO1).isEqualTo(jazzCashPaymentDTO2);
        jazzCashPaymentDTO2.setId(2L);
        assertThat(jazzCashPaymentDTO1).isNotEqualTo(jazzCashPaymentDTO2);
        jazzCashPaymentDTO1.setId(null);
        assertThat(jazzCashPaymentDTO1).isNotEqualTo(jazzCashPaymentDTO2);
    }
}
