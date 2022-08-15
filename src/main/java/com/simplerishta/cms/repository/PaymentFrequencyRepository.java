package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.PaymentFrequency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentFrequencyRepository extends JpaRepository<PaymentFrequency, Long> {}
