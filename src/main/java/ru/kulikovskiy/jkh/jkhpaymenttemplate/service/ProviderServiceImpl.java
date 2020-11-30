package ru.kulikovskiy.jkh.jkhpaymenttemplate.service;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.ServiceRequestDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.OrganizationsMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.JkhTemplateRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.OrganizationRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
@Slf4j
public class ProviderServiceImpl implements ProviderService {
    private final OrganizationRepository organizationRepository;
    private final JkhTemplateService jkhTemplateService;
    private final OrganizationsMapper organizationsMapper;
    private final JkhTemplateRepository jkhTemplateRepository;

    @Override
    public void addProviderJkhTemplate(ServiceRequestDto serviceRequestDto) throws NotFoundException {
        JkhTemplate jkhTemplate = jkhTemplateService.getJkhTemplateModifyProvider(serviceRequestDto.getClientId(), serviceRequestDto.getJkhTemplateId());

        String inn = serviceRequestDto.getInn();
        Organization organization = ofNullable(organizationRepository.getByInn(inn)).orElseThrow(() ->new NotFoundException("Organization not found inn=" + inn));

        List<Organization> organizationList = organizationsMapper.organizationsFromJson(jkhTemplate.getTemplateJson());
        if (!organizationList.contains(organization)) {
            organizationList.add(organization);
        }
        jkhTemplate.setTemplateJson(organizationsMapper.jsonOrganizationsFromEntity(organizationList));
        jkhTemplateRepository.save(jkhTemplate);
    }

    @Override
    public void deleteServiceJkhTemplate(ServiceRequestDto serviceRequestDto) throws NotFoundException {
        JkhTemplate jkhTemplate = jkhTemplateService.getJkhTemplateModifyProvider(serviceRequestDto.getClientId(), serviceRequestDto.getJkhTemplateId());

        String inn = serviceRequestDto.getInn();
        Organization organization = ofNullable(organizationRepository.getByInn(inn)).orElseThrow(() ->new NotFoundException("Organization not found inn=" + inn));

        List<Organization> organizationList = organizationsMapper.organizationsFromJson(jkhTemplate.getTemplateJson());
        if (organizationList.contains(organization)) {
            organizationList.remove(organization);
            jkhTemplate.setTemplateJson(organizationsMapper.jsonOrganizationsFromEntity(organizationList));
            jkhTemplateRepository.save(jkhTemplate);
        }
    }
}
