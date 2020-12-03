package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.JkhPaymentTemplateService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.config.DaDataConfig;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.AddJkhTemplateRequestDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.JkhTemplateResponseDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.JkhTemplatesClientResponseDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.JkhTemplateMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.JkhTemplatesClientMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.OrganizationsMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.ClientRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.HouseRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.JkhTemplateRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.OrganizationRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.JkhTemplateService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.JkhTemplateServiceImpl;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.*;

import static javassist.bytecode.AccessFlag.of;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.data.mapping.Alias.ofNullable;

@RunWith(SpringRunner.class)
public class JKhTempateServiceTest {

    @TestConfiguration
    static class JKhTempateServiceTestContextConfigaration {
        @Bean
        public JkhTemplateService jkhPaymentTemplateService() {
            return new JkhTemplateServiceImpl();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public JkhTemplatesClientMapper jkhTemplatesClientMapper(){
            return new JkhTemplatesClientMapper();
        }

        @Bean
        public OrganizationsMapper organizationsMapper() {
            return new OrganizationsMapper();
        }

        @Bean
        public JkhTemplateMapper jkhTemplateMapper() {
            return new JkhTemplateMapper();
        }


    }
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private HouseRepository houseRepository;
    @MockBean
    private JkhTemplateRepository jkhTemplateRepository;
    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private RestTemplate daDataResttemplate;
    @MockBean
    private DaDataConfig daDataConfig;

    @Autowired
    private JkhTemplatesClientMapper jkhTemplatesClientMapper;
    @Autowired
    private JkhTemplateMapper jkhTemplateMapper;
    @Autowired
    private OrganizationsMapper organizationsMapper;

    @Autowired
    private JkhTemplateService jkhTemplateService;

    private JkhTemplate jkhTemplate;
    private JkhTemplateResponseDto jkhTemplateResponseDto;
    private Client client;
    private AddressData addressData = new AddressData();
    private DaData daData = new DaData();
    private DaDataResponse daDataResponse = new DaDataResponse();
    private Set<Organization> organizations = new HashSet<>();



    @Before
    public void setup() throws NotFoundException {
        Organization organizationFirst = new Organization("1", "Москва, Ленина 3", "www.1.ru", "89211234567", "UAZ", "UAX JKH",                "1", "3425425", "1", "{id:1}", LocalDate.now(), LocalDate.of(2020, 11, 1), new HashSet<House>());

        Organization organizationTwice = new Organization("9c4862c1-eced-45bd-9b59-1ef440b39774", "Москва, Ленина 3", "www.1.ru", "89211234567", "UAZ", "UAX JKH",
                "1", "3425425", "1", "{id:1}", LocalDate.now(), LocalDate.of(2020, 11, 1), new HashSet<House>());

        organizations.add(organizationFirst);
        organizations.add(organizationTwice);

        jkhTemplate = new JkhTemplate("my", "2", "house_code3", "[\n" +
                "\t{\n" +
                "\t\t\"guid\": \"9c4862c1-eced-45bd-9b59-1ef440b39774\",\n" +
                "\t\t\"address\": null,\n" +
                "\t\t\"cite\": null,\n" +
                "\t\t\"phone\": \"+7(495)5975568\",\n" +
                "\t\t\"shortName\": \"АО \\\"МОСОБЛГАЗ\\\"\",\n" +
                "\t\t\"fullName\": \"АКЦИОНЕРНОЕ ОБЩЕСТВО \\\"МОСОБЛГАЗ\\\"\",\n" +
                "\t\t\"inn\": null,\n" +
                "\t\t\"kpp\": null,\n" +
                "\t\t\"ogrn\": null,\n" +
                "\t\t\"orgResponse\": \"OrganizationJkh(guid=9c4862c1-eced-45bd-9b59-1ef440b39774, createDate=null, lastEventDate=null, fullName=АКЦИОНЕРНОЕ ОБЩЕСТВО \\\"МОСОБЛГАЗ\\\", shortName=АО \\\"МОСОБЛГАЗ\\\", chiefLastName=null, chiefFirstName=null, chiefMiddleName=null, orgAddress=null, phone=+7(495)5975568, orgEmail=null, url=null, organizationType=null, organizationJkhRoles=null, factualAddress=FactualAddress(region=null, area=null, city=null, settlement=null, planningStructureElement=null, street=null, house=null, additionalInfo=null, houseNumber=null, buildingNumber=null, structNumber=null, formattedAddress=Московская обл, г. Одинцово, д. Раздоры, д. 1), status=null, inn=null, kpp=null, ogrn=null)\",\n" +
                "\t\t\"createDt\": null,\n" +
                "\t\t\"updateDt\": null\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"guid\": \"ca308240-3660-4de4-9448-58a8278cd02a\",\n" +
                "\t\t\"address\": null,\n" +
                "\t\t\"cite\": \"www.tsk-mosenergo.ru\",\n" +
                "\t\t\"phone\": \"+7 (495) 225-14-33\",\n" +
                "\t\t\"shortName\": \"Химкинский филиал ООО \\\"ТСК Мосэнерго\\\"\",\n" +
                "\t\t\"fullName\": \"Химкинский филиал Общества с ограниченной ответственностью \\\"Теплоснабжающая компания Мосэнерго\\\"\",\n" +
                "\t\t\"inn\": null,\n" +
                "\t\t\"kpp\": null,\n" +
                "\t\t\"ogrn\": null,\n" +
                "\t\t\"orgResponse\": \"OrganizationJkh(guid=ca308240-3660-4de4-9448-58a8278cd02a, createDate=null, lastEventDate=null, fullName=Химкинский филиал Общества с ограниченной ответственностью \\\"Теплоснабжающая компания Мосэнерго\\\", shortName=Химкинский филиал ООО \\\"ТСК Мосэнерго\\\", chiefLastName=null, chiefFirstName=null, chiefMiddleName=null, orgAddress=null, phone=+7 (495) 225-14-33, orgEmail=null, url=www.tsk-mosenergo.ru, organizationType=null, organizationJkhRoles=null, factualAddress=FactualAddress(region=null, area=null, city=null, settlement=null, planningStructureElement=null, street=null, house=null, additionalInfo=null, houseNumber=null, buildingNumber=null, structNumber=null, formattedAddress=Московская обл, г. Химки, ш. Нагорное, д. 6), status=null, inn=null, kpp=null, ogrn=null)\",\n" +
                "\t\t\"createDt\": null,\n" +
                "\t\t\"updateDt\": null\n" +
                "\t}\n" +
                "]", "1");
        client = new Client();
        client.setId("2");
        client.setFirstName("Со");
        client.setLastName("Ким");
        client.setAddressDefault("Москва, Ленина 3");

        addressData.setHouseFiasId("9c4862c1-eced-45bd-9b59-1ef440b39774");
        daData.setData(addressData);
        daDataResponse.setDaData(Arrays.asList(daData));

        Mockito.when(jkhTemplateRepository.findAllByClientId(jkhTemplate.getClientId())).thenReturn(new ArrayList<>(Arrays.asList(jkhTemplate)));
        Mockito.when(clientRepository.findById("2")).thenReturn(Optional.ofNullable(client));
        Mockito.when(daDataConfig.getUrl()).thenReturn("http://suggestions.dadata.ru");
        Mockito.when(daDataResttemplate.exchange(anyString(), any(),  any(), (Class<Object>) any())).thenReturn(new ResponseEntity<>(daDataResponse, HttpStatus.OK));
        Mockito.when(jkhTemplateRepository.findByClientIdAndHouseCodeAndIsActiveNotNull(client.getId(), "9c4862c1-eced-45bd-9b59-1ef440b39774")).thenReturn(jkhTemplate);
        Mockito.when(houseRepository.getByOrganizationsHouse("9c4862c1-eced-45bd-9b59-1ef440b39774")).thenReturn(organizations);

    }

    @Test
    public void checkGetJkhTemplates() throws NotFoundException {
        List<JkhTemplatesClientResponseDto> jkhTemplateList = jkhTemplateService.getJkhTemplatesList("2");
        Assert.assertEquals(1, jkhTemplateList.size());
        Assert.assertEquals("2", jkhTemplateList.get(0).getClientId());
        Assert.assertEquals("my", jkhTemplateList.get(0).getNameTemplate());
    }

    @Test
    public void checkAddJkhTemplate() {
        AddJkhTemplateRequestDto jkhTemplateRequestDto = new AddJkhTemplateRequestDto();
        jkhTemplateRequestDto.setClientId("2");
        jkhTemplateRequestDto.setNameTemplate("my");

        jkhTemplateService.addJkhTemplate(jkhTemplateRequestDto);

        Assert.assertEquals("2", jkhTemplate.getClientId());
        Assert.assertEquals("my", jkhTemplate.getNameTemplate());
        Assert.assertEquals("9c4862c1-eced-45bd-9b59-1ef440b39774", jkhTemplate.getHouseCode());
    }
}
