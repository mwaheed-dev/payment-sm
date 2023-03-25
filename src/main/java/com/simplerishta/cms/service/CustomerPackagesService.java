package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.CustomerPackages;
import com.simplerishta.cms.repository.CustomerPackagesRepository;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
import com.simplerishta.cms.service.mapper.CustomerPackagesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerPackages}.
 */
@Service
@Transactional
public class CustomerPackagesService {

    private final Logger log = LoggerFactory.getLogger(CustomerPackagesService.class);

    private final CustomerPackagesRepository customerPackagesRepository;

    private final CustomerPackagesMapper customerPackagesMapper;

    public CustomerPackagesService(CustomerPackagesRepository customerPackagesRepository, CustomerPackagesMapper customerPackagesMapper) {
        this.customerPackagesRepository = customerPackagesRepository;
        this.customerPackagesMapper = customerPackagesMapper;
    }

    /**
     * Save a customerPackages.
     *
     * @param customerPackagesDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerPackagesDTO save(CustomerPackagesDTO customerPackagesDTO) {
        log.debug("Request to save CustomerPackages : {}", customerPackagesDTO);
        CustomerPackages customerPackages = customerPackagesMapper.toEntity(customerPackagesDTO);
        customerPackages = customerPackagesRepository.save(customerPackages);
        return customerPackagesMapper.toDto(customerPackages);
    }

    /**
     * Update a customerPackages.
     *
     * @param customerPackagesDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerPackagesDTO update(CustomerPackagesDTO customerPackagesDTO) {
        log.debug("Request to update CustomerPackages : {}", customerPackagesDTO);
        CustomerPackages customerPackages = customerPackagesMapper.toEntity(customerPackagesDTO);
        customerPackages = customerPackagesRepository.save(customerPackages);
        return customerPackagesMapper.toDto(customerPackages);
    }

    /**
     * Partially update a customerPackages.
     *
     * @param customerPackagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerPackagesDTO> partialUpdate(CustomerPackagesDTO customerPackagesDTO) {
        log.debug("Request to partially update CustomerPackages : {}", customerPackagesDTO);

        return customerPackagesRepository
            .findById(customerPackagesDTO.getId())
            .map(existingCustomerPackages -> {
                customerPackagesMapper.partialUpdate(existingCustomerPackages, customerPackagesDTO);

                return existingCustomerPackages;
            })
            .map(customerPackagesRepository::save)
            .map(customerPackagesMapper::toDto);
    }

    /**
     * Get all the customerPackages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPackagesDTO> findAll() {
        log.debug("Request to get all CustomerPackages");
        return customerPackagesRepository
            .findAll()
            .stream()
            .map(customerPackagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one customerPackages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerPackagesDTO> findOne(Long id) {
        log.debug("Request to get CustomerPackages : {}", id);
        return customerPackagesRepository.findById(id).map(customerPackagesMapper::toDto);
    }

    /**
     * Delete the customerPackages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerPackages : {}", id);
        customerPackagesRepository.deleteById(id);
    }
}
