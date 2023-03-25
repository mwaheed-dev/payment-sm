package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.Tariff} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TariffDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String tariffCode;

    @NotNull
    private Double price;

    private Integer duration;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    private PackagesDTO tariff;

    private CountryDTO country;

    private UserTariffDTO tariff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public PackagesDTO getTariff() {
        return tariff;
    }

    public void setTariff(PackagesDTO tariff) {
        this.tariff = tariff;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public UserTariffDTO getTariff() {
        return tariff;
    }

    public void setTariff(UserTariffDTO tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TariffDTO)) {
            return false;
        }

        TariffDTO tariffDTO = (TariffDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tariffDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TariffDTO{" +
            "id=" + getId() +
            ", tariffCode='" + getTariffCode() + "'" +
            ", price=" + getPrice() +
            ", duration=" + getDuration() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", tariff=" + getTariff() +
            ", country=" + getCountry() +
            ", tariff=" + getTariff() +
            "}";
    }
}
