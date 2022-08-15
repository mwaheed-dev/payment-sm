package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentFrequencyMapperTest {

    private PaymentFrequencyMapper paymentFrequencyMapper;

    @BeforeEach
    public void setUp() {
        paymentFrequencyMapper = new PaymentFrequencyMapperImpl();
    }
}
