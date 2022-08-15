package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.PaymentFrequency;
import com.simplerishta.cms.repository.PaymentFrequencyRepository;
import com.simplerishta.cms.service.dto.PaymentFrequencyDTO;
import com.simplerishta.cms.service.mapper.PaymentFrequencyMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentFrequencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentFrequencyResourceIT {

    private static final String DEFAULT_PAYMENT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payment-frequencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentFrequencyRepository paymentFrequencyRepository;

    @Autowired
    private PaymentFrequencyMapper paymentFrequencyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentFrequencyMockMvc;

    private PaymentFrequency paymentFrequency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFrequency createEntity(EntityManager em) {
        PaymentFrequency paymentFrequency = new PaymentFrequency()
            .paymentFrequency(DEFAULT_PAYMENT_FREQUENCY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return paymentFrequency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentFrequency createUpdatedEntity(EntityManager em) {
        PaymentFrequency paymentFrequency = new PaymentFrequency()
            .paymentFrequency(UPDATED_PAYMENT_FREQUENCY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return paymentFrequency;
    }

    @BeforeEach
    public void initTest() {
        paymentFrequency = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentFrequency() throws Exception {
        int databaseSizeBeforeCreate = paymentFrequencyRepository.findAll().size();
        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);
        restPaymentFrequencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentFrequency testPaymentFrequency = paymentFrequencyList.get(paymentFrequencyList.size() - 1);
        assertThat(testPaymentFrequency.getPaymentFrequency()).isEqualTo(DEFAULT_PAYMENT_FREQUENCY);
        assertThat(testPaymentFrequency.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentFrequency.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentFrequency.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createPaymentFrequencyWithExistingId() throws Exception {
        // Create the PaymentFrequency with an existing ID
        paymentFrequency.setId(1L);
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        int databaseSizeBeforeCreate = paymentFrequencyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentFrequencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPaymentFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFrequencyRepository.findAll().size();
        // set the field null
        paymentFrequency.setPaymentFrequency(null);

        // Create the PaymentFrequency, which fails.
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        restPaymentFrequencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFrequencyRepository.findAll().size();
        // set the field null
        paymentFrequency.setCreatedBy(null);

        // Create the PaymentFrequency, which fails.
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        restPaymentFrequencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentFrequencyRepository.findAll().size();
        // set the field null
        paymentFrequency.setCreatedAt(null);

        // Create the PaymentFrequency, which fails.
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        restPaymentFrequencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentFrequencies() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        // Get all the paymentFrequencyList
        restPaymentFrequencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentFrequency.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentFrequency").value(hasItem(DEFAULT_PAYMENT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getPaymentFrequency() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        // Get the paymentFrequency
        restPaymentFrequencyMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentFrequency.getId().intValue()))
            .andExpect(jsonPath("$.paymentFrequency").value(DEFAULT_PAYMENT_FREQUENCY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentFrequency() throws Exception {
        // Get the paymentFrequency
        restPaymentFrequencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentFrequency() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();

        // Update the paymentFrequency
        PaymentFrequency updatedPaymentFrequency = paymentFrequencyRepository.findById(paymentFrequency.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentFrequency are not directly saved in db
        em.detach(updatedPaymentFrequency);
        updatedPaymentFrequency
            .paymentFrequency(UPDATED_PAYMENT_FREQUENCY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(updatedPaymentFrequency);

        restPaymentFrequencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFrequencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
        PaymentFrequency testPaymentFrequency = paymentFrequencyList.get(paymentFrequencyList.size() - 1);
        assertThat(testPaymentFrequency.getPaymentFrequency()).isEqualTo(UPDATED_PAYMENT_FREQUENCY);
        assertThat(testPaymentFrequency.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentFrequency.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentFrequency.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentFrequencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentFrequencyWithPatch() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();

        // Update the paymentFrequency using partial update
        PaymentFrequency partialUpdatedPaymentFrequency = new PaymentFrequency();
        partialUpdatedPaymentFrequency.setId(paymentFrequency.getId());

        partialUpdatedPaymentFrequency.paymentFrequency(UPDATED_PAYMENT_FREQUENCY);

        restPaymentFrequencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFrequency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFrequency))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
        PaymentFrequency testPaymentFrequency = paymentFrequencyList.get(paymentFrequencyList.size() - 1);
        assertThat(testPaymentFrequency.getPaymentFrequency()).isEqualTo(UPDATED_PAYMENT_FREQUENCY);
        assertThat(testPaymentFrequency.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentFrequency.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentFrequency.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentFrequencyWithPatch() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();

        // Update the paymentFrequency using partial update
        PaymentFrequency partialUpdatedPaymentFrequency = new PaymentFrequency();
        partialUpdatedPaymentFrequency.setId(paymentFrequency.getId());

        partialUpdatedPaymentFrequency
            .paymentFrequency(UPDATED_PAYMENT_FREQUENCY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPaymentFrequencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentFrequency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentFrequency))
            )
            .andExpect(status().isOk());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
        PaymentFrequency testPaymentFrequency = paymentFrequencyList.get(paymentFrequencyList.size() - 1);
        assertThat(testPaymentFrequency.getPaymentFrequency()).isEqualTo(UPDATED_PAYMENT_FREQUENCY);
        assertThat(testPaymentFrequency.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentFrequency.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentFrequency.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentFrequencyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentFrequency() throws Exception {
        int databaseSizeBeforeUpdate = paymentFrequencyRepository.findAll().size();
        paymentFrequency.setId(count.incrementAndGet());

        // Create the PaymentFrequency
        PaymentFrequencyDTO paymentFrequencyDTO = paymentFrequencyMapper.toDto(paymentFrequency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentFrequencyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentFrequencyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentFrequency in the database
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentFrequency() throws Exception {
        // Initialize the database
        paymentFrequencyRepository.saveAndFlush(paymentFrequency);

        int databaseSizeBeforeDelete = paymentFrequencyRepository.findAll().size();

        // Delete the paymentFrequency
        restPaymentFrequencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentFrequency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentFrequency> paymentFrequencyList = paymentFrequencyRepository.findAll();
        assertThat(paymentFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
