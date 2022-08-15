package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.CustomerPackages;
import com.simplerishta.cms.repository.CustomerPackagesRepository;
import com.simplerishta.cms.service.dto.CustomerPackagesDTO;
import com.simplerishta.cms.service.mapper.CustomerPackagesMapper;
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
 * Integration tests for the {@link CustomerPackagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerPackagesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/customer-packages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerPackagesRepository customerPackagesRepository;

    @Autowired
    private CustomerPackagesMapper customerPackagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPackagesMockMvc;

    private CustomerPackages customerPackages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPackages createEntity(EntityManager em) {
        CustomerPackages customerPackages = new CustomerPackages()
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return customerPackages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPackages createUpdatedEntity(EntityManager em) {
        CustomerPackages customerPackages = new CustomerPackages()
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return customerPackages;
    }

    @BeforeEach
    public void initTest() {
        customerPackages = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerPackages() throws Exception {
        int databaseSizeBeforeCreate = customerPackagesRepository.findAll().size();
        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);
        restCustomerPackagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPackages testCustomerPackages = customerPackagesList.get(customerPackagesList.size() - 1);
        assertThat(testCustomerPackages.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerPackages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomerPackages.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCustomerPackages.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCustomerPackagesWithExistingId() throws Exception {
        // Create the CustomerPackages with an existing ID
        customerPackages.setId(1L);
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        int databaseSizeBeforeCreate = customerPackagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPackagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPackagesRepository.findAll().size();
        // set the field null
        customerPackages.setName(null);

        // Create the CustomerPackages, which fails.
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        restCustomerPackagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPackagesRepository.findAll().size();
        // set the field null
        customerPackages.setCreatedBy(null);

        // Create the CustomerPackages, which fails.
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        restCustomerPackagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPackagesRepository.findAll().size();
        // set the field null
        customerPackages.setCreatedAt(null);

        // Create the CustomerPackages, which fails.
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        restCustomerPackagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerPackages() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        // Get all the customerPackagesList
        restCustomerPackagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPackages.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCustomerPackages() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        // Get the customerPackages
        restCustomerPackagesMockMvc
            .perform(get(ENTITY_API_URL_ID, customerPackages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPackages.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomerPackages() throws Exception {
        // Get the customerPackages
        restCustomerPackagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerPackages() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();

        // Update the customerPackages
        CustomerPackages updatedCustomerPackages = customerPackagesRepository.findById(customerPackages.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPackages are not directly saved in db
        em.detach(updatedCustomerPackages);
        updatedCustomerPackages
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(updatedCustomerPackages);

        restCustomerPackagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerPackagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
        CustomerPackages testCustomerPackages = customerPackagesList.get(customerPackagesList.size() - 1);
        assertThat(testCustomerPackages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerPackages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerPackages.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomerPackages.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerPackagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerPackagesWithPatch() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();

        // Update the customerPackages using partial update
        CustomerPackages partialUpdatedCustomerPackages = new CustomerPackages();
        partialUpdatedCustomerPackages.setId(customerPackages.getId());

        partialUpdatedCustomerPackages
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomerPackagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerPackages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerPackages))
            )
            .andExpect(status().isOk());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
        CustomerPackages testCustomerPackages = customerPackagesList.get(customerPackagesList.size() - 1);
        assertThat(testCustomerPackages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerPackages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerPackages.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomerPackages.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCustomerPackagesWithPatch() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();

        // Update the customerPackages using partial update
        CustomerPackages partialUpdatedCustomerPackages = new CustomerPackages();
        partialUpdatedCustomerPackages.setId(customerPackages.getId());

        partialUpdatedCustomerPackages
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomerPackagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerPackages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerPackages))
            )
            .andExpect(status().isOk());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
        CustomerPackages testCustomerPackages = customerPackagesList.get(customerPackagesList.size() - 1);
        assertThat(testCustomerPackages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerPackages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerPackages.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomerPackages.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerPackagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerPackages() throws Exception {
        int databaseSizeBeforeUpdate = customerPackagesRepository.findAll().size();
        customerPackages.setId(count.incrementAndGet());

        // Create the CustomerPackages
        CustomerPackagesDTO customerPackagesDTO = customerPackagesMapper.toDto(customerPackages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerPackagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerPackagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerPackages in the database
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerPackages() throws Exception {
        // Initialize the database
        customerPackagesRepository.saveAndFlush(customerPackages);

        int databaseSizeBeforeDelete = customerPackagesRepository.findAll().size();

        // Delete the customerPackages
        restCustomerPackagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerPackages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPackages> customerPackagesList = customerPackagesRepository.findAll();
        assertThat(customerPackagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
