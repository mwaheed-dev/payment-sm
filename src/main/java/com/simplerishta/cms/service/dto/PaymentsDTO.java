package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.Payments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentsDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionType;

    @NotNull
    private String paymentProvider;

    private Instant transactionTime;

    private Double amount;

    private String paymentType;

    private String txnReferenceNumber;

    private String responseCode;

    private String status;

    private String providerReferenceNumber;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    private UserTariffDTO payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public Instant getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTxnReferenceNumber() {
        return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
        this.txnReferenceNumber = txnReferenceNumber;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProviderReferenceNumber() {
        return providerReferenceNumber;
    }

    public void setProviderReferenceNumber(String providerReferenceNumber) {
        this.providerReferenceNumber = providerReferenceNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserTariffDTO getPayments() {
        return payments;
    }

    public void setPayments(UserTariffDTO payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentsDTO)) {
            return false;
        }

        PaymentsDTO paymentsDTO = (PaymentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsDTO{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", paymentProvider='" + getPaymentProvider() + "'" +
            ", transactionTime='" + getTransactionTime() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", txnReferenceNumber='" + getTxnReferenceNumber() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", providerReferenceNumber='" + getProviderReferenceNumber() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", payments=" + getPayments() +
            "}";
    }
}
