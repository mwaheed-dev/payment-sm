package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.CustomersQuotas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomersQuotas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersQuotasRepository extends JpaRepository<CustomersQuotas, Long> {}
