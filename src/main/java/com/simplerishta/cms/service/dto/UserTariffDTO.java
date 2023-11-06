package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.UserTariff} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTariffDTO implements Serializable {

    private Long id;

    private TariffDTO tariff;

    private PaymentsDTO payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TariffDTO getTariff() {
        return tariff;
    }

    public void setTariff(TariffDTO tariff) {
        this.tariff = tariff;
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
        if (!(o instanceof UserTariffDTO)) {
            return false;
        }

        UserTariffDTO userTariffDTO = (UserTariffDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTariffDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTariffDTO{" +
            "id=" + getId() +
            ", tariff=" + getTariff() +
            ", payments=" + getPayments() +
            "}";
    }
}
