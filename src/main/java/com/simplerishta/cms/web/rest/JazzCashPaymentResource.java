package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.JazzCashPaymentRepository;
import com.simplerishta.cms.service.JazzCashPaymentService;
import com.simplerishta.cms.service.dto.JazzCashPaymentDTO;
import com.simplerishta.cms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplerishta.cms.domain.JazzCashPayment}.
 */
@RestController
@RequestMapping("/api")
public class JazzCashPaymentResource {

    private final Logger log = LoggerFactory.getLogger(JazzCashPaymentResource.class);

    private static final String ENTITY_NAME = "simpleRishtaJazzCashPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JazzCashPaymentService jazzCashPaymentService;

    private final JazzCashPaymentRepository jazzCashPaymentRepository;

    public JazzCashPaymentResource(JazzCashPaymentService jazzCashPaymentService, JazzCashPaymentRepository jazzCashPaymentRepository) {
        this.jazzCashPaymentService = jazzCashPaymentService;
        this.jazzCashPaymentRepository = jazzCashPaymentRepository;
    }

    /**
     * {@code POST  /jazz-cash-payments} : Create a new jazzCashPayment.
     *
     * @param jazzCashPaymentDTO the jazzCashPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jazzCashPaymentDTO, or with status {@code 400 (Bad Request)} if the jazzCashPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jazz-cash-payments")
    public ResponseEntity<JazzCashPaymentDTO> createJazzCashPayment(@RequestBody JazzCashPaymentDTO jazzCashPaymentDTO)
        throws URISyntaxException {
        log.debug("REST request to save JazzCashPayment : {}", jazzCashPaymentDTO);
        if (jazzCashPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new jazzCashPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JazzCashPaymentDTO result = jazzCashPaymentService.save(jazzCashPaymentDTO);
        return ResponseEntity
            .created(new URI("/api/jazz-cash-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jazz-cash-payments/:id} : Updates an existing jazzCashPayment.
     *
     * @param id the id of the jazzCashPaymentDTO to save.
     * @param jazzCashPaymentDTO the jazzCashPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jazzCashPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the jazzCashPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jazzCashPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jazz-cash-payments/{id}")
    public ResponseEntity<JazzCashPaymentDTO> updateJazzCashPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JazzCashPaymentDTO jazzCashPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JazzCashPayment : {}, {}", id, jazzCashPaymentDTO);
        if (jazzCashPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jazzCashPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jazzCashPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JazzCashPaymentDTO result = jazzCashPaymentService.update(jazzCashPaymentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jazzCashPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /jazz-cash-payments/:id} : Partial updates given fields of an existing jazzCashPayment, field will ignore if it is null
     *
     * @param id the id of the jazzCashPaymentDTO to save.
     * @param jazzCashPaymentDTO the jazzCashPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jazzCashPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the jazzCashPaymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jazzCashPaymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jazzCashPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/jazz-cash-payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JazzCashPaymentDTO> partialUpdateJazzCashPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JazzCashPaymentDTO jazzCashPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JazzCashPayment partially : {}, {}", id, jazzCashPaymentDTO);
        if (jazzCashPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jazzCashPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jazzCashPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JazzCashPaymentDTO> result = jazzCashPaymentService.partialUpdate(jazzCashPaymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jazzCashPaymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /jazz-cash-payments} : get all the jazzCashPayments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jazzCashPayments in body.
     */
    @GetMapping("/jazz-cash-payments")
    public List<JazzCashPaymentDTO> getAllJazzCashPayments() {
        log.debug("REST request to get all JazzCashPayments");
        return jazzCashPaymentService.findAll();
    }

    /**
     * {@code GET  /jazz-cash-payments/:id} : get the "id" jazzCashPayment.
     *
     * @param id the id of the jazzCashPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jazzCashPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jazz-cash-payments/{id}")
    public ResponseEntity<JazzCashPaymentDTO> getJazzCashPayment(@PathVariable Long id) {
        log.debug("REST request to get JazzCashPayment : {}", id);
        Optional<JazzCashPaymentDTO> jazzCashPaymentDTO = jazzCashPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jazzCashPaymentDTO);
    }

    /**
     * {@code DELETE  /jazz-cash-payments/:id} : delete the "id" jazzCashPayment.
     *
     * @param id the id of the jazzCashPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jazz-cash-payments/{id}")
    public ResponseEntity<Void> deleteJazzCashPayment(@PathVariable Long id) {
        log.debug("REST request to delete JazzCashPayment : {}", id);
        jazzCashPaymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
