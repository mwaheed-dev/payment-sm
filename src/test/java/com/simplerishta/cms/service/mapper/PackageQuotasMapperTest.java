package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageQuotasMapperTest {

    private PackageQuotasMapper packageQuotasMapper;

    @BeforeEach
    public void setUp() {
        packageQuotasMapper = new PackageQuotasMapperImpl();
    }
}
