package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String addressDefault;

    private LocalDate createDt;

    private LocalDate updateDt;

}
