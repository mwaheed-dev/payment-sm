package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.Payments;
import com.simplerishta.cms.repository.PaymentsRepository;
import com.simplerishta.cms.service.dto.PaymentsDTO;
import com.simplerishta.cms.service.mapper.PaymentsMapper;
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
 * Integration tests for the {@link PaymentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentsResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_PROVIDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSACTION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TXN_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TXN_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private PaymentsMapper paymentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity(EntityManager em) {
        Payments payments = new Payments()
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .paymentProvider(DEFAULT_PAYMENT_PROVIDER)
            .transactionTime(DEFAULT_TRANSACTION_TIME)
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .txnReferenceNumber(DEFAULT_TXN_REFERENCE_NUMBER)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .status(DEFAULT_STATUS)
            .providerReferenceNumber(DEFAULT_PROVIDER_REFERENCE_NUMBER)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return payments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createUpdatedEntity(EntityManager em) {
        Payments payments = new Payments()
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER)
            .transactionTime(UPDATED_TRANSACTION_TIME)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .txnReferenceNumber(UPDATED_TXN_REFERENCE_NUMBER)
            .responseCode(UPDATED_RESPONSE_CODE)
            .status(UPDATED_STATUS)
            .providerReferenceNumber(UPDATED_PROVIDER_REFERENCE_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return payments;
    }

    @BeforeEach
    public void initTest() {
        payments = createEntity(em);
    }

    @Test
    @Transactional
    void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();
        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testPayments.getPaymentProvider()).isEqualTo(DEFAULT_PAYMENT_PROVIDER);
        assertThat(testPayments.getTransactionTime()).isEqualTo(DEFAULT_TRANSACTION_TIME);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPayments.getTxnReferenceNumber()).isEqualTo(DEFAULT_TXN_REFERENCE_NUMBER);
        assertThat(testPayments.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayments.getProviderReferenceNumber()).isEqualTo(DEFAULT_PROVIDER_REFERENCE_NUMBER);
        assertThat(testPayments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createPaymentsWithExistingId() throws Exception {
        // Create the Payments with an existing ID
        payments.setId(1L);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setTransactionType(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentProvider(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setCreatedAt(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].paymentProvider").value(hasItem(DEFAULT_PAYMENT_PROVIDER)))
            .andExpect(jsonPath("$.[*].transactionTime").value(hasItem(DEFAULT_TRANSACTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].txnReferenceNumber").value(hasItem(DEFAULT_TXN_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].providerReferenceNumber").value(hasItem(DEFAULT_PROVIDER_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get the payments
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId().intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.paymentProvider").value(DEFAULT_PAYMENT_PROVIDER))
            .andExpect(jsonPath("$.transactionTime").value(DEFAULT_TRANSACTION_TIME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.txnReferenceNumber").value(DEFAULT_TXN_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.providerReferenceNumber").value(DEFAULT_PROVIDER_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getId()).get();
        // Disconnect from session so that the updates on updatedPayments are not directly saved in db
        em.detach(updatedPayments);
        updatedPayments
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER)
            .transactionTime(UPDATED_TRANSACTION_TIME)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .txnReferenceNumber(UPDATED_TXN_REFERENCE_NUMBER)
            .responseCode(UPDATED_RESPONSE_CODE)
            .status(UPDATED_STATUS)
            .providerReferenceNumber(UPDATED_PROVIDER_REFERENCE_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(updatedPayments);

        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPayments.getPaymentProvider()).isEqualTo(UPDATED_PAYMENT_PROVIDER);
        assertThat(testPayments.getTransactionTime()).isEqualTo(UPDATED_TRANSACTION_TIME);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getTxnReferenceNumber()).isEqualTo(UPDATED_TXN_REFERENCE_NUMBER);
        assertThat(testPayments.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayments.getProviderReferenceNumber()).isEqualTo(UPDATED_PROVIDER_REFERENCE_NUMBER);
        assertThat(testPayments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .txnReferenceNumber(UPDATED_TXN_REFERENCE_NUMBER);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPayments.getPaymentProvider()).isEqualTo(UPDATED_PAYMENT_PROVIDER);
        assertThat(testPayments.getTransactionTime()).isEqualTo(DEFAULT_TRANSACTION_TIME);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getTxnReferenceNumber()).isEqualTo(UPDATED_TXN_REFERENCE_NUMBER);
        assertThat(testPayments.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayments.getProviderReferenceNumber()).isEqualTo(DEFAULT_PROVIDER_REFERENCE_NUMBER);
        assertThat(testPayments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER)
            .transactionTime(UPDATED_TRANSACTION_TIME)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .txnReferenceNumber(UPDATED_TXN_REFERENCE_NUMBER)
            .responseCode(UPDATED_RESPONSE_CODE)
            .status(UPDATED_STATUS)
            .providerReferenceNumber(UPDATED_PROVIDER_REFERENCE_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPayments.getPaymentProvider()).isEqualTo(UPDATED_PAYMENT_PROVIDER);
        assertThat(testPayments.getTransactionTime()).isEqualTo(UPDATED_TRANSACTION_TIME);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getTxnReferenceNumber()).isEqualTo(UPDATED_TXN_REFERENCE_NUMBER);
        assertThat(testPayments.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayments.getProviderReferenceNumber()).isEqualTo(UPDATED_PROVIDER_REFERENCE_NUMBER);
        assertThat(testPayments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, payments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
