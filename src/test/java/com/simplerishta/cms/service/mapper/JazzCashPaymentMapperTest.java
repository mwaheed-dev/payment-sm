package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JazzCashPaymentMapperTest {

    private JazzCashPaymentMapper jazzCashPaymentMapper;

    @BeforeEach
    public void setUp() {
        jazzCashPaymentMapper = new JazzCashPaymentMapperImpl();
    }
}
