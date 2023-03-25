package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.repository.UserTariffRepository;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import com.simplerishta.cms.service.mapper.UserTariffMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserTariff}.
 */
@Service
@Transactional
public class UserTariffService {

    private final Logger log = LoggerFactory.getLogger(UserTariffService.class);

    private final UserTariffRepository userTariffRepository;

    private final UserTariffMapper userTariffMapper;

    public UserTariffService(UserTariffRepository userTariffRepository, UserTariffMapper userTariffMapper) {
        this.userTariffRepository = userTariffRepository;
        this.userTariffMapper = userTariffMapper;
    }

    /**
     * Save a userTariff.
     *
     * @param userTariffDTO the entity to save.
     * @return the persisted entity.
     */
    public UserTariffDTO save(UserTariffDTO userTariffDTO) {
        log.debug("Request to save UserTariff : {}", userTariffDTO);
        UserTariff userTariff = userTariffMapper.toEntity(userTariffDTO);
        userTariff = userTariffRepository.save(userTariff);
        return userTariffMapper.toDto(userTariff);
    }

    /**
     * Update a userTariff.
     *
     * @param userTariffDTO the entity to save.
     * @return the persisted entity.
     */
    public UserTariffDTO update(UserTariffDTO userTariffDTO) {
        log.debug("Request to update UserTariff : {}", userTariffDTO);
        UserTariff userTariff = userTariffMapper.toEntity(userTariffDTO);
        // no save call needed as we have no fields that can be updated
        return userTariffMapper.toDto(userTariff);
    }

    /**
     * Partially update a userTariff.
     *
     * @param userTariffDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserTariffDTO> partialUpdate(UserTariffDTO userTariffDTO) {
        log.debug("Request to partially update UserTariff : {}", userTariffDTO);

        return userTariffRepository
            .findById(userTariffDTO.getId())
            .map(existingUserTariff -> {
                userTariffMapper.partialUpdate(existingUserTariff, userTariffDTO);

                return existingUserTariff;
            })
            // .map(userTariffRepository::save)
            .map(userTariffMapper::toDto);
    }

    /**
     * Get all the userTariffs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserTariffDTO> findAll() {
        log.debug("Request to get all UserTariffs");
        return userTariffRepository.findAll().stream().map(userTariffMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userTariff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserTariffDTO> findOne(Long id) {
        log.debug("Request to get UserTariff : {}", id);
        return userTariffRepository.findById(id).map(userTariffMapper::toDto);
    }

    /**
     * Delete the userTariff by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserTariff : {}", id);
        userTariffRepository.deleteById(id);
    }
}
