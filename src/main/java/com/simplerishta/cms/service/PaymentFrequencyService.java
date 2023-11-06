package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.PaymentFrequency;
import com.simplerishta.cms.repository.PaymentFrequencyRepository;
import com.simplerishta.cms.service.dto.PaymentFrequencyDTO;
import com.simplerishta.cms.service.mapper.PaymentFrequencyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentFrequency}.
 */
@Service
@Transactional
public class PaymentFrequencyService {

    private final Logger log = LoggerFactory.getLogger(PaymentFrequencyService.class);

    private final PaymentFrequencyRepository paymentFrequencyRepository;

    private final PaymentFrequencyMapper paymentFrequencyMapper;

    public PaymentFrequencyService(PaymentFrequencyRepository paymentFrequencyRepository, PaymentFrequencyMapper paymentFrequencyMapper) {
        this.paymentFrequencyRepository = paymentFrequencyRepository;
        this.paymentFrequencyMapper = paymentFrequencyMapper;
    }

    /**
     * Save a paymentFrequency.
     *
     * @param paymentFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentFrequencyDTO save(PaymentFrequencyDTO paymentFrequencyDTO) {
        log.debug("Request to save PaymentFrequency : {}", paymentFrequencyDTO);
        PaymentFrequency paymentFrequency = paymentFrequencyMapper.toEntity(paymentFrequencyDTO);
        paymentFrequency = paymentFrequencyRepository.save(paymentFrequency);
        return paymentFrequencyMapper.toDto(paymentFrequency);
    }

    /**
     * Update a paymentFrequency.
     *
     * @param paymentFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentFrequencyDTO update(PaymentFrequencyDTO paymentFrequencyDTO) {
        log.debug("Request to update PaymentFrequency : {}", paymentFrequencyDTO);
        PaymentFrequency paymentFrequency = paymentFrequencyMapper.toEntity(paymentFrequencyDTO);
        paymentFrequency = paymentFrequencyRepository.save(paymentFrequency);
        return paymentFrequencyMapper.toDto(paymentFrequency);
    }

    /**
     * Partially update a paymentFrequency.
     *
     * @param paymentFrequencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentFrequencyDTO> partialUpdate(PaymentFrequencyDTO paymentFrequencyDTO) {
        log.debug("Request to partially update PaymentFrequency : {}", paymentFrequencyDTO);

        return paymentFrequencyRepository
            .findById(paymentFrequencyDTO.getId())
            .map(existingPaymentFrequency -> {
                paymentFrequencyMapper.partialUpdate(existingPaymentFrequency, paymentFrequencyDTO);

                return existingPaymentFrequency;
            })
            .map(paymentFrequencyRepository::save)
            .map(paymentFrequencyMapper::toDto);
    }

    /**
     * Get all the paymentFrequencies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentFrequencyDTO> findAll() {
        log.debug("Request to get all PaymentFrequencies");
        return paymentFrequencyRepository
            .findAll()
            .stream()
            .map(paymentFrequencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paymentFrequency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentFrequencyDTO> findOne(Long id) {
        log.debug("Request to get PaymentFrequency : {}", id);
        return paymentFrequencyRepository.findById(id).map(paymentFrequencyMapper::toDto);
    }

    /**
     * Delete the paymentFrequency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentFrequency : {}", id);
        paymentFrequencyRepository.deleteById(id);
    }
}
