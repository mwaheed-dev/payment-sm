package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.JazzCashPayment;
import com.simplerishta.cms.repository.JazzCashPaymentRepository;
import com.simplerishta.cms.service.dto.JazzCashPaymentDTO;
import com.simplerishta.cms.service.mapper.JazzCashPaymentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JazzCashPayment}.
 */
@Service
@Transactional
public class JazzCashPaymentService {

    private final Logger log = LoggerFactory.getLogger(JazzCashPaymentService.class);

    private final JazzCashPaymentRepository jazzCashPaymentRepository;

    private final JazzCashPaymentMapper jazzCashPaymentMapper;

    public JazzCashPaymentService(JazzCashPaymentRepository jazzCashPaymentRepository, JazzCashPaymentMapper jazzCashPaymentMapper) {
        this.jazzCashPaymentRepository = jazzCashPaymentRepository;
        this.jazzCashPaymentMapper = jazzCashPaymentMapper;
    }

    /**
     * Save a jazzCashPayment.
     *
     * @param jazzCashPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    public JazzCashPaymentDTO save(JazzCashPaymentDTO jazzCashPaymentDTO) {
        log.debug("Request to save JazzCashPayment : {}", jazzCashPaymentDTO);
        JazzCashPayment jazzCashPayment = jazzCashPaymentMapper.toEntity(jazzCashPaymentDTO);
        jazzCashPayment = jazzCashPaymentRepository.save(jazzCashPayment);
        return jazzCashPaymentMapper.toDto(jazzCashPayment);
    }

    /**
     * Update a jazzCashPayment.
     *
     * @param jazzCashPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    public JazzCashPaymentDTO update(JazzCashPaymentDTO jazzCashPaymentDTO) {
        log.debug("Request to update JazzCashPayment : {}", jazzCashPaymentDTO);
        JazzCashPayment jazzCashPayment = jazzCashPaymentMapper.toEntity(jazzCashPaymentDTO);
        jazzCashPayment = jazzCashPaymentRepository.save(jazzCashPayment);
        return jazzCashPaymentMapper.toDto(jazzCashPayment);
    }

    /**
     * Partially update a jazzCashPayment.
     *
     * @param jazzCashPaymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JazzCashPaymentDTO> partialUpdate(JazzCashPaymentDTO jazzCashPaymentDTO) {
        log.debug("Request to partially update JazzCashPayment : {}", jazzCashPaymentDTO);

        return jazzCashPaymentRepository
            .findById(jazzCashPaymentDTO.getId())
            .map(existingJazzCashPayment -> {
                jazzCashPaymentMapper.partialUpdate(existingJazzCashPayment, jazzCashPaymentDTO);

                return existingJazzCashPayment;
            })
            .map(jazzCashPaymentRepository::save)
            .map(jazzCashPaymentMapper::toDto);
    }

    /**
     * Get all the jazzCashPayments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JazzCashPaymentDTO> findAll() {
        log.debug("Request to get all JazzCashPayments");
        return jazzCashPaymentRepository
            .findAll()
            .stream()
            .map(jazzCashPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jazzCashPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JazzCashPaymentDTO> findOne(Long id) {
        log.debug("Request to get JazzCashPayment : {}", id);
        return jazzCashPaymentRepository.findById(id).map(jazzCashPaymentMapper::toDto);
    }

    /**
     * Delete the jazzCashPayment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JazzCashPayment : {}", id);
        jazzCashPaymentRepository.deleteById(id);
    }
}
