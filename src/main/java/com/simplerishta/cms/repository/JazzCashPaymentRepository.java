package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.JazzCashPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JazzCashPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JazzCashPaymentRepository extends JpaRepository<JazzCashPayment, Long> {}
