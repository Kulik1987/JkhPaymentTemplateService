package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrganizationResponse {
    private String guid;
    private String address;
    private String cite;
    private String phone;
    private String shortName;
    private String fullName;
}
