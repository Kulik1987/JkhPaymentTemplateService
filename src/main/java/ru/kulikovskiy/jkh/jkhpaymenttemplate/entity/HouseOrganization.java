package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "house_organization")
@AllArgsConstructor
@NoArgsConstructor
public class HouseOrganization implements Serializable {
    @EmbeddedId
    private HouseOrganizationIdentity houseOrganizationIdentity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseOrganization)) return false;
        HouseOrganization that = (HouseOrganization) o;
        return Objects.equals(houseOrganizationIdentity, that.houseOrganizationIdentity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseOrganizationIdentity);
    }
}
