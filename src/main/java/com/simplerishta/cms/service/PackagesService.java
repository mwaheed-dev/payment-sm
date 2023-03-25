package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.Packages;
import com.simplerishta.cms.repository.PackagesRepository;
import com.simplerishta.cms.service.dto.PackagesDTO;
import com.simplerishta.cms.service.mapper.PackagesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Packages}.
 */
@Service
@Transactional
public class PackagesService {

    private final Logger log = LoggerFactory.getLogger(PackagesService.class);

    private final PackagesRepository packagesRepository;

    private final PackagesMapper packagesMapper;

    public PackagesService(PackagesRepository packagesRepository, PackagesMapper packagesMapper) {
        this.packagesRepository = packagesRepository;
        this.packagesMapper = packagesMapper;
    }

    /**
     * Save a packages.
     *
     * @param packagesDTO the entity to save.
     * @return the persisted entity.
     */
    public PackagesDTO save(PackagesDTO packagesDTO) {
        log.debug("Request to save Packages : {}", packagesDTO);
        Packages packages = packagesMapper.toEntity(packagesDTO);
        packages = packagesRepository.save(packages);
        return packagesMapper.toDto(packages);
    }

    /**
     * Update a packages.
     *
     * @param packagesDTO the entity to save.
     * @return the persisted entity.
     */
    public PackagesDTO update(PackagesDTO packagesDTO) {
        log.debug("Request to update Packages : {}", packagesDTO);
        Packages packages = packagesMapper.toEntity(packagesDTO);
        packages = packagesRepository.save(packages);
        return packagesMapper.toDto(packages);
    }

    /**
     * Partially update a packages.
     *
     * @param packagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PackagesDTO> partialUpdate(PackagesDTO packagesDTO) {
        log.debug("Request to partially update Packages : {}", packagesDTO);

        return packagesRepository
            .findById(packagesDTO.getId())
            .map(existingPackages -> {
                packagesMapper.partialUpdate(existingPackages, packagesDTO);

                return existingPackages;
            })
            .map(packagesRepository::save)
            .map(packagesMapper::toDto);
    }

    /**
     * Get all the packages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PackagesDTO> findAll() {
        log.debug("Request to get all Packages");
        return packagesRepository.findAll().stream().map(packagesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one packages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackagesDTO> findOne(Long id) {
        log.debug("Request to get Packages : {}", id);
        return packagesRepository.findById(id).map(packagesMapper::toDto);
    }

    /**
     * Delete the packages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Packages : {}", id);
        packagesRepository.deleteById(id);
    }
}
