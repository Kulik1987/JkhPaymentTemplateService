package ru.kulikovskiy.jkh.jkhpaymenttemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyJkhTemplateRequestDto {
    private String clientId;
    private Integer jkhTemplateId;
}
