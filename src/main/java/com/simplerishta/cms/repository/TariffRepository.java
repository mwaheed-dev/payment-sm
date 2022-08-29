package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.Tariff;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tariff entity.
 */
@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
    default Optional<Tariff> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Tariff> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Tariff> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct tariff from Tariff tariff left join fetch tariff.country",
        countQuery = "select count(distinct tariff) from Tariff tariff"
    )
    Page<Tariff> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct tariff from Tariff tariff left join fetch tariff.country")
    List<Tariff> findAllWithToOneRelationships();

    @Query("select tariff from Tariff tariff left join fetch tariff.country where tariff.id =:id")
    Optional<Tariff> findOneWithToOneRelationships(@Param("id") Long id);
}
