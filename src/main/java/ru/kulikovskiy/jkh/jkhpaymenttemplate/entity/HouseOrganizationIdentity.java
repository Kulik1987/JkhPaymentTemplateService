package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HouseOrganizationIdentity implements Serializable {
    @NotNull
    @Column(name = "houses_guid")
    private String houses_guid;
    @NotNull
    @Column(name = "orgs_guid")
    private String orgsGuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseOrganizationIdentity that = (HouseOrganizationIdentity) o;
        return houses_guid.equals(that.houses_guid) &&
                orgsGuid.equals(that.orgsGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houses_guid, orgsGuid);
    }
}
