package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.PaymentType;
import com.simplerishta.cms.repository.PaymentTypeRepository;
import com.simplerishta.cms.service.dto.PaymentTypeDTO;
import com.simplerishta.cms.service.mapper.PaymentTypeMapper;
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
 * Integration tests for the {@link PaymentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentTypeResourceIT {

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentTypeMockMvc;

    private PaymentType paymentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
            .typeName(DEFAULT_TYPE_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return paymentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createUpdatedEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
            .typeName(UPDATED_TYPE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return paymentType;
    }

    @BeforeEach
    public void initTest() {
        paymentType = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentType() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();
        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);
        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createPaymentTypeWithExistingId() throws Exception {
        // Create the PaymentType with an existing ID
        paymentType.setId(1L);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTypeRepository.findAll().size();
        // set the field null
        paymentType.setTypeName(null);

        // Create the PaymentType, which fails.
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTypeRepository.findAll().size();
        // set the field null
        paymentType.setCreatedBy(null);

        // Create the PaymentType, which fails.
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTypeRepository.findAll().size();
        // set the field null
        paymentType.setCreatedAt(null);

        // Create the PaymentType, which fails.
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentTypes() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList
        restPaymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get the paymentType
        restPaymentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentType.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentType() throws Exception {
        // Get the paymentType
        restPaymentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType
        PaymentType updatedPaymentType = paymentTypeRepository.findById(paymentType.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentType are not directly saved in db
        em.detach(updatedPaymentType);
        updatedPaymentType
            .typeName(UPDATED_TYPE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(updatedPaymentType);

        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentTypeWithPatch() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType using partial update
        PaymentType partialUpdatedPaymentType = new PaymentType();
        partialUpdatedPaymentType.setId(paymentType.getId());

        partialUpdatedPaymentType.typeName(UPDATED_TYPE_NAME).updatedAt(UPDATED_UPDATED_AT);

        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentType))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaymentType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentTypeWithPatch() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType using partial update
        PaymentType partialUpdatedPaymentType = new PaymentType();
        partialUpdatedPaymentType.setId(paymentType.getId());

        partialUpdatedPaymentType
            .typeName(UPDATED_TYPE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentType))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaymentType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeDelete = paymentTypeRepository.findAll().size();

        // Delete the paymentType
        restPaymentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
