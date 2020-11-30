package ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper;

import org.springframework.stereotype.Component;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.JkhTemplatesClientResponseDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;

@Component
public class JkhTemplatesClientMapper {

    public JkhTemplatesClientResponseDto jkhTemplatesClientDtoFromEntity(JkhTemplate jkhTemplate) {
        return JkhTemplatesClientResponseDto.builder().clientId(jkhTemplate.getClientId())
                .nameTemplate(jkhTemplate.getNameTemplate())
                .jkhTemplateId(jkhTemplate.getId()).build();

    }
}
