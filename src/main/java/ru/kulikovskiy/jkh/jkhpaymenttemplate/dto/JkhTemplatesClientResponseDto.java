package ru.kulikovskiy.jkh.jkhpaymenttemplate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JkhTemplatesClientResponseDto {
    private String clientId;
    private Integer jkhTemplateId;
    private String nameTemplate;
}
