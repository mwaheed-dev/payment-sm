package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.PackageQuotas} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PackageQuotasDTO implements Serializable {

    private Long id;

    private Integer numberOfProfileViews;

    private Integer numberOfConversations;

    private Integer numberOfRequestSent;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    private PackagesDTO customerQuotas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfProfileViews() {
        return numberOfProfileViews;
    }

    public void setNumberOfProfileViews(Integer numberOfProfileViews) {
        this.numberOfProfileViews = numberOfProfileViews;
    }

    public Integer getNumberOfConversations() {
        return numberOfConversations;
    }

    public void setNumberOfConversations(Integer numberOfConversations) {
        this.numberOfConversations = numberOfConversations;
    }

    public Integer getNumberOfRequestSent() {
        return numberOfRequestSent;
    }

    public void setNumberOfRequestSent(Integer numberOfRequestSent) {
        this.numberOfRequestSent = numberOfRequestSent;
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

    public PackagesDTO getCustomerQuotas() {
        return customerQuotas;
    }

    public void setCustomerQuotas(PackagesDTO customerQuotas) {
        this.customerQuotas = customerQuotas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageQuotasDTO)) {
            return false;
        }

        PackageQuotasDTO packageQuotasDTO = (PackageQuotasDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, packageQuotasDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PackageQuotasDTO{" +
            "id=" + getId() +
            ", numberOfProfileViews=" + getNumberOfProfileViews() +
            ", numberOfConversations=" + getNumberOfConversations() +
            ", numberOfRequestSent=" + getNumberOfRequestSent() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", customerQuotas=" + getCustomerQuotas() +
            "}";
    }
}
