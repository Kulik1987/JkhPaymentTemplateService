package ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.JkhTemplateResponseDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.House;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.HouseRepository;

import java.util.List;

@Component
public class JkhTemplateMapper {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public JkhTemplateResponseDto jkhTemplateDtoFromEntity(JkhTemplate jkhTemplate) {
        House house = houseRepository.getByHouseGuid(jkhTemplate.getHouseCode());
        List<Organization> organizations = null;
        try {
            organizations = objectMapper.readValue(jkhTemplate.getTemplateJson(), List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return JkhTemplateResponseDto.builder().clientId(jkhTemplate.getClientId())
                .fullAddress(house.getHouseFormattedAddress())
                .organizations(organizations).build();
    }

}
