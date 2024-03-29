package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.PaymentsRepository;
import com.simplerishta.cms.service.PaymentsService;
import com.simplerishta.cms.service.dto.PaymentsDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.Payments}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "simpleRishtaPayments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsService paymentsService;

    private final PaymentsRepository paymentsRepository;

    public PaymentsResource(PaymentsService paymentsService, PaymentsRepository paymentsRepository) {
        this.paymentsService = paymentsService;
        this.paymentsRepository = paymentsRepository;
    }

    /**
     * {@code POST  /payments} : Create a new payments.
     *
     * @param paymentsDTO the paymentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentsDTO, or with status {@code 400 (Bad Request)} if the payments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<PaymentsDTO> createPayments(@Valid @RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", paymentsDTO);
        if (paymentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new payments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity
            .created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments/:id} : Updates an existing payments.
     *
     * @param id the id of the paymentsDTO to save.
     * @param paymentsDTO the paymentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments/{id}")
    public ResponseEntity<PaymentsDTO> updatePayments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentsDTO paymentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Payments : {}, {}", id, paymentsDTO);
        if (paymentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentsDTO result = paymentsService.update(paymentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payments/:id} : Partial updates given fields of an existing payments, field will ignore if it is null
     *
     * @param id the id of the paymentsDTO to save.
     * @param paymentsDTO the paymentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentsDTO> partialUpdatePayments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentsDTO paymentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payments partially : {}, {}", id, paymentsDTO);
        if (paymentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentsDTO> result = paymentsService.partialUpdate(paymentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public List<PaymentsDTO> getAllPayments() {
        log.debug("REST request to get all Payments");
        return paymentsService.findAll();
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payments.
     *
     * @param id the id of the paymentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentsDTO> getPayments(@PathVariable Long id) {
        log.debug("REST request to get Payments : {}", id);
        Optional<PaymentsDTO> paymentsDTO = paymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsDTO);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payments.
     *
     * @param id the id of the paymentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayments(@PathVariable Long id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
