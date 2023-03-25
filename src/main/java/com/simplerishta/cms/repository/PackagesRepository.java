package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.Packages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Packages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackagesRepository extends JpaRepository<Packages, Long> {}
