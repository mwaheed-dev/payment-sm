package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.JazzCashPayment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JazzCashPaymentDTO implements Serializable {

    private Long id;

    private String ppAmount;

    private String ppAuthCode;

    private String ppBankID;

    private String ppBillReference;

    private String ppLanguage;

    private String ppMerchantID;

    private String ppResponseCode;

    private String ppResponseMessage;

    private String ppRetreivalReferenceNo;

    private String ppSecureHash;

    private String ppSettlementExpiry;

    private String ppSubMerchantId;

    private String ppTxnCurrency;

    private String ppTxnDateTime;

    private String ppTxnRefNo;

    private String ppTxnType;

    private String ppVersion;

    private String ppmbf1;

    private String ppmbf2;

    private String ppmbf3;

    private String ppmbf4;

    private String ppmbf5;

    private PaymentsDTO payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPpAmount() {
        return ppAmount;
    }

    public void setPpAmount(String ppAmount) {
        this.ppAmount = ppAmount;
    }

    public String getPpAuthCode() {
        return ppAuthCode;
    }

    public void setPpAuthCode(String ppAuthCode) {
        this.ppAuthCode = ppAuthCode;
    }

    public String getPpBankID() {
        return ppBankID;
    }

    public void setPpBankID(String ppBankID) {
        this.ppBankID = ppBankID;
    }

    public String getPpBillReference() {
        return ppBillReference;
    }

    public void setPpBillReference(String ppBillReference) {
        this.ppBillReference = ppBillReference;
    }

    public String getPpLanguage() {
        return ppLanguage;
    }

    public void setPpLanguage(String ppLanguage) {
        this.ppLanguage = ppLanguage;
    }

    public String getPpMerchantID() {
        return ppMerchantID;
    }

    public void setPpMerchantID(String ppMerchantID) {
        this.ppMerchantID = ppMerchantID;
    }

    public String getPpResponseCode() {
        return ppResponseCode;
    }

    public void setPpResponseCode(String ppResponseCode) {
        this.ppResponseCode = ppResponseCode;
    }

    public String getPpResponseMessage() {
        return ppResponseMessage;
    }

    public void setPpResponseMessage(String ppResponseMessage) {
        this.ppResponseMessage = ppResponseMessage;
    }

    public String getPpRetreivalReferenceNo() {
        return ppRetreivalReferenceNo;
    }

    public void setPpRetreivalReferenceNo(String ppRetreivalReferenceNo) {
        this.ppRetreivalReferenceNo = ppRetreivalReferenceNo;
    }

    public String getPpSecureHash() {
        return ppSecureHash;
    }

    public void setPpSecureHash(String ppSecureHash) {
        this.ppSecureHash = ppSecureHash;
    }

    public String getPpSettlementExpiry() {
        return ppSettlementExpiry;
    }

    public void setPpSettlementExpiry(String ppSettlementExpiry) {
        this.ppSettlementExpiry = ppSettlementExpiry;
    }

    public String getPpSubMerchantId() {
        return ppSubMerchantId;
    }

    public void setPpSubMerchantId(String ppSubMerchantId) {
        this.ppSubMerchantId = ppSubMerchantId;
    }

    public String getPpTxnCurrency() {
        return ppTxnCurrency;
    }

    public void setPpTxnCurrency(String ppTxnCurrency) {
        this.ppTxnCurrency = ppTxnCurrency;
    }

    public String getPpTxnDateTime() {
        return ppTxnDateTime;
    }

    public void setPpTxnDateTime(String ppTxnDateTime) {
        this.ppTxnDateTime = ppTxnDateTime;
    }

    public String getPpTxnRefNo() {
        return ppTxnRefNo;
    }

    public void setPpTxnRefNo(String ppTxnRefNo) {
        this.ppTxnRefNo = ppTxnRefNo;
    }

    public String getPpTxnType() {
        return ppTxnType;
    }

    public void setPpTxnType(String ppTxnType) {
        this.ppTxnType = ppTxnType;
    }

    public String getPpVersion() {
        return ppVersion;
    }

    public void setPpVersion(String ppVersion) {
        this.ppVersion = ppVersion;
    }

    public String getPpmbf1() {
        return ppmbf1;
    }

    public void setPpmbf1(String ppmbf1) {
        this.ppmbf1 = ppmbf1;
    }

    public String getPpmbf2() {
        return ppmbf2;
    }

    public void setPpmbf2(String ppmbf2) {
        this.ppmbf2 = ppmbf2;
    }

    public String getPpmbf3() {
        return ppmbf3;
    }

    public void setPpmbf3(String ppmbf3) {
        this.ppmbf3 = ppmbf3;
    }

    public String getPpmbf4() {
        return ppmbf4;
    }

    public void setPpmbf4(String ppmbf4) {
        this.ppmbf4 = ppmbf4;
    }

    public String getPpmbf5() {
        return ppmbf5;
    }

    public void setPpmbf5(String ppmbf5) {
        this.ppmbf5 = ppmbf5;
    }

    public PaymentsDTO getPayments() {
        return payments;
    }

    public void setPayments(PaymentsDTO payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JazzCashPaymentDTO)) {
            return false;
        }

        JazzCashPaymentDTO jazzCashPaymentDTO = (JazzCashPaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jazzCashPaymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JazzCashPaymentDTO{" +
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
            ", payments=" + getPayments() +
            "}";
    }
}
