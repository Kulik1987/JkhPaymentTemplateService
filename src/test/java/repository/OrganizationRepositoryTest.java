package repository;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.JkhPaymentTemplateService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.House;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.OrganizationRepository;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JkhPaymentTemplateService.class)
public class OrganizationRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository organizationRepository;
    private Organization organization;

    @Before
    public void initObjects() {
        organization = new Organization("1", "Москва, Ленина 3", "www.1.ru", "89211234567", "UAZ", "UAX JKH",
                "1", "3425425", "1", "{id:1}", LocalDate.now(), LocalDate.of(2020, 11, 1), new HashSet<House>());
    }

    @Test
    public void checkGetByInn() {
        this.entityManager.persist(organization);
        Organization organizationResponse = this.organizationRepository.getByInn("1");
        assertThat(organizationResponse.getGuid()).isEqualTo("1");
        assertThat(organizationResponse.getAddress()).isEqualTo("Москва, Ленина 3");
        assertThat(organizationResponse.getCite()).isEqualTo("www.1.ru");
        assertThat(organizationResponse.getPhone()).isEqualTo("89211234567");
        assertThat(organizationResponse.getShortName()).isEqualTo("UAZ");
        assertThat(organizationResponse.getFullName()).isEqualTo("UAX JKH");
        assertThat(organizationResponse.getInn()).isEqualTo("1");
        assertThat(organizationResponse.getKpp()).isEqualTo("3425425");
        assertThat(organizationResponse.getOgrn()).isEqualTo("1");
        assertThat(organizationResponse.getOrgResponse()).isEqualTo("{id:1}");
    }
}
