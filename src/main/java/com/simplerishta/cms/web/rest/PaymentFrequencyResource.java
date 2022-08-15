package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.PaymentFrequencyRepository;
import com.simplerishta.cms.service.PaymentFrequencyService;
import com.simplerishta.cms.service.dto.PaymentFrequencyDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.PaymentFrequency}.
 */
@RestController
@RequestMapping("/api")
public class PaymentFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(PaymentFrequencyResource.class);

    private static final String ENTITY_NAME = "simpleRishtaPaymentFrequency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentFrequencyService paymentFrequencyService;

    private final PaymentFrequencyRepository paymentFrequencyRepository;

    public PaymentFrequencyResource(
        PaymentFrequencyService paymentFrequencyService,
        PaymentFrequencyRepository paymentFrequencyRepository
    ) {
        this.paymentFrequencyService = paymentFrequencyService;
        this.paymentFrequencyRepository = paymentFrequencyRepository;
    }

    /**
     * {@code POST  /payment-frequencies} : Create a new paymentFrequency.
     *
     * @param paymentFrequencyDTO the paymentFrequencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentFrequencyDTO, or with status {@code 400 (Bad Request)} if the paymentFrequency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-frequencies")
    public ResponseEntity<PaymentFrequencyDTO> createPaymentFrequency(@Valid @RequestBody PaymentFrequencyDTO paymentFrequencyDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentFrequency : {}", paymentFrequencyDTO);
        if (paymentFrequencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentFrequencyDTO result = paymentFrequencyService.save(paymentFrequencyDTO);
        return ResponseEntity
            .created(new URI("/api/payment-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-frequencies/:id} : Updates an existing paymentFrequency.
     *
     * @param id the id of the paymentFrequencyDTO to save.
     * @param paymentFrequencyDTO the paymentFrequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFrequencyDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFrequencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentFrequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-frequencies/{id}")
    public ResponseEntity<PaymentFrequencyDTO> updatePaymentFrequency(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentFrequencyDTO paymentFrequencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentFrequency : {}, {}", id, paymentFrequencyDTO);
        if (paymentFrequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFrequencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFrequencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentFrequencyDTO result = paymentFrequencyService.update(paymentFrequencyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFrequencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-frequencies/:id} : Partial updates given fields of an existing paymentFrequency, field will ignore if it is null
     *
     * @param id the id of the paymentFrequencyDTO to save.
     * @param paymentFrequencyDTO the paymentFrequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentFrequencyDTO,
     * or with status {@code 400 (Bad Request)} if the paymentFrequencyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentFrequencyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentFrequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-frequencies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentFrequencyDTO> partialUpdatePaymentFrequency(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentFrequencyDTO paymentFrequencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentFrequency partially : {}, {}", id, paymentFrequencyDTO);
        if (paymentFrequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentFrequencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentFrequencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentFrequencyDTO> result = paymentFrequencyService.partialUpdate(paymentFrequencyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentFrequencyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-frequencies} : get all the paymentFrequencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentFrequencies in body.
     */
    @GetMapping("/payment-frequencies")
    public List<PaymentFrequencyDTO> getAllPaymentFrequencies() {
        log.debug("REST request to get all PaymentFrequencies");
        return paymentFrequencyService.findAll();
    }

    /**
     * {@code GET  /payment-frequencies/:id} : get the "id" paymentFrequency.
     *
     * @param id the id of the paymentFrequencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentFrequencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-frequencies/{id}")
    public ResponseEntity<PaymentFrequencyDTO> getPaymentFrequency(@PathVariable Long id) {
        log.debug("REST request to get PaymentFrequency : {}", id);
        Optional<PaymentFrequencyDTO> paymentFrequencyDTO = paymentFrequencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentFrequencyDTO);
    }

    /**
     * {@code DELETE  /payment-frequencies/:id} : delete the "id" paymentFrequency.
     *
     * @param id the id of the paymentFrequencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-frequencies/{id}")
    public ResponseEntity<Void> deletePaymentFrequency(@PathVariable Long id) {
        log.debug("REST request to delete PaymentFrequency : {}", id);
        paymentFrequencyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
