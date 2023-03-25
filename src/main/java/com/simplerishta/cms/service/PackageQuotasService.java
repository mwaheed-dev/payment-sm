package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.PackageQuotas;
import com.simplerishta.cms.repository.PackageQuotasRepository;
import com.simplerishta.cms.service.dto.PackageQuotasDTO;
import com.simplerishta.cms.service.mapper.PackageQuotasMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PackageQuotas}.
 */
@Service
@Transactional
public class PackageQuotasService {

    private final Logger log = LoggerFactory.getLogger(PackageQuotasService.class);

    private final PackageQuotasRepository packageQuotasRepository;

    private final PackageQuotasMapper packageQuotasMapper;

    public PackageQuotasService(PackageQuotasRepository packageQuotasRepository, PackageQuotasMapper packageQuotasMapper) {
        this.packageQuotasRepository = packageQuotasRepository;
        this.packageQuotasMapper = packageQuotasMapper;
    }

    /**
     * Save a packageQuotas.
     *
     * @param packageQuotasDTO the entity to save.
     * @return the persisted entity.
     */
    public PackageQuotasDTO save(PackageQuotasDTO packageQuotasDTO) {
        log.debug("Request to save PackageQuotas : {}", packageQuotasDTO);
        PackageQuotas packageQuotas = packageQuotasMapper.toEntity(packageQuotasDTO);
        packageQuotas = packageQuotasRepository.save(packageQuotas);
        return packageQuotasMapper.toDto(packageQuotas);
    }

    /**
     * Update a packageQuotas.
     *
     * @param packageQuotasDTO the entity to save.
     * @return the persisted entity.
     */
    public PackageQuotasDTO update(PackageQuotasDTO packageQuotasDTO) {
        log.debug("Request to update PackageQuotas : {}", packageQuotasDTO);
        PackageQuotas packageQuotas = packageQuotasMapper.toEntity(packageQuotasDTO);
        packageQuotas = packageQuotasRepository.save(packageQuotas);
        return packageQuotasMapper.toDto(packageQuotas);
    }

    /**
     * Partially update a packageQuotas.
     *
     * @param packageQuotasDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PackageQuotasDTO> partialUpdate(PackageQuotasDTO packageQuotasDTO) {
        log.debug("Request to partially update PackageQuotas : {}", packageQuotasDTO);

        return packageQuotasRepository
            .findById(packageQuotasDTO.getId())
            .map(existingPackageQuotas -> {
                packageQuotasMapper.partialUpdate(existingPackageQuotas, packageQuotasDTO);

                return existingPackageQuotas;
            })
            .map(packageQuotasRepository::save)
            .map(packageQuotasMapper::toDto);
    }

    /**
     * Get all the packageQuotas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PackageQuotasDTO> findAll() {
        log.debug("Request to get all PackageQuotas");
        return packageQuotasRepository.findAll().stream().map(packageQuotasMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one packageQuotas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackageQuotasDTO> findOne(Long id) {
        log.debug("Request to get PackageQuotas : {}", id);
        return packageQuotasRepository.findById(id).map(packageQuotasMapper::toDto);
    }

    /**
     * Delete the packageQuotas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PackageQuotas : {}", id);
        packageQuotasRepository.deleteById(id);
    }
}
