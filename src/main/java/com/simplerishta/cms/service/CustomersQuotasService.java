package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.CustomersQuotas;
import com.simplerishta.cms.repository.CustomersQuotasRepository;
import com.simplerishta.cms.service.dto.CustomersQuotasDTO;
import com.simplerishta.cms.service.mapper.CustomersQuotasMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomersQuotas}.
 */
@Service
@Transactional
public class CustomersQuotasService {

    private final Logger log = LoggerFactory.getLogger(CustomersQuotasService.class);

    private final CustomersQuotasRepository customersQuotasRepository;

    private final CustomersQuotasMapper customersQuotasMapper;

    public CustomersQuotasService(CustomersQuotasRepository customersQuotasRepository, CustomersQuotasMapper customersQuotasMapper) {
        this.customersQuotasRepository = customersQuotasRepository;
        this.customersQuotasMapper = customersQuotasMapper;
    }

    /**
     * Save a customersQuotas.
     *
     * @param customersQuotasDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomersQuotasDTO save(CustomersQuotasDTO customersQuotasDTO) {
        log.debug("Request to save CustomersQuotas : {}", customersQuotasDTO);
        CustomersQuotas customersQuotas = customersQuotasMapper.toEntity(customersQuotasDTO);
        customersQuotas = customersQuotasRepository.save(customersQuotas);
        return customersQuotasMapper.toDto(customersQuotas);
    }

    /**
     * Update a customersQuotas.
     *
     * @param customersQuotasDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomersQuotasDTO update(CustomersQuotasDTO customersQuotasDTO) {
        log.debug("Request to save CustomersQuotas : {}", customersQuotasDTO);
        CustomersQuotas customersQuotas = customersQuotasMapper.toEntity(customersQuotasDTO);
        customersQuotas = customersQuotasRepository.save(customersQuotas);
        return customersQuotasMapper.toDto(customersQuotas);
    }

    /**
     * Partially update a customersQuotas.
     *
     * @param customersQuotasDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomersQuotasDTO> partialUpdate(CustomersQuotasDTO customersQuotasDTO) {
        log.debug("Request to partially update CustomersQuotas : {}", customersQuotasDTO);

        return customersQuotasRepository
            .findById(customersQuotasDTO.getId())
            .map(existingCustomersQuotas -> {
                customersQuotasMapper.partialUpdate(existingCustomersQuotas, customersQuotasDTO);

                return existingCustomersQuotas;
            })
            .map(customersQuotasRepository::save)
            .map(customersQuotasMapper::toDto);
    }

    /**
     * Get all the customersQuotas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomersQuotasDTO> findAll() {
        log.debug("Request to get all CustomersQuotas");
        return customersQuotasRepository
            .findAll()
            .stream()
            .map(customersQuotasMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one customersQuotas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomersQuotasDTO> findOne(Long id) {
        log.debug("Request to get CustomersQuotas : {}", id);
        return customersQuotasRepository.findById(id).map(customersQuotasMapper::toDto);
    }

    /**
     * Delete the customersQuotas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomersQuotas : {}", id);
        customersQuotasRepository.deleteById(id);
    }
}
