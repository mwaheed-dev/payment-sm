package com.simplerishta.cms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PackageQuotas.
 */
@Entity
@Table(name = "package_quotas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PackageQuotas implements Serializable {

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

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    private Packages packageQuotas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PackageQuotas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfProfileViews() {
        return this.numberOfProfileViews;
    }

    public PackageQuotas numberOfProfileViews(Integer numberOfProfileViews) {
        this.setNumberOfProfileViews(numberOfProfileViews);
        return this;
    }

    public void setNumberOfProfileViews(Integer numberOfProfileViews) {
        this.numberOfProfileViews = numberOfProfileViews;
    }

    public Integer getNumberOfConversations() {
        return this.numberOfConversations;
    }

    public PackageQuotas numberOfConversations(Integer numberOfConversations) {
        this.setNumberOfConversations(numberOfConversations);
        return this;
    }

    public void setNumberOfConversations(Integer numberOfConversations) {
        this.numberOfConversations = numberOfConversations;
    }

    public Integer getNumberOfRequestSent() {
        return this.numberOfRequestSent;
    }

    public PackageQuotas numberOfRequestSent(Integer numberOfRequestSent) {
        this.setNumberOfRequestSent(numberOfRequestSent);
        return this;
    }

    public void setNumberOfRequestSent(Integer numberOfRequestSent) {
        this.numberOfRequestSent = numberOfRequestSent;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PackageQuotas createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PackageQuotas updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Packages getPackageQuotas() {
        return this.packageQuotas;
    }

    public void setPackageQuotas(Packages packages) {
        this.packageQuotas = packages;
    }

    public PackageQuotas packageQuotas(Packages packages) {
        this.setPackageQuotas(packages);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageQuotas)) {
            return false;
        }
        return id != null && id.equals(((PackageQuotas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PackageQuotas{" +
            "id=" + getId() +
            ", numberOfProfileViews=" + getNumberOfProfileViews() +
            ", numberOfConversations=" + getNumberOfConversations() +
            ", numberOfRequestSent=" + getNumberOfRequestSent() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
