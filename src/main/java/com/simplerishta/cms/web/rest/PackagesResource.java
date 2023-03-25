package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.PackagesRepository;
import com.simplerishta.cms.service.PackagesService;
import com.simplerishta.cms.service.dto.PackagesDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.Packages}.
 */
@RestController
@RequestMapping("/api")
public class PackagesResource {

    private final Logger log = LoggerFactory.getLogger(PackagesResource.class);

    private static final String ENTITY_NAME = "simpleRishtaPackages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackagesService packagesService;

    private final PackagesRepository packagesRepository;

    public PackagesResource(PackagesService packagesService, PackagesRepository packagesRepository) {
        this.packagesService = packagesService;
        this.packagesRepository = packagesRepository;
    }

    /**
     * {@code POST  /packages} : Create a new packages.
     *
     * @param packagesDTO the packagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packagesDTO, or with status {@code 400 (Bad Request)} if the packages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/packages")
    public ResponseEntity<PackagesDTO> createPackages(@Valid @RequestBody PackagesDTO packagesDTO) throws URISyntaxException {
        log.debug("REST request to save Packages : {}", packagesDTO);
        if (packagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new packages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackagesDTO result = packagesService.save(packagesDTO);
        return ResponseEntity
            .created(new URI("/api/packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /packages/:id} : Updates an existing packages.
     *
     * @param id the id of the packagesDTO to save.
     * @param packagesDTO the packagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packagesDTO,
     * or with status {@code 400 (Bad Request)} if the packagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/packages/{id}")
    public ResponseEntity<PackagesDTO> updatePackages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PackagesDTO packagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Packages : {}, {}", id, packagesDTO);
        if (packagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackagesDTO result = packagesService.update(packagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /packages/:id} : Partial updates given fields of an existing packages, field will ignore if it is null
     *
     * @param id the id of the packagesDTO to save.
     * @param packagesDTO the packagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packagesDTO,
     * or with status {@code 400 (Bad Request)} if the packagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/packages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PackagesDTO> partialUpdatePackages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PackagesDTO packagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Packages partially : {}, {}", id, packagesDTO);
        if (packagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackagesDTO> result = packagesService.partialUpdate(packagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /packages} : get all the packages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packages in body.
     */
    @GetMapping("/packages")
    public List<PackagesDTO> getAllPackages() {
        log.debug("REST request to get all Packages");
        return packagesService.findAll();
    }

    /**
     * {@code GET  /packages/:id} : get the "id" packages.
     *
     * @param id the id of the packagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/packages/{id}")
    public ResponseEntity<PackagesDTO> getPackages(@PathVariable Long id) {
        log.debug("REST request to get Packages : {}", id);
        Optional<PackagesDTO> packagesDTO = packagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packagesDTO);
    }

    /**
     * {@code DELETE  /packages/:id} : delete the "id" packages.
     *
     * @param id the id of the packagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackages(@PathVariable Long id) {
        log.debug("REST request to delete Packages : {}", id);
        packagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
