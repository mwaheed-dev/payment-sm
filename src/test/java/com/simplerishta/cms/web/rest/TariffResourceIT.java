package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.Tariff;
import com.simplerishta.cms.repository.TariffRepository;
import com.simplerishta.cms.service.TariffService;
import com.simplerishta.cms.service.dto.TariffDTO;
import com.simplerishta.cms.service.mapper.TariffMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TariffResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TariffResourceIT {

    private static final String DEFAULT_TARIFF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TARIFF_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tariffs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TariffRepository tariffRepository;

    @Mock
    private TariffRepository tariffRepositoryMock;

    @Autowired
    private TariffMapper tariffMapper;

    @Mock
    private TariffService tariffServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTariffMockMvc;

    private Tariff tariff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tariff createEntity(EntityManager em) {
        Tariff tariff = new Tariff()
            .tariffCode(DEFAULT_TARIFF_CODE)
            .price(DEFAULT_PRICE)
            .duration(DEFAULT_DURATION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return tariff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tariff createUpdatedEntity(EntityManager em) {
        Tariff tariff = new Tariff()
            .tariffCode(UPDATED_TARIFF_CODE)
            .price(UPDATED_PRICE)
            .duration(UPDATED_DURATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return tariff;
    }

    @BeforeEach
    public void initTest() {
        tariff = createEntity(em);
    }

    @Test
    @Transactional
    void createTariff() throws Exception {
        int databaseSizeBeforeCreate = tariffRepository.findAll().size();
        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);
        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isCreated());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate + 1);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getTariffCode()).isEqualTo(DEFAULT_TARIFF_CODE);
        assertThat(testTariff.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTariff.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTariff.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTariff.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTariffWithExistingId() throws Exception {
        // Create the Tariff with an existing ID
        tariff.setId(1L);
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        int databaseSizeBeforeCreate = tariffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTariffCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffRepository.findAll().size();
        // set the field null
        tariff.setTariffCode(null);

        // Create the Tariff, which fails.
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isBadRequest());

        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffRepository.findAll().size();
        // set the field null
        tariff.setPrice(null);

        // Create the Tariff, which fails.
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isBadRequest());

        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffRepository.findAll().size();
        // set the field null
        tariff.setCreatedAt(null);

        // Create the Tariff, which fails.
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isBadRequest());

        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTariffs() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get all the tariffList
        restTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].tariffCode").value(hasItem(DEFAULT_TARIFF_CODE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTariffsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tariffServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTariffMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tariffServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTariffsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tariffServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTariffMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tariffRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get the tariff
        restTariffMockMvc
            .perform(get(ENTITY_API_URL_ID, tariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tariff.getId().intValue()))
            .andExpect(jsonPath("$.tariffCode").value(DEFAULT_TARIFF_CODE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTariff() throws Exception {
        // Get the tariff
        restTariffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff
        Tariff updatedTariff = tariffRepository.findById(tariff.getId()).get();
        // Disconnect from session so that the updates on updatedTariff are not directly saved in db
        em.detach(updatedTariff);
        updatedTariff
            .tariffCode(UPDATED_TARIFF_CODE)
            .price(UPDATED_PRICE)
            .duration(UPDATED_DURATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TariffDTO tariffDTO = tariffMapper.toDto(updatedTariff);

        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getTariffCode()).isEqualTo(UPDATED_TARIFF_CODE);
        assertThat(testTariff.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTariff.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTariff.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTariff.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTariffWithPatch() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff using partial update
        Tariff partialUpdatedTariff = new Tariff();
        partialUpdatedTariff.setId(tariff.getId());

        partialUpdatedTariff.duration(UPDATED_DURATION).createdAt(UPDATED_CREATED_AT);

        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTariff))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getTariffCode()).isEqualTo(DEFAULT_TARIFF_CODE);
        assertThat(testTariff.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTariff.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTariff.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTariff.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTariffWithPatch() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff using partial update
        Tariff partialUpdatedTariff = new Tariff();
        partialUpdatedTariff.setId(tariff.getId());

        partialUpdatedTariff
            .tariffCode(UPDATED_TARIFF_CODE)
            .price(UPDATED_PRICE)
            .duration(UPDATED_DURATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTariff))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getTariffCode()).isEqualTo(UPDATED_TARIFF_CODE);
        assertThat(testTariff.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTariff.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTariff.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTariff.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tariffDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tariffDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeDelete = tariffRepository.findAll().size();

        // Delete the tariff
        restTariffMockMvc
            .perform(delete(ENTITY_API_URL_ID, tariff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
