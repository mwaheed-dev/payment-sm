package com.simplerishta.cms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Packages.
 */
@Entity
@Table(name = "packages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Packages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Size(max = 30)
    @Column(name = "created_by", length = 30, nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Packages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Packages name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Packages createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Packages createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Packages updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Packages)) {
            return false;
        }
        return id != null && id.equals(((Packages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Packages{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
