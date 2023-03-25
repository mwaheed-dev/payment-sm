package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.UserTariff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserTariff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTariffRepository extends JpaRepository<UserTariff, Long> {}
