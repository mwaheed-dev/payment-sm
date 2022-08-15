package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.CustomerPackagesRepository;
import com.simplerishta.cms.service.CustomerPackagesService;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.CustomerPackages}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPackagesResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPackagesResource.class);

    private static final String ENTITY_NAME = "simpleRishtaCustomerPackages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPackagesService customerPackagesService;

    private final CustomerPackagesRepository customerPackagesRepository;

    public CustomerPackagesResource(
        CustomerPackagesService customerPackagesService,
        CustomerPackagesRepository customerPackagesRepository
    ) {
        this.customerPackagesService = customerPackagesService;
        this.customerPackagesRepository = customerPackagesRepository;
    }

    /**
     * {@code POST  /customer-packages} : Create a new customerPackages.
     *
     * @param customerPackagesDTO the customerPackagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPackagesDTO, or with status {@code 400 (Bad Request)} if the customerPackages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-packages")
    public ResponseEntity<CustomerPackagesDTO> createCustomerPackages(@Valid @RequestBody CustomerPackagesDTO customerPackagesDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomerPackages : {}", customerPackagesDTO);
        if (customerPackagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPackages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPackagesDTO result = customerPackagesService.save(customerPackagesDTO);
        return ResponseEntity
            .created(new URI("/api/customer-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-packages/:id} : Updates an existing customerPackages.
     *
     * @param id the id of the customerPackagesDTO to save.
     * @param customerPackagesDTO the customerPackagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPackagesDTO,
     * or with status {@code 400 (Bad Request)} if the customerPackagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPackagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-packages/{id}")
    public ResponseEntity<CustomerPackagesDTO> updateCustomerPackages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerPackagesDTO customerPackagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerPackages : {}, {}", id, customerPackagesDTO);
        if (customerPackagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerPackagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerPackagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerPackagesDTO result = customerPackagesService.update(customerPackagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPackagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-packages/:id} : Partial updates given fields of an existing customerPackages, field will ignore if it is null
     *
     * @param id the id of the customerPackagesDTO to save.
     * @param customerPackagesDTO the customerPackagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPackagesDTO,
     * or with status {@code 400 (Bad Request)} if the customerPackagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerPackagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerPackagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-packages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerPackagesDTO> partialUpdateCustomerPackages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerPackagesDTO customerPackagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerPackages partially : {}, {}", id, customerPackagesDTO);
        if (customerPackagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerPackagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerPackagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerPackagesDTO> result = customerPackagesService.partialUpdate(customerPackagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPackagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-packages} : get all the customerPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPackages in body.
     */
    @GetMapping("/customer-packages")
    public List<CustomerPackagesDTO> getAllCustomerPackages() {
        log.debug("REST request to get all CustomerPackages");
        return customerPackagesService.findAll();
    }

    /**
     * {@code GET  /customer-packages/:id} : get the "id" customerPackages.
     *
     * @param id the id of the customerPackagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPackagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-packages/{id}")
    public ResponseEntity<CustomerPackagesDTO> getCustomerPackages(@PathVariable Long id) {
        log.debug("REST request to get CustomerPackages : {}", id);
        Optional<CustomerPackagesDTO> customerPackagesDTO = customerPackagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPackagesDTO);
    }

    /**
     * {@code DELETE  /customer-packages/:id} : delete the "id" customerPackages.
     *
     * @param id the id of the customerPackagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-packages/{id}")
    public ResponseEntity<Void> deleteCustomerPackages(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPackages : {}", id);
        customerPackagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
