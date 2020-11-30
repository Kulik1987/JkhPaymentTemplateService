package ru.kulikovskiy.jkh.jkhpaymenttemplate.dto;

import lombok.Builder;
import lombok.Data;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;

import java.util.List;

@Data
@Builder
public class JkhTemplateResponseDto {
    private String nameTemplate;
    private String clientId;
    private String fullAddress;
    private List<Organization> organizations;
}
