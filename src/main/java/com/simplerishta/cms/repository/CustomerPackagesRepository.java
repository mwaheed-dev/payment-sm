package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.CustomerPackages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerPackages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPackagesRepository extends JpaRepository<CustomerPackages, Long> {}
