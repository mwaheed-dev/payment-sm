package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.JazzCashPayment;
import com.simplerishta.cms.repository.JazzCashPaymentRepository;
import com.simplerishta.cms.service.dto.JazzCashPaymentDTO;
import com.simplerishta.cms.service.mapper.JazzCashPaymentMapper;
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
 * Integration tests for the {@link JazzCashPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JazzCashPaymentResourceIT {

    private static final String DEFAULT_PP_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PP_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PP_AUTH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_AUTH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_BANK_ID = "AAAAAAAAAA";
    private static final String UPDATED_PP_BANK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PP_BILL_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PP_BILL_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_PP_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_MERCHANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PP_MERCHANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PP_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_RESPONSE_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_PP_RESPONSE_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_RETREIVAL_REFERENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PP_RETREIVAL_REFERENCE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PP_SECURE_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PP_SECURE_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_PP_SETTLEMENT_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_PP_SETTLEMENT_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_PP_SUB_MERCHANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PP_SUB_MERCHANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PP_TXN_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PP_TXN_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PP_TXN_DATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_PP_TXN_DATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_PP_TXN_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_PP_TXN_REF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PP_TXN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PP_TXN_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PPMBF_1 = "AAAAAAAAAA";
    private static final String UPDATED_PPMBF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PPMBF_2 = "AAAAAAAAAA";
    private static final String UPDATED_PPMBF_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PPMBF_3 = "AAAAAAAAAA";
    private static final String UPDATED_PPMBF_3 = "BBBBBBBBBB";

    private static final String DEFAULT_PPMBF_4 = "AAAAAAAAAA";
    private static final String UPDATED_PPMBF_4 = "BBBBBBBBBB";

    private static final String DEFAULT_PPMBF_5 = "AAAAAAAAAA";
    private static final String UPDATED_PPMBF_5 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jazz-cash-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JazzCashPaymentRepository jazzCashPaymentRepository;

    @Autowired
    private JazzCashPaymentMapper jazzCashPaymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJazzCashPaymentMockMvc;

    private JazzCashPayment jazzCashPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JazzCashPayment createEntity(EntityManager em) {
        JazzCashPayment jazzCashPayment = new JazzCashPayment()
            .ppAmount(DEFAULT_PP_AMOUNT)
            .ppAuthCode(DEFAULT_PP_AUTH_CODE)
            .ppBankID(DEFAULT_PP_BANK_ID)
            .ppBillReference(DEFAULT_PP_BILL_REFERENCE)
            .ppLanguage(DEFAULT_PP_LANGUAGE)
            .ppMerchantID(DEFAULT_PP_MERCHANT_ID)
            .ppResponseCode(DEFAULT_PP_RESPONSE_CODE)
            .ppResponseMessage(DEFAULT_PP_RESPONSE_MESSAGE)
            .ppRetreivalReferenceNo(DEFAULT_PP_RETREIVAL_REFERENCE_NO)
            .ppSecureHash(DEFAULT_PP_SECURE_HASH)
            .ppSettlementExpiry(DEFAULT_PP_SETTLEMENT_EXPIRY)
            .ppSubMerchantId(DEFAULT_PP_SUB_MERCHANT_ID)
            .ppTxnCurrency(DEFAULT_PP_TXN_CURRENCY)
            .ppTxnDateTime(DEFAULT_PP_TXN_DATE_TIME)
            .ppTxnRefNo(DEFAULT_PP_TXN_REF_NO)
            .ppTxnType(DEFAULT_PP_TXN_TYPE)
            .ppVersion(DEFAULT_PP_VERSION)
            .ppmbf1(DEFAULT_PPMBF_1)
            .ppmbf2(DEFAULT_PPMBF_2)
            .ppmbf3(DEFAULT_PPMBF_3)
            .ppmbf4(DEFAULT_PPMBF_4)
            .ppmbf5(DEFAULT_PPMBF_5);
        return jazzCashPayment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JazzCashPayment createUpdatedEntity(EntityManager em) {
        JazzCashPayment jazzCashPayment = new JazzCashPayment()
            .ppAmount(UPDATED_PP_AMOUNT)
            .ppAuthCode(UPDATED_PP_AUTH_CODE)
            .ppBankID(UPDATED_PP_BANK_ID)
            .ppBillReference(UPDATED_PP_BILL_REFERENCE)
            .ppLanguage(UPDATED_PP_LANGUAGE)
            .ppMerchantID(UPDATED_PP_MERCHANT_ID)
            .ppResponseCode(UPDATED_PP_RESPONSE_CODE)
            .ppResponseMessage(UPDATED_PP_RESPONSE_MESSAGE)
            .ppRetreivalReferenceNo(UPDATED_PP_RETREIVAL_REFERENCE_NO)
            .ppSecureHash(UPDATED_PP_SECURE_HASH)
            .ppSettlementExpiry(UPDATED_PP_SETTLEMENT_EXPIRY)
            .ppSubMerchantId(UPDATED_PP_SUB_MERCHANT_ID)
            .ppTxnCurrency(UPDATED_PP_TXN_CURRENCY)
            .ppTxnDateTime(UPDATED_PP_TXN_DATE_TIME)
            .ppTxnRefNo(UPDATED_PP_TXN_REF_NO)
            .ppTxnType(UPDATED_PP_TXN_TYPE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppmbf1(UPDATED_PPMBF_1)
            .ppmbf2(UPDATED_PPMBF_2)
            .ppmbf3(UPDATED_PPMBF_3)
            .ppmbf4(UPDATED_PPMBF_4)
            .ppmbf5(UPDATED_PPMBF_5);
        return jazzCashPayment;
    }

    @BeforeEach
    public void initTest() {
        jazzCashPayment = createEntity(em);
    }

    @Test
    @Transactional
    void createJazzCashPayment() throws Exception {
        int databaseSizeBeforeCreate = jazzCashPaymentRepository.findAll().size();
        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);
        restJazzCashPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        JazzCashPayment testJazzCashPayment = jazzCashPaymentList.get(jazzCashPaymentList.size() - 1);
        assertThat(testJazzCashPayment.getPpAmount()).isEqualTo(DEFAULT_PP_AMOUNT);
        assertThat(testJazzCashPayment.getPpAuthCode()).isEqualTo(DEFAULT_PP_AUTH_CODE);
        assertThat(testJazzCashPayment.getPpBankID()).isEqualTo(DEFAULT_PP_BANK_ID);
        assertThat(testJazzCashPayment.getPpBillReference()).isEqualTo(DEFAULT_PP_BILL_REFERENCE);
        assertThat(testJazzCashPayment.getPpLanguage()).isEqualTo(DEFAULT_PP_LANGUAGE);
        assertThat(testJazzCashPayment.getPpMerchantID()).isEqualTo(DEFAULT_PP_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpResponseCode()).isEqualTo(DEFAULT_PP_RESPONSE_CODE);
        assertThat(testJazzCashPayment.getPpResponseMessage()).isEqualTo(DEFAULT_PP_RESPONSE_MESSAGE);
        assertThat(testJazzCashPayment.getPpRetreivalReferenceNo()).isEqualTo(DEFAULT_PP_RETREIVAL_REFERENCE_NO);
        assertThat(testJazzCashPayment.getPpSecureHash()).isEqualTo(DEFAULT_PP_SECURE_HASH);
        assertThat(testJazzCashPayment.getPpSettlementExpiry()).isEqualTo(DEFAULT_PP_SETTLEMENT_EXPIRY);
        assertThat(testJazzCashPayment.getPpSubMerchantId()).isEqualTo(DEFAULT_PP_SUB_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpTxnCurrency()).isEqualTo(DEFAULT_PP_TXN_CURRENCY);
        assertThat(testJazzCashPayment.getPpTxnDateTime()).isEqualTo(DEFAULT_PP_TXN_DATE_TIME);
        assertThat(testJazzCashPayment.getPpTxnRefNo()).isEqualTo(DEFAULT_PP_TXN_REF_NO);
        assertThat(testJazzCashPayment.getPpTxnType()).isEqualTo(DEFAULT_PP_TXN_TYPE);
        assertThat(testJazzCashPayment.getPpVersion()).isEqualTo(DEFAULT_PP_VERSION);
        assertThat(testJazzCashPayment.getPpmbf1()).isEqualTo(DEFAULT_PPMBF_1);
        assertThat(testJazzCashPayment.getPpmbf2()).isEqualTo(DEFAULT_PPMBF_2);
        assertThat(testJazzCashPayment.getPpmbf3()).isEqualTo(DEFAULT_PPMBF_3);
        assertThat(testJazzCashPayment.getPpmbf4()).isEqualTo(DEFAULT_PPMBF_4);
        assertThat(testJazzCashPayment.getPpmbf5()).isEqualTo(DEFAULT_PPMBF_5);
    }

    @Test
    @Transactional
    void createJazzCashPaymentWithExistingId() throws Exception {
        // Create the JazzCashPayment with an existing ID
        jazzCashPayment.setId(1L);
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        int databaseSizeBeforeCreate = jazzCashPaymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJazzCashPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJazzCashPayments() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        // Get all the jazzCashPaymentList
        restJazzCashPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jazzCashPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].ppAmount").value(hasItem(DEFAULT_PP_AMOUNT)))
            .andExpect(jsonPath("$.[*].ppAuthCode").value(hasItem(DEFAULT_PP_AUTH_CODE)))
            .andExpect(jsonPath("$.[*].ppBankID").value(hasItem(DEFAULT_PP_BANK_ID)))
            .andExpect(jsonPath("$.[*].ppBillReference").value(hasItem(DEFAULT_PP_BILL_REFERENCE)))
            .andExpect(jsonPath("$.[*].ppLanguage").value(hasItem(DEFAULT_PP_LANGUAGE)))
            .andExpect(jsonPath("$.[*].ppMerchantID").value(hasItem(DEFAULT_PP_MERCHANT_ID)))
            .andExpect(jsonPath("$.[*].ppResponseCode").value(hasItem(DEFAULT_PP_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].ppResponseMessage").value(hasItem(DEFAULT_PP_RESPONSE_MESSAGE)))
            .andExpect(jsonPath("$.[*].ppRetreivalReferenceNo").value(hasItem(DEFAULT_PP_RETREIVAL_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].ppSecureHash").value(hasItem(DEFAULT_PP_SECURE_HASH)))
            .andExpect(jsonPath("$.[*].ppSettlementExpiry").value(hasItem(DEFAULT_PP_SETTLEMENT_EXPIRY)))
            .andExpect(jsonPath("$.[*].ppSubMerchantId").value(hasItem(DEFAULT_PP_SUB_MERCHANT_ID)))
            .andExpect(jsonPath("$.[*].ppTxnCurrency").value(hasItem(DEFAULT_PP_TXN_CURRENCY)))
            .andExpect(jsonPath("$.[*].ppTxnDateTime").value(hasItem(DEFAULT_PP_TXN_DATE_TIME)))
            .andExpect(jsonPath("$.[*].ppTxnRefNo").value(hasItem(DEFAULT_PP_TXN_REF_NO)))
            .andExpect(jsonPath("$.[*].ppTxnType").value(hasItem(DEFAULT_PP_TXN_TYPE)))
            .andExpect(jsonPath("$.[*].ppVersion").value(hasItem(DEFAULT_PP_VERSION)))
            .andExpect(jsonPath("$.[*].ppmbf1").value(hasItem(DEFAULT_PPMBF_1)))
            .andExpect(jsonPath("$.[*].ppmbf2").value(hasItem(DEFAULT_PPMBF_2)))
            .andExpect(jsonPath("$.[*].ppmbf3").value(hasItem(DEFAULT_PPMBF_3)))
            .andExpect(jsonPath("$.[*].ppmbf4").value(hasItem(DEFAULT_PPMBF_4)))
            .andExpect(jsonPath("$.[*].ppmbf5").value(hasItem(DEFAULT_PPMBF_5)));
    }

    @Test
    @Transactional
    void getJazzCashPayment() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        // Get the jazzCashPayment
        restJazzCashPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, jazzCashPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jazzCashPayment.getId().intValue()))
            .andExpect(jsonPath("$.ppAmount").value(DEFAULT_PP_AMOUNT))
            .andExpect(jsonPath("$.ppAuthCode").value(DEFAULT_PP_AUTH_CODE))
            .andExpect(jsonPath("$.ppBankID").value(DEFAULT_PP_BANK_ID))
            .andExpect(jsonPath("$.ppBillReference").value(DEFAULT_PP_BILL_REFERENCE))
            .andExpect(jsonPath("$.ppLanguage").value(DEFAULT_PP_LANGUAGE))
            .andExpect(jsonPath("$.ppMerchantID").value(DEFAULT_PP_MERCHANT_ID))
            .andExpect(jsonPath("$.ppResponseCode").value(DEFAULT_PP_RESPONSE_CODE))
            .andExpect(jsonPath("$.ppResponseMessage").value(DEFAULT_PP_RESPONSE_MESSAGE))
            .andExpect(jsonPath("$.ppRetreivalReferenceNo").value(DEFAULT_PP_RETREIVAL_REFERENCE_NO))
            .andExpect(jsonPath("$.ppSecureHash").value(DEFAULT_PP_SECURE_HASH))
            .andExpect(jsonPath("$.ppSettlementExpiry").value(DEFAULT_PP_SETTLEMENT_EXPIRY))
            .andExpect(jsonPath("$.ppSubMerchantId").value(DEFAULT_PP_SUB_MERCHANT_ID))
            .andExpect(jsonPath("$.ppTxnCurrency").value(DEFAULT_PP_TXN_CURRENCY))
            .andExpect(jsonPath("$.ppTxnDateTime").value(DEFAULT_PP_TXN_DATE_TIME))
            .andExpect(jsonPath("$.ppTxnRefNo").value(DEFAULT_PP_TXN_REF_NO))
            .andExpect(jsonPath("$.ppTxnType").value(DEFAULT_PP_TXN_TYPE))
            .andExpect(jsonPath("$.ppVersion").value(DEFAULT_PP_VERSION))
            .andExpect(jsonPath("$.ppmbf1").value(DEFAULT_PPMBF_1))
            .andExpect(jsonPath("$.ppmbf2").value(DEFAULT_PPMBF_2))
            .andExpect(jsonPath("$.ppmbf3").value(DEFAULT_PPMBF_3))
            .andExpect(jsonPath("$.ppmbf4").value(DEFAULT_PPMBF_4))
            .andExpect(jsonPath("$.ppmbf5").value(DEFAULT_PPMBF_5));
    }

    @Test
    @Transactional
    void getNonExistingJazzCashPayment() throws Exception {
        // Get the jazzCashPayment
        restJazzCashPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJazzCashPayment() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();

        // Update the jazzCashPayment
        JazzCashPayment updatedJazzCashPayment = jazzCashPaymentRepository.findById(jazzCashPayment.getId()).get();
        // Disconnect from session so that the updates on updatedJazzCashPayment are not directly saved in db
        em.detach(updatedJazzCashPayment);
        updatedJazzCashPayment
            .ppAmount(UPDATED_PP_AMOUNT)
            .ppAuthCode(UPDATED_PP_AUTH_CODE)
            .ppBankID(UPDATED_PP_BANK_ID)
            .ppBillReference(UPDATED_PP_BILL_REFERENCE)
            .ppLanguage(UPDATED_PP_LANGUAGE)
            .ppMerchantID(UPDATED_PP_MERCHANT_ID)
            .ppResponseCode(UPDATED_PP_RESPONSE_CODE)
            .ppResponseMessage(UPDATED_PP_RESPONSE_MESSAGE)
            .ppRetreivalReferenceNo(UPDATED_PP_RETREIVAL_REFERENCE_NO)
            .ppSecureHash(UPDATED_PP_SECURE_HASH)
            .ppSettlementExpiry(UPDATED_PP_SETTLEMENT_EXPIRY)
            .ppSubMerchantId(UPDATED_PP_SUB_MERCHANT_ID)
            .ppTxnCurrency(UPDATED_PP_TXN_CURRENCY)
            .ppTxnDateTime(UPDATED_PP_TXN_DATE_TIME)
            .ppTxnRefNo(UPDATED_PP_TXN_REF_NO)
            .ppTxnType(UPDATED_PP_TXN_TYPE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppmbf1(UPDATED_PPMBF_1)
            .ppmbf2(UPDATED_PPMBF_2)
            .ppmbf3(UPDATED_PPMBF_3)
            .ppmbf4(UPDATED_PPMBF_4)
            .ppmbf5(UPDATED_PPMBF_5);
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(updatedJazzCashPayment);

        restJazzCashPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jazzCashPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
        JazzCashPayment testJazzCashPayment = jazzCashPaymentList.get(jazzCashPaymentList.size() - 1);
        assertThat(testJazzCashPayment.getPpAmount()).isEqualTo(UPDATED_PP_AMOUNT);
        assertThat(testJazzCashPayment.getPpAuthCode()).isEqualTo(UPDATED_PP_AUTH_CODE);
        assertThat(testJazzCashPayment.getPpBankID()).isEqualTo(UPDATED_PP_BANK_ID);
        assertThat(testJazzCashPayment.getPpBillReference()).isEqualTo(UPDATED_PP_BILL_REFERENCE);
        assertThat(testJazzCashPayment.getPpLanguage()).isEqualTo(UPDATED_PP_LANGUAGE);
        assertThat(testJazzCashPayment.getPpMerchantID()).isEqualTo(UPDATED_PP_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpResponseCode()).isEqualTo(UPDATED_PP_RESPONSE_CODE);
        assertThat(testJazzCashPayment.getPpResponseMessage()).isEqualTo(UPDATED_PP_RESPONSE_MESSAGE);
        assertThat(testJazzCashPayment.getPpRetreivalReferenceNo()).isEqualTo(UPDATED_PP_RETREIVAL_REFERENCE_NO);
        assertThat(testJazzCashPayment.getPpSecureHash()).isEqualTo(UPDATED_PP_SECURE_HASH);
        assertThat(testJazzCashPayment.getPpSettlementExpiry()).isEqualTo(UPDATED_PP_SETTLEMENT_EXPIRY);
        assertThat(testJazzCashPayment.getPpSubMerchantId()).isEqualTo(UPDATED_PP_SUB_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpTxnCurrency()).isEqualTo(UPDATED_PP_TXN_CURRENCY);
        assertThat(testJazzCashPayment.getPpTxnDateTime()).isEqualTo(UPDATED_PP_TXN_DATE_TIME);
        assertThat(testJazzCashPayment.getPpTxnRefNo()).isEqualTo(UPDATED_PP_TXN_REF_NO);
        assertThat(testJazzCashPayment.getPpTxnType()).isEqualTo(UPDATED_PP_TXN_TYPE);
        assertThat(testJazzCashPayment.getPpVersion()).isEqualTo(UPDATED_PP_VERSION);
        assertThat(testJazzCashPayment.getPpmbf1()).isEqualTo(UPDATED_PPMBF_1);
        assertThat(testJazzCashPayment.getPpmbf2()).isEqualTo(UPDATED_PPMBF_2);
        assertThat(testJazzCashPayment.getPpmbf3()).isEqualTo(UPDATED_PPMBF_3);
        assertThat(testJazzCashPayment.getPpmbf4()).isEqualTo(UPDATED_PPMBF_4);
        assertThat(testJazzCashPayment.getPpmbf5()).isEqualTo(UPDATED_PPMBF_5);
    }

    @Test
    @Transactional
    void putNonExistingJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jazzCashPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJazzCashPaymentWithPatch() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();

        // Update the jazzCashPayment using partial update
        JazzCashPayment partialUpdatedJazzCashPayment = new JazzCashPayment();
        partialUpdatedJazzCashPayment.setId(jazzCashPayment.getId());

        partialUpdatedJazzCashPayment
            .ppAuthCode(UPDATED_PP_AUTH_CODE)
            .ppBankID(UPDATED_PP_BANK_ID)
            .ppMerchantID(UPDATED_PP_MERCHANT_ID)
            .ppRetreivalReferenceNo(UPDATED_PP_RETREIVAL_REFERENCE_NO)
            .ppSettlementExpiry(UPDATED_PP_SETTLEMENT_EXPIRY)
            .ppSubMerchantId(UPDATED_PP_SUB_MERCHANT_ID)
            .ppTxnCurrency(UPDATED_PP_TXN_CURRENCY)
            .ppTxnRefNo(UPDATED_PP_TXN_REF_NO)
            .ppTxnType(UPDATED_PP_TXN_TYPE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppmbf2(UPDATED_PPMBF_2)
            .ppmbf3(UPDATED_PPMBF_3)
            .ppmbf4(UPDATED_PPMBF_4)
            .ppmbf5(UPDATED_PPMBF_5);

        restJazzCashPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJazzCashPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJazzCashPayment))
            )
            .andExpect(status().isOk());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
        JazzCashPayment testJazzCashPayment = jazzCashPaymentList.get(jazzCashPaymentList.size() - 1);
        assertThat(testJazzCashPayment.getPpAmount()).isEqualTo(DEFAULT_PP_AMOUNT);
        assertThat(testJazzCashPayment.getPpAuthCode()).isEqualTo(UPDATED_PP_AUTH_CODE);
        assertThat(testJazzCashPayment.getPpBankID()).isEqualTo(UPDATED_PP_BANK_ID);
        assertThat(testJazzCashPayment.getPpBillReference()).isEqualTo(DEFAULT_PP_BILL_REFERENCE);
        assertThat(testJazzCashPayment.getPpLanguage()).isEqualTo(DEFAULT_PP_LANGUAGE);
        assertThat(testJazzCashPayment.getPpMerchantID()).isEqualTo(UPDATED_PP_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpResponseCode()).isEqualTo(DEFAULT_PP_RESPONSE_CODE);
        assertThat(testJazzCashPayment.getPpResponseMessage()).isEqualTo(DEFAULT_PP_RESPONSE_MESSAGE);
        assertThat(testJazzCashPayment.getPpRetreivalReferenceNo()).isEqualTo(UPDATED_PP_RETREIVAL_REFERENCE_NO);
        assertThat(testJazzCashPayment.getPpSecureHash()).isEqualTo(DEFAULT_PP_SECURE_HASH);
        assertThat(testJazzCashPayment.getPpSettlementExpiry()).isEqualTo(UPDATED_PP_SETTLEMENT_EXPIRY);
        assertThat(testJazzCashPayment.getPpSubMerchantId()).isEqualTo(UPDATED_PP_SUB_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpTxnCurrency()).isEqualTo(UPDATED_PP_TXN_CURRENCY);
        assertThat(testJazzCashPayment.getPpTxnDateTime()).isEqualTo(DEFAULT_PP_TXN_DATE_TIME);
        assertThat(testJazzCashPayment.getPpTxnRefNo()).isEqualTo(UPDATED_PP_TXN_REF_NO);
        assertThat(testJazzCashPayment.getPpTxnType()).isEqualTo(UPDATED_PP_TXN_TYPE);
        assertThat(testJazzCashPayment.getPpVersion()).isEqualTo(UPDATED_PP_VERSION);
        assertThat(testJazzCashPayment.getPpmbf1()).isEqualTo(DEFAULT_PPMBF_1);
        assertThat(testJazzCashPayment.getPpmbf2()).isEqualTo(UPDATED_PPMBF_2);
        assertThat(testJazzCashPayment.getPpmbf3()).isEqualTo(UPDATED_PPMBF_3);
        assertThat(testJazzCashPayment.getPpmbf4()).isEqualTo(UPDATED_PPMBF_4);
        assertThat(testJazzCashPayment.getPpmbf5()).isEqualTo(UPDATED_PPMBF_5);
    }

    @Test
    @Transactional
    void fullUpdateJazzCashPaymentWithPatch() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();

        // Update the jazzCashPayment using partial update
        JazzCashPayment partialUpdatedJazzCashPayment = new JazzCashPayment();
        partialUpdatedJazzCashPayment.setId(jazzCashPayment.getId());

        partialUpdatedJazzCashPayment
            .ppAmount(UPDATED_PP_AMOUNT)
            .ppAuthCode(UPDATED_PP_AUTH_CODE)
            .ppBankID(UPDATED_PP_BANK_ID)
            .ppBillReference(UPDATED_PP_BILL_REFERENCE)
            .ppLanguage(UPDATED_PP_LANGUAGE)
            .ppMerchantID(UPDATED_PP_MERCHANT_ID)
            .ppResponseCode(UPDATED_PP_RESPONSE_CODE)
            .ppResponseMessage(UPDATED_PP_RESPONSE_MESSAGE)
            .ppRetreivalReferenceNo(UPDATED_PP_RETREIVAL_REFERENCE_NO)
            .ppSecureHash(UPDATED_PP_SECURE_HASH)
            .ppSettlementExpiry(UPDATED_PP_SETTLEMENT_EXPIRY)
            .ppSubMerchantId(UPDATED_PP_SUB_MERCHANT_ID)
            .ppTxnCurrency(UPDATED_PP_TXN_CURRENCY)
            .ppTxnDateTime(UPDATED_PP_TXN_DATE_TIME)
            .ppTxnRefNo(UPDATED_PP_TXN_REF_NO)
            .ppTxnType(UPDATED_PP_TXN_TYPE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppmbf1(UPDATED_PPMBF_1)
            .ppmbf2(UPDATED_PPMBF_2)
            .ppmbf3(UPDATED_PPMBF_3)
            .ppmbf4(UPDATED_PPMBF_4)
            .ppmbf5(UPDATED_PPMBF_5);

        restJazzCashPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJazzCashPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJazzCashPayment))
            )
            .andExpect(status().isOk());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
        JazzCashPayment testJazzCashPayment = jazzCashPaymentList.get(jazzCashPaymentList.size() - 1);
        assertThat(testJazzCashPayment.getPpAmount()).isEqualTo(UPDATED_PP_AMOUNT);
        assertThat(testJazzCashPayment.getPpAuthCode()).isEqualTo(UPDATED_PP_AUTH_CODE);
        assertThat(testJazzCashPayment.getPpBankID()).isEqualTo(UPDATED_PP_BANK_ID);
        assertThat(testJazzCashPayment.getPpBillReference()).isEqualTo(UPDATED_PP_BILL_REFERENCE);
        assertThat(testJazzCashPayment.getPpLanguage()).isEqualTo(UPDATED_PP_LANGUAGE);
        assertThat(testJazzCashPayment.getPpMerchantID()).isEqualTo(UPDATED_PP_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpResponseCode()).isEqualTo(UPDATED_PP_RESPONSE_CODE);
        assertThat(testJazzCashPayment.getPpResponseMessage()).isEqualTo(UPDATED_PP_RESPONSE_MESSAGE);
        assertThat(testJazzCashPayment.getPpRetreivalReferenceNo()).isEqualTo(UPDATED_PP_RETREIVAL_REFERENCE_NO);
        assertThat(testJazzCashPayment.getPpSecureHash()).isEqualTo(UPDATED_PP_SECURE_HASH);
        assertThat(testJazzCashPayment.getPpSettlementExpiry()).isEqualTo(UPDATED_PP_SETTLEMENT_EXPIRY);
        assertThat(testJazzCashPayment.getPpSubMerchantId()).isEqualTo(UPDATED_PP_SUB_MERCHANT_ID);
        assertThat(testJazzCashPayment.getPpTxnCurrency()).isEqualTo(UPDATED_PP_TXN_CURRENCY);
        assertThat(testJazzCashPayment.getPpTxnDateTime()).isEqualTo(UPDATED_PP_TXN_DATE_TIME);
        assertThat(testJazzCashPayment.getPpTxnRefNo()).isEqualTo(UPDATED_PP_TXN_REF_NO);
        assertThat(testJazzCashPayment.getPpTxnType()).isEqualTo(UPDATED_PP_TXN_TYPE);
        assertThat(testJazzCashPayment.getPpVersion()).isEqualTo(UPDATED_PP_VERSION);
        assertThat(testJazzCashPayment.getPpmbf1()).isEqualTo(UPDATED_PPMBF_1);
        assertThat(testJazzCashPayment.getPpmbf2()).isEqualTo(UPDATED_PPMBF_2);
        assertThat(testJazzCashPayment.getPpmbf3()).isEqualTo(UPDATED_PPMBF_3);
        assertThat(testJazzCashPayment.getPpmbf4()).isEqualTo(UPDATED_PPMBF_4);
        assertThat(testJazzCashPayment.getPpmbf5()).isEqualTo(UPDATED_PPMBF_5);
    }

    @Test
    @Transactional
    void patchNonExistingJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jazzCashPaymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJazzCashPayment() throws Exception {
        int databaseSizeBeforeUpdate = jazzCashPaymentRepository.findAll().size();
        jazzCashPayment.setId(count.incrementAndGet());

        // Create the JazzCashPayment
        JazzCashPaymentDTO jazzCashPaymentDTO = jazzCashPaymentMapper.toDto(jazzCashPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJazzCashPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jazzCashPaymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JazzCashPayment in the database
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJazzCashPayment() throws Exception {
        // Initialize the database
        jazzCashPaymentRepository.saveAndFlush(jazzCashPayment);

        int databaseSizeBeforeDelete = jazzCashPaymentRepository.findAll().size();

        // Delete the jazzCashPayment
        restJazzCashPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, jazzCashPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JazzCashPayment> jazzCashPaymentList = jazzCashPaymentRepository.findAll();
        assertThat(jazzCashPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
