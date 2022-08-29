package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.CustomersQuotasRepository;
import com.simplerishta.cms.service.CustomersQuotasService;
import com.simplerishta.cms.service.dto.CustomersQuotasDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.CustomersQuotas}.
 */
@RestController
@RequestMapping("/api")
public class CustomersQuotasResource {

    private final Logger log = LoggerFactory.getLogger(CustomersQuotasResource.class);

    private static final String ENTITY_NAME = "simpleRishtaCustomersQuotas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersQuotasService customersQuotasService;

    private final CustomersQuotasRepository customersQuotasRepository;

    public CustomersQuotasResource(CustomersQuotasService customersQuotasService, CustomersQuotasRepository customersQuotasRepository) {
        this.customersQuotasService = customersQuotasService;
        this.customersQuotasRepository = customersQuotasRepository;
    }

    /**
     * {@code POST  /customers-quotas} : Create a new customersQuotas.
     *
     * @param customersQuotasDTO the customersQuotasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersQuotasDTO, or with status {@code 400 (Bad Request)} if the customersQuotas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers-quotas")
    public ResponseEntity<CustomersQuotasDTO> createCustomersQuotas(@Valid @RequestBody CustomersQuotasDTO customersQuotasDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomersQuotas : {}", customersQuotasDTO);
        if (customersQuotasDTO.getId() != null) {
            throw new BadRequestAlertException("A new customersQuotas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersQuotasDTO result = customersQuotasService.save(customersQuotasDTO);
        return ResponseEntity
            .created(new URI("/api/customers-quotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers-quotas/:id} : Updates an existing customersQuotas.
     *
     * @param id the id of the customersQuotasDTO to save.
     * @param customersQuotasDTO the customersQuotasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersQuotasDTO,
     * or with status {@code 400 (Bad Request)} if the customersQuotasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersQuotasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers-quotas/{id}")
    public ResponseEntity<CustomersQuotasDTO> updateCustomersQuotas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomersQuotasDTO customersQuotasDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomersQuotas : {}, {}", id, customersQuotasDTO);
        if (customersQuotasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersQuotasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersQuotasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomersQuotasDTO result = customersQuotasService.update(customersQuotasDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersQuotasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers-quotas/:id} : Partial updates given fields of an existing customersQuotas, field will ignore if it is null
     *
     * @param id the id of the customersQuotasDTO to save.
     * @param customersQuotasDTO the customersQuotasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersQuotasDTO,
     * or with status {@code 400 (Bad Request)} if the customersQuotasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customersQuotasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customersQuotasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers-quotas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomersQuotasDTO> partialUpdateCustomersQuotas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomersQuotasDTO customersQuotasDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomersQuotas partially : {}, {}", id, customersQuotasDTO);
        if (customersQuotasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersQuotasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersQuotasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomersQuotasDTO> result = customersQuotasService.partialUpdate(customersQuotasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersQuotasDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customers-quotas} : get all the customersQuotas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customersQuotas in body.
     */
    @GetMapping("/customers-quotas")
    public List<CustomersQuotasDTO> getAllCustomersQuotas() {
        log.debug("REST request to get all CustomersQuotas");
        return customersQuotasService.findAll();
    }

    /**
     * {@code GET  /customers-quotas/:id} : get the "id" customersQuotas.
     *
     * @param id the id of the customersQuotasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersQuotasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers-quotas/{id}")
    public ResponseEntity<CustomersQuotasDTO> getCustomersQuotas(@PathVariable Long id) {
        log.debug("REST request to get CustomersQuotas : {}", id);
        Optional<CustomersQuotasDTO> customersQuotasDTO = customersQuotasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersQuotasDTO);
    }

    /**
     * {@code DELETE  /customers-quotas/:id} : delete the "id" customersQuotas.
     *
     * @param id the id of the customersQuotasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers-quotas/{id}")
    public ResponseEntity<Void> deleteCustomersQuotas(@PathVariable Long id) {
        log.debug("REST request to delete CustomersQuotas : {}", id);
        customersQuotasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
