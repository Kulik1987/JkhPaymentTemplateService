package ru.kulikovskiy.jkh.jkhpaymenttemplate.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AddJkhTemplateRequestDto {
    @NotNull
    private String clientId;
    private String fullAddress;
    private String nameTemplate;
}
