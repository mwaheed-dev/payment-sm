package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerPackagesMapperTest {

    private CustomerPackagesMapper customerPackagesMapper;

    @BeforeEach
    public void setUp() {
        customerPackagesMapper = new CustomerPackagesMapperImpl();
    }
}
