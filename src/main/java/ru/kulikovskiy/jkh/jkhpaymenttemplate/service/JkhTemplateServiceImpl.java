package ru.kulikovskiy.jkh.jkhpaymenttemplate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.config.DaDataConfig;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.JkhTemplateMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.JkhTemplatesClientMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.OrganizationsMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.ClientRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.HouseRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.JkhTemplateRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.OrganizationRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class JkhTemplateServiceImpl implements JkhTemplateService {
    private final ClientRepository clientRepository;
    private final RestTemplate daDataResttemplate;
    private final DaDataConfig daDataConfig;
    private final HouseRepository houseRepository;
    private final ObjectMapper objectMapper;
    private final JkhTemplateRepository jkhTemplateRepository;
    private final JkhTemplateMapper jkhTemplateMapper;
    private final OrganizationsMapper organizationsMapper;
    private final OrganizationRepository organizationRepository;
    private final JkhTemplatesClientMapper jkhTemplatesClientMapper;


    public JkhTemplateServiceImpl(ClientRepository clientRepository, RestTemplate daDataResttemplate, DaDataConfig daDataConfig, HouseRepository houseRepository, ObjectMapper objectMapper, JkhTemplateRepository jkhTemplateRepository, JkhTemplateMapper jkhTemplateMapper, OrganizationsMapper organizationsMapper, OrganizationRepository organizationRepository, JkhTemplatesClientMapper jkhTemplatesClientMapper) {
        this.clientRepository = clientRepository;
        this.daDataResttemplate = daDataResttemplate;
        this.daDataConfig = daDataConfig;
        this.houseRepository = houseRepository;
        this.objectMapper = objectMapper;
        this.jkhTemplateRepository = jkhTemplateRepository;
        this.jkhTemplateMapper = jkhTemplateMapper;
        this.organizationsMapper = organizationsMapper;
        this.organizationRepository = organizationRepository;
        this.jkhTemplatesClientMapper = jkhTemplatesClientMapper;
    }

    @Override
    public List<JkhTemplatesClientResponseDto> getJkhTemplatesList(String clientId) throws NotFoundException {
        List<JkhTemplate> jkhTemplatesList = ofNullable(jkhTemplateRepository.findAllByClientId(clientId)).orElseThrow(() -> new NotFoundException("JkhTemplate not found"));
        List<JkhTemplatesClientResponseDto> jkhTemplateListDto = new ArrayList<>();
        jkhTemplatesList.forEach(jkhTemplate -> jkhTemplateListDto.add(jkhTemplatesClientMapper.jkhTemplatesClientDtoFromEntity(jkhTemplate)));
        return jkhTemplateListDto;
    }

    @Override
    public void addJkhTemplate(AddJkhTemplateRequestDto request) {
        Client client = clientRepository.findById(request.getClientId()).get();
        String houseFiasId;

        if ((request.getFullAddress() == null) || (request.getFullAddress().isEmpty())) {
            houseFiasId = getHouseFiasId(client.getAddressDefault());
        } else {
            houseFiasId = getHouseFiasId(request.getFullAddress());
        }

        JkhTemplate jkhTemplate = ofNullable(jkhTemplateRepository.findByClientIdAndHouseCodeAndIsActiveNotNull(request.getClientId(), houseFiasId)).orElse(new JkhTemplate());
        ofNullable(client.getId()).ifPresent(jkhTemplate::setClientId);
        ofNullable(houseFiasId).ifPresent(jkhTemplate::setHouseCode);
        jkhTemplate.setIsActive("1");
        ofNullable(request.getNameTemplate()).ifPresent(jkhTemplate::setNameTemplate);
        getJsonOrganizations(houseFiasId, jkhTemplate);

        jkhTemplateRepository.save(jkhTemplate);
    }

    @Override
    public JkhTemplateResponseDto getJkhTemplate(String clientId, String fullAddress) throws NotFoundException {
        String houseFiasId = getHouseFiasId(fullAddress);
        JkhTemplate jkhTemplate = ofNullable(jkhTemplateRepository.findByClientIdAndHouseCodeAndIsActiveNotNull(clientId, houseFiasId)).orElseThrow(() -> new NotFoundException("JkhTemplate not found"));
        return jkhTemplateMapper.jkhTemplateDtoFromEntity(jkhTemplate);
    }

    @Override
    public void updateJkhTemplate(ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto) throws NotFoundException {
        Integer id = modifyJkhTemplateRequestDto.getJkhTemplateId();
        JkhTemplate jkhTemplate = jkhTemplateRepository.findByIdAndIsActiveNotNull(id).orElseThrow(() -> new NotFoundException("JkhTempate not found with id=" + id));
        Set<Organization> organizationsModify = updateOrganizationsJkhTemplate(jkhTemplate);

        String json = organizationsMapper.jsonOrganizationsFromEntity(organizationsModify);
        jkhTemplate.setTemplateJson(json);

        jkhTemplateRepository.save(jkhTemplate);
    }

    @Override
    public void deleteJkhTemplate(ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto) throws NotFoundException {
        Integer jkhTemplateId = modifyJkhTemplateRequestDto.getJkhTemplateId();
        JkhTemplate jkhTemplate = ofNullable(jkhTemplateRepository.findByIdAndIsActiveNotNull(jkhTemplateId)).orElseThrow(() -> new NotFoundException("JkhTemplate not found with id=" + String.valueOf(jkhTemplateId))).get();
        jkhTemplate.setIsActive("");
        jkhTemplateRepository.save(jkhTemplate);
    }

    @Override
    public JkhTemplate getJkhTemplateModifyProvider(String clientId, Integer jkhTemplateId) throws NotFoundException {
        return ofNullable(jkhTemplateRepository.findByIdAndIsActiveNotNull(jkhTemplateId)).orElseThrow(() -> new NotFoundException("JkhTemplate not found with id=" + String.valueOf(jkhTemplateId))).get();
    }

    private Set<Organization> updateOrganizationsJkhTemplate(JkhTemplate jkhTemplate) throws NotFoundException {
        House house = houseRepository.findById(jkhTemplate.getHouseCode()).orElseThrow(() -> new NotFoundException("House not found with guid=" + jkhTemplate.getHouseCode()));

        List<Organization> organizations = organizationsMapper.organizationsFromJson(jkhTemplate.getTemplateJson());
        List<Organization> actualOrganizations = new ArrayList<>();
        organizations.stream().
                map(org -> organizationRepository.findById(org.getGuid())).
                filter(org -> org != null).forEach(organization -> actualOrganizations.add(organization.get()));

        Set<Organization> organizationsModify = new HashSet<>();
        if (house.getOrganizationsHouse().isEmpty()) {
            organizations.stream().map(org -> organizationsModify.add(org));
        } else {
            organizationsModify.addAll(house.getOrganizationsHouse());
            organizationsModify.addAll(actualOrganizations);
        }
        return organizationsModify;
    }

    private void getJsonOrganizations(String houseFiasId, JkhTemplate jkhTemplate) {
        Set<Organization> organizationSet = houseRepository.getByOrganizationsHouse(houseFiasId);
        if (organizationSet != null) {
            String json = organizationsMapper.jsonOrganizationsFromEntity(organizationSet);
            jkhTemplate.setTemplateJson(json);
        }
    }

    private String getHouseFiasId(String address) {
        DaDataRequest daDataRequest = new DaDataRequest(address, 1);
        return getAddressFromDaData(daDataRequest).getHouseFiasId();
    }

    private AddressData getAddressFromDaData(DaDataRequest daDataRequest) {
        String url = UriComponentsBuilder.newInstance().uri(URI.create(daDataConfig.getUrl())).path("/suggestions/api/4_1/rs/suggest/address").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.set(HttpHeaders.AUTHORIZATION, "Token 0b4197e10d738bee613009bb6ed1d58cf6427fee");

        HttpEntity<DaDataRequest> request = new HttpEntity(daDataRequest, headers);

        ResponseEntity<DaDataResponse> response = daDataResttemplate.exchange(url, HttpMethod.POST, request, DaDataResponse.class);
        return response.getBody().getDaData().get(0).getData();
    }
}
