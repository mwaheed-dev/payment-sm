package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.repository.TariffRepository;
import com.simplerishta.cms.service.dto.TariffDTO;
import com.simplerishta.cms.service.mapper.TariffMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tariff}.
 */
@Service
@Transactional
public class TariffService {

    private final Logger log = LoggerFactory.getLogger(TariffService.class);

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    public TariffService(TariffRepository tariffRepository, TariffMapper tariffMapper) {
        this.tariffRepository = tariffRepository;
        this.tariffMapper = tariffMapper;
    }

    /**
     * Save a tariff.
     *
     * @param tariffDTO the entity to save.
     * @return the persisted entity.
     */
    public TariffDTO save(TariffDTO tariffDTO) {
        log.debug("Request to save Tariff : {}", tariffDTO);
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        return tariffMapper.toDto(tariff);
    }

    /**
     * Update a tariff.
     *
     * @param tariffDTO the entity to save.
     * @return the persisted entity.
     */
    public TariffDTO update(TariffDTO tariffDTO) {
        log.debug("Request to save Tariff : {}", tariffDTO);
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        return tariffMapper.toDto(tariff);
    }

    /**
     * Partially update a tariff.
     *
     * @param tariffDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TariffDTO> partialUpdate(TariffDTO tariffDTO) {
        log.debug("Request to partially update Tariff : {}", tariffDTO);

        return tariffRepository
            .findById(tariffDTO.getId())
            .map(existingTariff -> {
                tariffMapper.partialUpdate(existingTariff, tariffDTO);

                return existingTariff;
            })
            .map(tariffRepository::save)
            .map(tariffMapper::toDto);
    }

    /**
     * Get all the tariffs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TariffDTO> findAll() {
        log.debug("Request to get all Tariffs");
        return tariffRepository.findAll().stream().map(tariffMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the tariffs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TariffDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tariffRepository.findAllWithEagerRelationships(pageable).map(tariffMapper::toDto);
    }

    /**
     * Get one tariff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TariffDTO> findOne(Long id) {
        log.debug("Request to get Tariff : {}", id);
        return tariffRepository.findOneWithEagerRelationships(id).map(tariffMapper::toDto);
    }

    /**
     * Delete the tariff by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tariff : {}", id);
        tariffRepository.deleteById(id);
    }
}
