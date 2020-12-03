package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "organization")
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    @Column(name = "guid")
    private String guid;
    private String address;
    private String cite;
    private String phone;
    private String shortName;
    private String fullName;
    private String inn;
    private String kpp;
    private String ogrn;
    private String orgResponse;
    private LocalDate createDt;
    private LocalDate updateDt;

    @JsonIgnore
    @ManyToMany(mappedBy = "organizationsHouse")
    Set<House> houses;

    public Organization(String guid, String address, String cite, String phone, String shortName) {
        this.guid = guid;
        this.address = address;
        this.cite = cite;
        this.phone = phone;
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(guid, that.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
