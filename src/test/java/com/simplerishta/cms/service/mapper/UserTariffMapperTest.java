package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTariffMapperTest {

    private UserTariffMapper userTariffMapper;

    @BeforeEach
    public void setUp() {
        userTariffMapper = new UserTariffMapperImpl();
    }
}
