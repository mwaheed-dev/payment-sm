package com.simplerishta.cms.web.rest;

import com.simplerishta.cms.repository.UserTariffRepository;
import com.simplerishta.cms.service.UserTariffService;
import com.simplerishta.cms.service.dto.UserTariffDTO;
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
 * REST controller for managing {@link com.simplerishta.cms.domain.UserTariff}.
 */
@RestController
@RequestMapping("/api")
public class UserTariffResource {

    private final Logger log = LoggerFactory.getLogger(UserTariffResource.class);

    private static final String ENTITY_NAME = "simpleRishtaUserTariff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTariffService userTariffService;

    private final UserTariffRepository userTariffRepository;

    public UserTariffResource(UserTariffService userTariffService, UserTariffRepository userTariffRepository) {
        this.userTariffService = userTariffService;
        this.userTariffRepository = userTariffRepository;
    }

    /**
     * {@code POST  /user-tariffs} : Create a new userTariff.
     *
     * @param userTariffDTO the userTariffDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTariffDTO, or with status {@code 400 (Bad Request)} if the userTariff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-tariffs")
    public ResponseEntity<UserTariffDTO> createUserTariff(@RequestBody UserTariffDTO userTariffDTO) throws URISyntaxException {
        log.debug("REST request to save UserTariff : {}", userTariffDTO);
        if (userTariffDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTariff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTariffDTO result = userTariffService.save(userTariffDTO);
        return ResponseEntity
            .created(new URI("/api/user-tariffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-tariffs/:id} : Updates an existing userTariff.
     *
     * @param id the id of the userTariffDTO to save.
     * @param userTariffDTO the userTariffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTariffDTO,
     * or with status {@code 400 (Bad Request)} if the userTariffDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTariffDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-tariffs/{id}")
    public ResponseEntity<UserTariffDTO> updateUserTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTariffDTO userTariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserTariff : {}, {}", id, userTariffDTO);
        if (userTariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTariffDTO result = userTariffService.update(userTariffDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTariffDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-tariffs/:id} : Partial updates given fields of an existing userTariff, field will ignore if it is null
     *
     * @param id the id of the userTariffDTO to save.
     * @param userTariffDTO the userTariffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTariffDTO,
     * or with status {@code 400 (Bad Request)} if the userTariffDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTariffDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTariffDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-tariffs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTariffDTO> partialUpdateUserTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTariffDTO userTariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserTariff partially : {}, {}", id, userTariffDTO);
        if (userTariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTariffDTO> result = userTariffService.partialUpdate(userTariffDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTariffDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-tariffs} : get all the userTariffs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTariffs in body.
     */
    @GetMapping("/user-tariffs")
    public List<UserTariffDTO> getAllUserTariffs() {
        log.debug("REST request to get all UserTariffs");
        return userTariffService.findAll();
    }

    /**
     * {@code GET  /user-tariffs/:id} : get the "id" userTariff.
     *
     * @param id the id of the userTariffDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTariffDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-tariffs/{id}")
    public ResponseEntity<UserTariffDTO> getUserTariff(@PathVariable Long id) {
        log.debug("REST request to get UserTariff : {}", id);
        Optional<UserTariffDTO> userTariffDTO = userTariffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTariffDTO);
    }

    /**
     * {@code DELETE  /user-tariffs/:id} : delete the "id" userTariff.
     *
     * @param id the id of the userTariffDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-tariffs/{id}")
    public ResponseEntity<Void> deleteUserTariff(@PathVariable Long id) {
        log.debug("REST request to delete UserTariff : {}", id);
        userTariffService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
