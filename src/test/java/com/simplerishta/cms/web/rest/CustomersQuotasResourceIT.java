package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.CustomersQuotas;
import com.simplerishta.cms.repository.CustomersQuotasRepository;
import com.simplerishta.cms.service.dto.CustomersQuotasDTO;
import com.simplerishta.cms.service.mapper.CustomersQuotasMapper;
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
 * Integration tests for the {@link CustomersQuotasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomersQuotasResourceIT {

    private static final Integer DEFAULT_NUMBER_OF_PROFILE_VIEWS = 1;
    private static final Integer UPDATED_NUMBER_OF_PROFILE_VIEWS = 2;

    private static final Integer DEFAULT_NUMBER_OF_CONVERSATIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_CONVERSATIONS = 2;

    private static final Integer DEFAULT_NUMBER_OF_REQUEST_SENT = 1;
    private static final Integer UPDATED_NUMBER_OF_REQUEST_SENT = 2;

    private static final Integer DEFAULT_FREE_USERS = 1;
    private static final Integer UPDATED_FREE_USERS = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/customers-quotas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomersQuotasRepository customersQuotasRepository;

    @Autowired
    private CustomersQuotasMapper customersQuotasMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersQuotasMockMvc;

    private CustomersQuotas customersQuotas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersQuotas createEntity(EntityManager em) {
        CustomersQuotas customersQuotas = new CustomersQuotas()
            .numberOfProfileViews(DEFAULT_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(DEFAULT_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(DEFAULT_NUMBER_OF_REQUEST_SENT)
            .freeUsers(DEFAULT_FREE_USERS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return customersQuotas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersQuotas createUpdatedEntity(EntityManager em) {
        CustomersQuotas customersQuotas = new CustomersQuotas()
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .freeUsers(UPDATED_FREE_USERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return customersQuotas;
    }

    @BeforeEach
    public void initTest() {
        customersQuotas = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomersQuotas() throws Exception {
        int databaseSizeBeforeCreate = customersQuotasRepository.findAll().size();
        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);
        restCustomersQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeCreate + 1);
        CustomersQuotas testCustomersQuotas = customersQuotasList.get(customersQuotasList.size() - 1);
        assertThat(testCustomersQuotas.getNumberOfProfileViews()).isEqualTo(DEFAULT_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testCustomersQuotas.getNumberOfConversations()).isEqualTo(DEFAULT_NUMBER_OF_CONVERSATIONS);
        assertThat(testCustomersQuotas.getNumberOfRequestSent()).isEqualTo(DEFAULT_NUMBER_OF_REQUEST_SENT);
        assertThat(testCustomersQuotas.getFreeUsers()).isEqualTo(DEFAULT_FREE_USERS);
        assertThat(testCustomersQuotas.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCustomersQuotas.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCustomersQuotasWithExistingId() throws Exception {
        // Create the CustomersQuotas with an existing ID
        customersQuotas.setId(1L);
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        int databaseSizeBeforeCreate = customersQuotasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersQuotasRepository.findAll().size();
        // set the field null
        customersQuotas.setCreatedAt(null);

        // Create the CustomersQuotas, which fails.
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        restCustomersQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomersQuotas() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        // Get all the customersQuotasList
        restCustomersQuotasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customersQuotas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfProfileViews").value(hasItem(DEFAULT_NUMBER_OF_PROFILE_VIEWS)))
            .andExpect(jsonPath("$.[*].numberOfConversations").value(hasItem(DEFAULT_NUMBER_OF_CONVERSATIONS)))
            .andExpect(jsonPath("$.[*].numberOfRequestSent").value(hasItem(DEFAULT_NUMBER_OF_REQUEST_SENT)))
            .andExpect(jsonPath("$.[*].freeUsers").value(hasItem(DEFAULT_FREE_USERS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCustomersQuotas() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        // Get the customersQuotas
        restCustomersQuotasMockMvc
            .perform(get(ENTITY_API_URL_ID, customersQuotas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customersQuotas.getId().intValue()))
            .andExpect(jsonPath("$.numberOfProfileViews").value(DEFAULT_NUMBER_OF_PROFILE_VIEWS))
            .andExpect(jsonPath("$.numberOfConversations").value(DEFAULT_NUMBER_OF_CONVERSATIONS))
            .andExpect(jsonPath("$.numberOfRequestSent").value(DEFAULT_NUMBER_OF_REQUEST_SENT))
            .andExpect(jsonPath("$.freeUsers").value(DEFAULT_FREE_USERS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomersQuotas() throws Exception {
        // Get the customersQuotas
        restCustomersQuotasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomersQuotas() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();

        // Update the customersQuotas
        CustomersQuotas updatedCustomersQuotas = customersQuotasRepository.findById(customersQuotas.getId()).get();
        // Disconnect from session so that the updates on updatedCustomersQuotas are not directly saved in db
        em.detach(updatedCustomersQuotas);
        updatedCustomersQuotas
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .freeUsers(UPDATED_FREE_USERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(updatedCustomersQuotas);

        restCustomersQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersQuotasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
        CustomersQuotas testCustomersQuotas = customersQuotasList.get(customersQuotasList.size() - 1);
        assertThat(testCustomersQuotas.getNumberOfProfileViews()).isEqualTo(UPDATED_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testCustomersQuotas.getNumberOfConversations()).isEqualTo(UPDATED_NUMBER_OF_CONVERSATIONS);
        assertThat(testCustomersQuotas.getNumberOfRequestSent()).isEqualTo(UPDATED_NUMBER_OF_REQUEST_SENT);
        assertThat(testCustomersQuotas.getFreeUsers()).isEqualTo(UPDATED_FREE_USERS);
        assertThat(testCustomersQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomersQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersQuotasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomersQuotasWithPatch() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();

        // Update the customersQuotas using partial update
        CustomersQuotas partialUpdatedCustomersQuotas = new CustomersQuotas();
        partialUpdatedCustomersQuotas.setId(customersQuotas.getId());

        partialUpdatedCustomersQuotas.freeUsers(UPDATED_FREE_USERS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restCustomersQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersQuotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersQuotas))
            )
            .andExpect(status().isOk());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
        CustomersQuotas testCustomersQuotas = customersQuotasList.get(customersQuotasList.size() - 1);
        assertThat(testCustomersQuotas.getNumberOfProfileViews()).isEqualTo(DEFAULT_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testCustomersQuotas.getNumberOfConversations()).isEqualTo(DEFAULT_NUMBER_OF_CONVERSATIONS);
        assertThat(testCustomersQuotas.getNumberOfRequestSent()).isEqualTo(DEFAULT_NUMBER_OF_REQUEST_SENT);
        assertThat(testCustomersQuotas.getFreeUsers()).isEqualTo(UPDATED_FREE_USERS);
        assertThat(testCustomersQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomersQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCustomersQuotasWithPatch() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();

        // Update the customersQuotas using partial update
        CustomersQuotas partialUpdatedCustomersQuotas = new CustomersQuotas();
        partialUpdatedCustomersQuotas.setId(customersQuotas.getId());

        partialUpdatedCustomersQuotas
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .freeUsers(UPDATED_FREE_USERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCustomersQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersQuotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersQuotas))
            )
            .andExpect(status().isOk());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
        CustomersQuotas testCustomersQuotas = customersQuotasList.get(customersQuotasList.size() - 1);
        assertThat(testCustomersQuotas.getNumberOfProfileViews()).isEqualTo(UPDATED_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testCustomersQuotas.getNumberOfConversations()).isEqualTo(UPDATED_NUMBER_OF_CONVERSATIONS);
        assertThat(testCustomersQuotas.getNumberOfRequestSent()).isEqualTo(UPDATED_NUMBER_OF_REQUEST_SENT);
        assertThat(testCustomersQuotas.getFreeUsers()).isEqualTo(UPDATED_FREE_USERS);
        assertThat(testCustomersQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCustomersQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customersQuotasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomersQuotas() throws Exception {
        int databaseSizeBeforeUpdate = customersQuotasRepository.findAll().size();
        customersQuotas.setId(count.incrementAndGet());

        // Create the CustomersQuotas
        CustomersQuotasDTO customersQuotasDTO = customersQuotasMapper.toDto(customersQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersQuotasDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersQuotas in the database
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomersQuotas() throws Exception {
        // Initialize the database
        customersQuotasRepository.saveAndFlush(customersQuotas);

        int databaseSizeBeforeDelete = customersQuotasRepository.findAll().size();

        // Delete the customersQuotas
        restCustomersQuotasMockMvc
            .perform(delete(ENTITY_API_URL_ID, customersQuotas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomersQuotas> customersQuotasList = customersQuotasRepository.findAll();
        assertThat(customersQuotasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
