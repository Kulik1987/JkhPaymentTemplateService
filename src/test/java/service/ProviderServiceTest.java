package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.ServiceRequestDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.House;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper.OrganizationsMapper;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.JkhTemplateRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.OrganizationRepository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.JkhTemplateService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.ProviderService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.ProviderServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;

@RunWith(SpringRunner.class)
public class ProviderServiceTest {
    @TestConfiguration
    static class ProviderServiceTestContextConfigaration {
        @Bean
        public ProviderService providerService() {
            return new ProviderServiceImpl();
        }

        @Bean
        public OrganizationsMapper organizationsMapper() {
            return new OrganizationsMapper();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private ProviderService providerService;
    @MockBean
    private OrganizationRepository organizationRepository;
    @MockBean
    private JkhTemplateService jkhTemplateService;
    @MockBean
    private JkhTemplateRepository jkhTemplateRepository;

    private JkhTemplate jkhTemplate;

    @Before
    public void setup() throws NotFoundException {
        Organization organizationNew = new Organization("1", "Москва, Ленина 3", "www.1.ru", "89211234567", "UAZ", "UAX JKH",
                "1", "3425425", "1", "{id:1}", LocalDate.now(), LocalDate.of(2020, 11, 1), new HashSet<House>());

        Organization organizationExist = new Organization("9c4862c1-eced-45bd-9b59-1ef440b39774", "Москва, Ленина 3", "www.1.ru", "89211234567", "UAZ", "UAX JKH",
                "1", "3425425", "1", "{id:1}", LocalDate.now(), LocalDate.of(2020, 11, 1), new HashSet<House>());

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

        Mockito.when(organizationRepository.getByInn(organizationNew.getInn())).thenReturn(organizationExist);
        Mockito.when(jkhTemplateService.getJkhTemplateModifyProvider("2", 1)).thenReturn(jkhTemplate);
    }

    @Test
    public void checkAddProviderJkhTemplate() throws NotFoundException {
        ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
        serviceRequestDto.setClientId("2");
        serviceRequestDto.setInn("1");
        serviceRequestDto.setJkhTemplateId(1);

        providerService.addProviderJkhTemplate(serviceRequestDto);
        Assert.assertEquals(3, StringUtils.countMatches(jkhTemplate.getTemplateJson(), "\"guid\""));
    }

    @Test
    public void checkDeleteServiceJkhTemplate() throws NotFoundException {
        ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
        serviceRequestDto.setClientId("2");
        serviceRequestDto.setInn("1");
        serviceRequestDto.setJkhTemplateId(1);

        providerService.deleteServiceJkhTemplate(serviceRequestDto);

        Assert.assertEquals(1, StringUtils.countMatches(jkhTemplate.getTemplateJson(), "\"guid\""));
    }
}
