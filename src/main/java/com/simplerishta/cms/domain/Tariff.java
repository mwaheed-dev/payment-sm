package com.simplerishta.cms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tariff.
 */
@Entity
@Table(name = "tariff")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tariff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "tariff_code", length = 150, nullable = false)
    private String tariffCode;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "duration")
    private Integer duration;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    private Packages packages;

    @ManyToOne
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tariff id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTariffCode() {
        return this.tariffCode;
    }

    public Tariff tariffCode(String tariffCode) {
        this.setTariffCode(tariffCode);
        return this;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public Double getPrice() {
        return this.price;
    }

    public Tariff price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Tariff duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Tariff createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Tariff updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Packages getPackages() {
        return this.packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    public Tariff packages(Packages packages) {
        this.setPackages(packages);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Tariff country(Country country) {
        this.setCountry(country);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tariff)) {
            return false;
        }
        return id != null && id.equals(((Tariff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tariff{" +
            "id=" + getId() +
            ", tariffCode='" + getTariffCode() + "'" +
            ", price=" + getPrice() +
            ", duration=" + getDuration() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
