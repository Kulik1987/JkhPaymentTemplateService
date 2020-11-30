package ru.kulikovskiy.jkh.jkhpaymenttemplate.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {
    @NotNull
    private String clientId;
    @NotNull
    Integer jkhTemplateId;
    @NotNull
    private String inn;
}
