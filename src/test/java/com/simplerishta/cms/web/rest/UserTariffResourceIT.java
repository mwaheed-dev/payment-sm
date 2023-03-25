package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.UserTariff;
import com.simplerishta.cms.repository.UserTariffRepository;
import com.simplerishta.cms.service.dto.UserTariffDTO;
import com.simplerishta.cms.service.mapper.UserTariffMapper;
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
 * Integration tests for the {@link UserTariffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTariffResourceIT {

    private static final String ENTITY_API_URL = "/api/user-tariffs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTariffRepository userTariffRepository;

    @Autowired
    private UserTariffMapper userTariffMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTariffMockMvc;

    private UserTariff userTariff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTariff createEntity(EntityManager em) {
        UserTariff userTariff = new UserTariff();
        return userTariff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTariff createUpdatedEntity(EntityManager em) {
        UserTariff userTariff = new UserTariff();
        return userTariff;
    }

    @BeforeEach
    public void initTest() {
        userTariff = createEntity(em);
    }

    @Test
    @Transactional
    void createUserTariff() throws Exception {
        int databaseSizeBeforeCreate = userTariffRepository.findAll().size();
        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);
        restUserTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTariffDTO)))
            .andExpect(status().isCreated());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeCreate + 1);
        UserTariff testUserTariff = userTariffList.get(userTariffList.size() - 1);
    }

    @Test
    @Transactional
    void createUserTariffWithExistingId() throws Exception {
        // Create the UserTariff with an existing ID
        userTariff.setId(1L);
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        int databaseSizeBeforeCreate = userTariffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTariffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserTariffs() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        // Get all the userTariffList
        restUserTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userTariff.getId().intValue())));
    }

    @Test
    @Transactional
    void getUserTariff() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        // Get the userTariff
        restUserTariffMockMvc
            .perform(get(ENTITY_API_URL_ID, userTariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userTariff.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserTariff() throws Exception {
        // Get the userTariff
        restUserTariffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserTariff() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();

        // Update the userTariff
        UserTariff updatedUserTariff = userTariffRepository.findById(userTariff.getId()).get();
        // Disconnect from session so that the updates on updatedUserTariff are not directly saved in db
        em.detach(updatedUserTariff);
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(updatedUserTariff);

        restUserTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
        UserTariff testUserTariff = userTariffList.get(userTariffList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTariffDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTariffWithPatch() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();

        // Update the userTariff using partial update
        UserTariff partialUpdatedUserTariff = new UserTariff();
        partialUpdatedUserTariff.setId(userTariff.getId());

        restUserTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTariff))
            )
            .andExpect(status().isOk());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
        UserTariff testUserTariff = userTariffList.get(userTariffList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateUserTariffWithPatch() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();

        // Update the userTariff using partial update
        UserTariff partialUpdatedUserTariff = new UserTariff();
        partialUpdatedUserTariff.setId(userTariff.getId());

        restUserTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTariff))
            )
            .andExpect(status().isOk());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
        UserTariff testUserTariff = userTariffList.get(userTariffList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTariffDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserTariff() throws Exception {
        int databaseSizeBeforeUpdate = userTariffRepository.findAll().size();
        userTariff.setId(count.incrementAndGet());

        // Create the UserTariff
        UserTariffDTO userTariffDTO = userTariffMapper.toDto(userTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTariffMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userTariffDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTariff in the database
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserTariff() throws Exception {
        // Initialize the database
        userTariffRepository.saveAndFlush(userTariff);

        int databaseSizeBeforeDelete = userTariffRepository.findAll().size();

        // Delete the userTariff
        restUserTariffMockMvc
            .perform(delete(ENTITY_API_URL_ID, userTariff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserTariff> userTariffList = userTariffRepository.findAll();
        assertThat(userTariffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
