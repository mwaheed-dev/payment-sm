package com.simplerishta.cms.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JazzCashPayment.
 */
@Entity
@Table(name = "jazz_cash_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JazzCashPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "pp_amount")
    private String ppAmount;

    @Column(name = "pp_auth_code")
    private String ppAuthCode;

    @Column(name = "pp_bank_id")
    private String ppBankID;

    @Column(name = "pp_bill_reference")
    private String ppBillReference;

    @Column(name = "pp_language")
    private String ppLanguage;

    @Column(name = "pp_merchant_id")
    private String ppMerchantID;

    @Column(name = "pp_response_code")
    private String ppResponseCode;

    @Column(name = "pp_response_message")
    private String ppResponseMessage;

    @Column(name = "pp_retreival_reference_no")
    private String ppRetreivalReferenceNo;

    @Column(name = "pp_secure_hash")
    private String ppSecureHash;

    @Column(name = "pp_settlement_expiry")
    private String ppSettlementExpiry;

    @Column(name = "pp_sub_merchant_id")
    private String ppSubMerchantId;

    @Column(name = "pp_txn_currency")
    private String ppTxnCurrency;

    @Column(name = "pp_txn_date_time")
    private String ppTxnDateTime;

    @Column(name = "pp_txn_ref_no")
    private String ppTxnRefNo;

    @Column(name = "pp_txn_type")
    private String ppTxnType;

    @Column(name = "pp_version")
    private String ppVersion;

    @Column(name = "ppmbf_1")
    private String ppmbf1;

    @Column(name = "ppmbf_2")
    private String ppmbf2;

    @Column(name = "ppmbf_3")
    private String ppmbf3;

    @Column(name = "ppmbf_4")
    private String ppmbf4;

    @Column(name = "ppmbf_5")
    private String ppmbf5;

    @ManyToOne
    private Payments payments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JazzCashPayment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPpAmount() {
        return this.ppAmount;
    }

    public JazzCashPayment ppAmount(String ppAmount) {
        this.setPpAmount(ppAmount);
        return this;
    }

    public void setPpAmount(String ppAmount) {
        this.ppAmount = ppAmount;
    }

    public String getPpAuthCode() {
        return this.ppAuthCode;
    }

    public JazzCashPayment ppAuthCode(String ppAuthCode) {
        this.setPpAuthCode(ppAuthCode);
        return this;
    }

    public void setPpAuthCode(String ppAuthCode) {
        this.ppAuthCode = ppAuthCode;
    }

    public String getPpBankID() {
        return this.ppBankID;
    }

    public JazzCashPayment ppBankID(String ppBankID) {
        this.setPpBankID(ppBankID);
        return this;
    }

    public void setPpBankID(String ppBankID) {
        this.ppBankID = ppBankID;
    }

    public String getPpBillReference() {
        return this.ppBillReference;
    }

    public JazzCashPayment ppBillReference(String ppBillReference) {
        this.setPpBillReference(ppBillReference);
        return this;
    }

    public void setPpBillReference(String ppBillReference) {
        this.ppBillReference = ppBillReference;
    }

    public String getPpLanguage() {
        return this.ppLanguage;
    }

    public JazzCashPayment ppLanguage(String ppLanguage) {
        this.setPpLanguage(ppLanguage);
        return this;
    }

    public void setPpLanguage(String ppLanguage) {
        this.ppLanguage = ppLanguage;
    }

    public String getPpMerchantID() {
        return this.ppMerchantID;
    }

    public JazzCashPayment ppMerchantID(String ppMerchantID) {
        this.setPpMerchantID(ppMerchantID);
        return this;
    }

    public void setPpMerchantID(String ppMerchantID) {
        this.ppMerchantID = ppMerchantID;
    }

    public String getPpResponseCode() {
        return this.ppResponseCode;
    }

    public JazzCashPayment ppResponseCode(String ppResponseCode) {
        this.setPpResponseCode(ppResponseCode);
        return this;
    }

    public void setPpResponseCode(String ppResponseCode) {
        this.ppResponseCode = ppResponseCode;
    }

    public String getPpResponseMessage() {
        return this.ppResponseMessage;
    }

    public JazzCashPayment ppResponseMessage(String ppResponseMessage) {
        this.setPpResponseMessage(ppResponseMessage);
        return this;
    }

    public void setPpResponseMessage(String ppResponseMessage) {
        this.ppResponseMessage = ppResponseMessage;
    }

    public String getPpRetreivalReferenceNo() {
        return this.ppRetreivalReferenceNo;
    }

    public JazzCashPayment ppRetreivalReferenceNo(String ppRetreivalReferenceNo) {
        this.setPpRetreivalReferenceNo(ppRetreivalReferenceNo);
        return this;
    }

    public void setPpRetreivalReferenceNo(String ppRetreivalReferenceNo) {
        this.ppRetreivalReferenceNo = ppRetreivalReferenceNo;
    }

    public String getPpSecureHash() {
        return this.ppSecureHash;
    }

    public JazzCashPayment ppSecureHash(String ppSecureHash) {
        this.setPpSecureHash(ppSecureHash);
        return this;
    }

    public void setPpSecureHash(String ppSecureHash) {
        this.ppSecureHash = ppSecureHash;
    }

    public String getPpSettlementExpiry() {
        return this.ppSettlementExpiry;
    }

    public JazzCashPayment ppSettlementExpiry(String ppSettlementExpiry) {
        this.setPpSettlementExpiry(ppSettlementExpiry);
        return this;
    }

    public void setPpSettlementExpiry(String ppSettlementExpiry) {
        this.ppSettlementExpiry = ppSettlementExpiry;
    }

    public String getPpSubMerchantId() {
        return this.ppSubMerchantId;
    }

    public JazzCashPayment ppSubMerchantId(String ppSubMerchantId) {
        this.setPpSubMerchantId(ppSubMerchantId);
        return this;
    }

    public void setPpSubMerchantId(String ppSubMerchantId) {
        this.ppSubMerchantId = ppSubMerchantId;
    }

    public String getPpTxnCurrency() {
        return this.ppTxnCurrency;
    }

    public JazzCashPayment ppTxnCurrency(String ppTxnCurrency) {
        this.setPpTxnCurrency(ppTxnCurrency);
        return this;
    }

    public void setPpTxnCurrency(String ppTxnCurrency) {
        this.ppTxnCurrency = ppTxnCurrency;
    }

    public String getPpTxnDateTime() {
        return this.ppTxnDateTime;
    }

    public JazzCashPayment ppTxnDateTime(String ppTxnDateTime) {
        this.setPpTxnDateTime(ppTxnDateTime);
        return this;
    }

    public void setPpTxnDateTime(String ppTxnDateTime) {
        this.ppTxnDateTime = ppTxnDateTime;
    }

    public String getPpTxnRefNo() {
        return this.ppTxnRefNo;
    }

    public JazzCashPayment ppTxnRefNo(String ppTxnRefNo) {
        this.setPpTxnRefNo(ppTxnRefNo);
        return this;
    }

    public void setPpTxnRefNo(String ppTxnRefNo) {
        this.ppTxnRefNo = ppTxnRefNo;
    }

    public String getPpTxnType() {
        return this.ppTxnType;
    }

    public JazzCashPayment ppTxnType(String ppTxnType) {
        this.setPpTxnType(ppTxnType);
        return this;
    }

    public void setPpTxnType(String ppTxnType) {
        this.ppTxnType = ppTxnType;
    }

    public String getPpVersion() {
        return this.ppVersion;
    }

    public JazzCashPayment ppVersion(String ppVersion) {
        this.setPpVersion(ppVersion);
        return this;
    }

    public void setPpVersion(String ppVersion) {
        this.ppVersion = ppVersion;
    }

    public String getPpmbf1() {
        return this.ppmbf1;
    }

    public JazzCashPayment ppmbf1(String ppmbf1) {
        this.setPpmbf1(ppmbf1);
        return this;
    }

    public void setPpmbf1(String ppmbf1) {
        this.ppmbf1 = ppmbf1;
    }

    public String getPpmbf2() {
        return this.ppmbf2;
    }

    public JazzCashPayment ppmbf2(String ppmbf2) {
        this.setPpmbf2(ppmbf2);
        return this;
    }

    public void setPpmbf2(String ppmbf2) {
        this.ppmbf2 = ppmbf2;
    }

    public String getPpmbf3() {
        return this.ppmbf3;
    }

    public JazzCashPayment ppmbf3(String ppmbf3) {
        this.setPpmbf3(ppmbf3);
        return this;
    }

    public void setPpmbf3(String ppmbf3) {
        this.ppmbf3 = ppmbf3;
    }

    public String getPpmbf4() {
        return this.ppmbf4;
    }

    public JazzCashPayment ppmbf4(String ppmbf4) {
        this.setPpmbf4(ppmbf4);
        return this;
    }

    public void setPpmbf4(String ppmbf4) {
        this.ppmbf4 = ppmbf4;
    }

    public String getPpmbf5() {
        return this.ppmbf5;
    }

    public JazzCashPayment ppmbf5(String ppmbf5) {
        this.setPpmbf5(ppmbf5);
        return this;
    }

    public void setPpmbf5(String ppmbf5) {
        this.ppmbf5 = ppmbf5;
    }

    public Payments getPayments() {
        return this.payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public JazzCashPayment payments(Payments payments) {
        this.setPayments(payments);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JazzCashPayment)) {
            return false;
        }
        return id != null && id.equals(((JazzCashPayment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JazzCashPayment{" +
            "id=" + getId() +
            ", ppAmount='" + getPpAmount() + "'" +
            ", ppAuthCode='" + getPpAuthCode() + "'" +
            ", ppBankID='" + getPpBankID() + "'" +
            ", ppBillReference='" + getPpBillReference() + "'" +
            ", ppLanguage='" + getPpLanguage() + "'" +
            ", ppMerchantID='" + getPpMerchantID() + "'" +
            ", ppResponseCode='" + getPpResponseCode() + "'" +
            ", ppResponseMessage='" + getPpResponseMessage() + "'" +
            ", ppRetreivalReferenceNo='" + getPpRetreivalReferenceNo() + "'" +
            ", ppSecureHash='" + getPpSecureHash() + "'" +
            ", ppSettlementExpiry='" + getPpSettlementExpiry() + "'" +
            ", ppSubMerchantId='" + getPpSubMerchantId() + "'" +
            ", ppTxnCurrency='" + getPpTxnCurrency() + "'" +
            ", ppTxnDateTime='" + getPpTxnDateTime() + "'" +
            ", ppTxnRefNo='" + getPpTxnRefNo() + "'" +
            ", ppTxnType='" + getPpTxnType() + "'" +
            ", ppVersion='" + getPpVersion() + "'" +
            ", ppmbf1='" + getPpmbf1() + "'" +
            ", ppmbf2='" + getPpmbf2() + "'" +
            ", ppmbf3='" + getPpmbf3() + "'" +
            ", ppmbf4='" + getPpmbf4() + "'" +
            ", ppmbf5='" + getPpmbf5() + "'" +
            "}";
    }
}
