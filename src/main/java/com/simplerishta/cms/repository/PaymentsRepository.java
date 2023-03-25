package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.Payments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Payments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {}
