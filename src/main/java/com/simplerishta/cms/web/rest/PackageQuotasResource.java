package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.PackageQuotasRepository;
import com.simplerishta.cms.service.PackageQuotasService;
import com.simplerishta.cms.service.dto.PackageQuotasDTO;
import com.simplerishta.cms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplerishta.cms.domain.PackageQuotas}.
 */
@RestController
@RequestMapping("/api")
public class PackageQuotasResource {

    private final Logger log = LoggerFactory.getLogger(PackageQuotasResource.class);

    private static final String ENTITY_NAME = "simpleRishtaPackageQuotas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackageQuotasService packageQuotasService;

    private final PackageQuotasRepository packageQuotasRepository;

    public PackageQuotasResource(PackageQuotasService packageQuotasService, PackageQuotasRepository packageQuotasRepository) {
        this.packageQuotasService = packageQuotasService;
        this.packageQuotasRepository = packageQuotasRepository;
    }

    /**
     * {@code POST  /package-quotas} : Create a new packageQuotas.
     *
     * @param packageQuotasDTO the packageQuotasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packageQuotasDTO, or with status {@code 400 (Bad Request)} if the packageQuotas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/package-quotas")
    public ResponseEntity<PackageQuotasDTO> createPackageQuotas(@Valid @RequestBody PackageQuotasDTO packageQuotasDTO)
        throws URISyntaxException {
        log.debug("REST request to save PackageQuotas : {}", packageQuotasDTO);
        if (packageQuotasDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageQuotas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageQuotasDTO result = packageQuotasService.save(packageQuotasDTO);
        return ResponseEntity
            .created(new URI("/api/package-quotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /package-quotas/:id} : Updates an existing packageQuotas.
     *
     * @param id the id of the packageQuotasDTO to save.
     * @param packageQuotasDTO the packageQuotasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageQuotasDTO,
     * or with status {@code 400 (Bad Request)} if the packageQuotasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packageQuotasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/package-quotas/{id}")
    public ResponseEntity<PackageQuotasDTO> updatePackageQuotas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PackageQuotasDTO packageQuotasDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PackageQuotas : {}, {}", id, packageQuotasDTO);
        if (packageQuotasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageQuotasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageQuotasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackageQuotasDTO result = packageQuotasService.update(packageQuotasDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageQuotasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /package-quotas/:id} : Partial updates given fields of an existing packageQuotas, field will ignore if it is null
     *
     * @param id the id of the packageQuotasDTO to save.
     * @param packageQuotasDTO the packageQuotasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageQuotasDTO,
     * or with status {@code 400 (Bad Request)} if the packageQuotasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packageQuotasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packageQuotasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/package-quotas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PackageQuotasDTO> partialUpdatePackageQuotas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PackageQuotasDTO packageQuotasDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PackageQuotas partially : {}, {}", id, packageQuotasDTO);
        if (packageQuotasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageQuotasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageQuotasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackageQuotasDTO> result = packageQuotasService.partialUpdate(packageQuotasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageQuotasDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /package-quotas} : get all the packageQuotas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packageQuotas in body.
     */
    @GetMapping("/package-quotas")
    public List<PackageQuotasDTO> getAllPackageQuotas() {
        log.debug("REST request to get all PackageQuotas");
        return packageQuotasService.findAll();
    }

    /**
     * {@code GET  /package-quotas/:id} : get the "id" packageQuotas.
     *
     * @param id the id of the packageQuotasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packageQuotasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/package-quotas/{id}")
    public ResponseEntity<PackageQuotasDTO> getPackageQuotas(@PathVariable Long id) {
        log.debug("REST request to get PackageQuotas : {}", id);
        Optional<PackageQuotasDTO> packageQuotasDTO = packageQuotasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packageQuotasDTO);
    }

    /**
     * {@code DELETE  /package-quotas/:id} : delete the "id" packageQuotas.
     *
     * @param id the id of the packageQuotasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/package-quotas/{id}")
    public ResponseEntity<Void> deletePackageQuotas(@PathVariable Long id) {
        log.debug("REST request to delete PackageQuotas : {}", id);
        packageQuotasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
