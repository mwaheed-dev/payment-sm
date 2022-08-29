package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomersQuotasMapperTest {

    private CustomersQuotasMapper customersQuotasMapper;

    @BeforeEach
    public void setUp() {
        customersQuotasMapper = new CustomersQuotasMapperImpl();
    }
}
