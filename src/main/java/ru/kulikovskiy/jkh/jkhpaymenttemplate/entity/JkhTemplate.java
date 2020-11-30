package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jkh_template")
@Data
public class JkhTemplate {
    @Id
    @SequenceGenerator(name = "jkh_template_id", sequenceName = "jkh_template_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jkh_template_id")
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_template")
    private String nameTemplate;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "house_code")
    private String houseCode;
    @Column(name = "template_json")
    private String templateJson;
    @Column(name = "is_active")
    private String isActive;
}
