package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.PackageQuotas;
import com.simplerishta.cms.repository.PackageQuotasRepository;
import com.simplerishta.cms.service.dto.PackageQuotasDTO;
import com.simplerishta.cms.service.mapper.PackageQuotasMapper;
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
 * Integration tests for the {@link PackageQuotasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PackageQuotasResourceIT {

    private static final Integer DEFAULT_NUMBER_OF_PROFILE_VIEWS = 1;
    private static final Integer UPDATED_NUMBER_OF_PROFILE_VIEWS = 2;

    private static final Integer DEFAULT_NUMBER_OF_CONVERSATIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_CONVERSATIONS = 2;

    private static final Integer DEFAULT_NUMBER_OF_REQUEST_SENT = 1;
    private static final Integer UPDATED_NUMBER_OF_REQUEST_SENT = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/package-quotas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackageQuotasRepository packageQuotasRepository;

    @Autowired
    private PackageQuotasMapper packageQuotasMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackageQuotasMockMvc;

    private PackageQuotas packageQuotas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageQuotas createEntity(EntityManager em) {
        PackageQuotas packageQuotas = new PackageQuotas()
            .numberOfProfileViews(DEFAULT_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(DEFAULT_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(DEFAULT_NUMBER_OF_REQUEST_SENT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return packageQuotas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageQuotas createUpdatedEntity(EntityManager em) {
        PackageQuotas packageQuotas = new PackageQuotas()
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return packageQuotas;
    }

    @BeforeEach
    public void initTest() {
        packageQuotas = createEntity(em);
    }

    @Test
    @Transactional
    void createPackageQuotas() throws Exception {
        int databaseSizeBeforeCreate = packageQuotasRepository.findAll().size();
        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);
        restPackageQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeCreate + 1);
        PackageQuotas testPackageQuotas = packageQuotasList.get(packageQuotasList.size() - 1);
        assertThat(testPackageQuotas.getNumberOfProfileViews()).isEqualTo(DEFAULT_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testPackageQuotas.getNumberOfConversations()).isEqualTo(DEFAULT_NUMBER_OF_CONVERSATIONS);
        assertThat(testPackageQuotas.getNumberOfRequestSent()).isEqualTo(DEFAULT_NUMBER_OF_REQUEST_SENT);
        assertThat(testPackageQuotas.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPackageQuotas.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createPackageQuotasWithExistingId() throws Exception {
        // Create the PackageQuotas with an existing ID
        packageQuotas.setId(1L);
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        int databaseSizeBeforeCreate = packageQuotasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageQuotasRepository.findAll().size();
        // set the field null
        packageQuotas.setCreatedAt(null);

        // Create the PackageQuotas, which fails.
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        restPackageQuotasMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPackageQuotas() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        // Get all the packageQuotasList
        restPackageQuotasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageQuotas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfProfileViews").value(hasItem(DEFAULT_NUMBER_OF_PROFILE_VIEWS)))
            .andExpect(jsonPath("$.[*].numberOfConversations").value(hasItem(DEFAULT_NUMBER_OF_CONVERSATIONS)))
            .andExpect(jsonPath("$.[*].numberOfRequestSent").value(hasItem(DEFAULT_NUMBER_OF_REQUEST_SENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getPackageQuotas() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        // Get the packageQuotas
        restPackageQuotasMockMvc
            .perform(get(ENTITY_API_URL_ID, packageQuotas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packageQuotas.getId().intValue()))
            .andExpect(jsonPath("$.numberOfProfileViews").value(DEFAULT_NUMBER_OF_PROFILE_VIEWS))
            .andExpect(jsonPath("$.numberOfConversations").value(DEFAULT_NUMBER_OF_CONVERSATIONS))
            .andExpect(jsonPath("$.numberOfRequestSent").value(DEFAULT_NUMBER_OF_REQUEST_SENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPackageQuotas() throws Exception {
        // Get the packageQuotas
        restPackageQuotasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPackageQuotas() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();

        // Update the packageQuotas
        PackageQuotas updatedPackageQuotas = packageQuotasRepository.findById(packageQuotas.getId()).get();
        // Disconnect from session so that the updates on updatedPackageQuotas are not directly saved in db
        em.detach(updatedPackageQuotas);
        updatedPackageQuotas
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(updatedPackageQuotas);

        restPackageQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageQuotasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isOk());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
        PackageQuotas testPackageQuotas = packageQuotasList.get(packageQuotasList.size() - 1);
        assertThat(testPackageQuotas.getNumberOfProfileViews()).isEqualTo(UPDATED_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testPackageQuotas.getNumberOfConversations()).isEqualTo(UPDATED_NUMBER_OF_CONVERSATIONS);
        assertThat(testPackageQuotas.getNumberOfRequestSent()).isEqualTo(UPDATED_NUMBER_OF_REQUEST_SENT);
        assertThat(testPackageQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPackageQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageQuotasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackageQuotasWithPatch() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();

        // Update the packageQuotas using partial update
        PackageQuotas partialUpdatedPackageQuotas = new PackageQuotas();
        partialUpdatedPackageQuotas.setId(packageQuotas.getId());

        partialUpdatedPackageQuotas
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPackageQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageQuotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageQuotas))
            )
            .andExpect(status().isOk());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
        PackageQuotas testPackageQuotas = packageQuotasList.get(packageQuotasList.size() - 1);
        assertThat(testPackageQuotas.getNumberOfProfileViews()).isEqualTo(DEFAULT_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testPackageQuotas.getNumberOfConversations()).isEqualTo(UPDATED_NUMBER_OF_CONVERSATIONS);
        assertThat(testPackageQuotas.getNumberOfRequestSent()).isEqualTo(UPDATED_NUMBER_OF_REQUEST_SENT);
        assertThat(testPackageQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPackageQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePackageQuotasWithPatch() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();

        // Update the packageQuotas using partial update
        PackageQuotas partialUpdatedPackageQuotas = new PackageQuotas();
        partialUpdatedPackageQuotas.setId(packageQuotas.getId());

        partialUpdatedPackageQuotas
            .numberOfProfileViews(UPDATED_NUMBER_OF_PROFILE_VIEWS)
            .numberOfConversations(UPDATED_NUMBER_OF_CONVERSATIONS)
            .numberOfRequestSent(UPDATED_NUMBER_OF_REQUEST_SENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPackageQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageQuotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageQuotas))
            )
            .andExpect(status().isOk());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
        PackageQuotas testPackageQuotas = packageQuotasList.get(packageQuotasList.size() - 1);
        assertThat(testPackageQuotas.getNumberOfProfileViews()).isEqualTo(UPDATED_NUMBER_OF_PROFILE_VIEWS);
        assertThat(testPackageQuotas.getNumberOfConversations()).isEqualTo(UPDATED_NUMBER_OF_CONVERSATIONS);
        assertThat(testPackageQuotas.getNumberOfRequestSent()).isEqualTo(UPDATED_NUMBER_OF_REQUEST_SENT);
        assertThat(testPackageQuotas.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPackageQuotas.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, packageQuotasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPackageQuotas() throws Exception {
        int databaseSizeBeforeUpdate = packageQuotasRepository.findAll().size();
        packageQuotas.setId(count.incrementAndGet());

        // Create the PackageQuotas
        PackageQuotasDTO packageQuotasDTO = packageQuotasMapper.toDto(packageQuotas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageQuotasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageQuotasDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageQuotas in the database
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePackageQuotas() throws Exception {
        // Initialize the database
        packageQuotasRepository.saveAndFlush(packageQuotas);

        int databaseSizeBeforeDelete = packageQuotasRepository.findAll().size();

        // Delete the packageQuotas
        restPackageQuotasMockMvc
            .perform(delete(ENTITY_API_URL_ID, packageQuotas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackageQuotas> packageQuotasList = packageQuotasRepository.findAll();
        assertThat(packageQuotasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
