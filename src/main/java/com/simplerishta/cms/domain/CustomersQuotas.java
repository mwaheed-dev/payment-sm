package com.simplerishta.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomersQuotas.
 */
@Entity
@Table(name = "customers_quotas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomersQuotas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_profile_views")
    private Integer numberOfProfileViews;

    @Column(name = "number_of_conversations")
    private Integer numberOfConversations;

    @Column(name = "number_of_request_sent")
    private Integer numberOfRequestSent;

    @Column(name = "free_users")
    private Integer freeUsers;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tariff" }, allowSetters = true)
    private CustomerPackages customerQuotas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomersQuotas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfProfileViews() {
        return this.numberOfProfileViews;
    }

    public CustomersQuotas numberOfProfileViews(Integer numberOfProfileViews) {
        this.setNumberOfProfileViews(numberOfProfileViews);
        return this;
    }

    public void setNumberOfProfileViews(Integer numberOfProfileViews) {
        this.numberOfProfileViews = numberOfProfileViews;
    }

    public Integer getNumberOfConversations() {
        return this.numberOfConversations;
    }

    public CustomersQuotas numberOfConversations(Integer numberOfConversations) {
        this.setNumberOfConversations(numberOfConversations);
        return this;
    }

    public void setNumberOfConversations(Integer numberOfConversations) {
        this.numberOfConversations = numberOfConversations;
    }

    public Integer getNumberOfRequestSent() {
        return this.numberOfRequestSent;
    }

    public CustomersQuotas numberOfRequestSent(Integer numberOfRequestSent) {
        this.setNumberOfRequestSent(numberOfRequestSent);
        return this;
    }

    public void setNumberOfRequestSent(Integer numberOfRequestSent) {
        this.numberOfRequestSent = numberOfRequestSent;
    }

    public Integer getFreeUsers() {
        return this.freeUsers;
    }

    public CustomersQuotas freeUsers(Integer freeUsers) {
        this.setFreeUsers(freeUsers);
        return this;
    }

    public void setFreeUsers(Integer freeUsers) {
        this.freeUsers = freeUsers;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public CustomersQuotas createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public CustomersQuotas updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CustomerPackages getCustomerQuotas() {
        return this.customerQuotas;
    }

    public void setCustomerQuotas(CustomerPackages customerPackages) {
        this.customerQuotas = customerPackages;
    }

    public CustomersQuotas customerQuotas(CustomerPackages customerPackages) {
        this.setCustomerQuotas(customerPackages);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersQuotas)) {
            return false;
        }
        return id != null && id.equals(((CustomersQuotas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersQuotas{" +
            "id=" + getId() +
            ", numberOfProfileViews=" + getNumberOfProfileViews() +
            ", numberOfConversations=" + getNumberOfConversations() +
            ", numberOfRequestSent=" + getNumberOfRequestSent() +
            ", freeUsers=" + getFreeUsers() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
