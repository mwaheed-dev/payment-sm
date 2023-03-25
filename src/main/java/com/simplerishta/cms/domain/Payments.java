package com.simplerishta.cms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payments.
 */
@Entity
@Table(name = "payments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @NotNull
    @Column(name = "payment_provider", nullable = false)
    private String paymentProvider;

    @Column(name = "transaction_time")
    private Instant transactionTime;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "txn_reference_number")
    private String txnReferenceNumber;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "status")
    private String status;

    @Column(name = "provider_reference_number")
    private String providerReferenceNumber;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    private UserTariff payments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public Payments transactionType(String transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentProvider() {
        return this.paymentProvider;
    }

    public Payments paymentProvider(String paymentProvider) {
        this.setPaymentProvider(paymentProvider);
        return this;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public Instant getTransactionTime() {
        return this.transactionTime;
    }

    public Payments transactionTime(Instant transactionTime) {
        this.setTransactionTime(transactionTime);
        return this;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Payments amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public Payments paymentType(String paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTxnReferenceNumber() {
        return this.txnReferenceNumber;
    }

    public Payments txnReferenceNumber(String txnReferenceNumber) {
        this.setTxnReferenceNumber(txnReferenceNumber);
        return this;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
        this.txnReferenceNumber = txnReferenceNumber;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public Payments responseCode(String responseCode) {
        this.setResponseCode(responseCode);
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return this.status;
    }

    public Payments status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProviderReferenceNumber() {
        return this.providerReferenceNumber;
    }

    public Payments providerReferenceNumber(String providerReferenceNumber) {
        this.setProviderReferenceNumber(providerReferenceNumber);
        return this;
    }

    public void setProviderReferenceNumber(String providerReferenceNumber) {
        this.providerReferenceNumber = providerReferenceNumber;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Payments createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Payments updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserTariff getPayments() {
        return this.payments;
    }

    public void setPayments(UserTariff userTariff) {
        this.payments = userTariff;
    }

    public Payments payments(UserTariff userTariff) {
        this.setPayments(userTariff);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payments)) {
            return false;
        }
        return id != null && id.equals(((Payments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payments{" +
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
            "}";
    }
}
