package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.PackageQuotas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PackageQuotas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageQuotasRepository extends JpaRepository<PackageQuotas, Long> {}
