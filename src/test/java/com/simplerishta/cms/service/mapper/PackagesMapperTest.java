package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackagesMapperTest {

    private PackagesMapper packagesMapper;

    @BeforeEach
    public void setUp() {
        packagesMapper = new PackagesMapperImpl();
    }
}
