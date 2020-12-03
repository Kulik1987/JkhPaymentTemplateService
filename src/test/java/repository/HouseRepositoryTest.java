package repository;

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
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.HouseRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JkhPaymentTemplateService.class)
public class HouseRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void checkGetByHouseGuid() throws Exception {
        Set<Organization> organizationSet = new HashSet<>();
        organizationSet.add(new Organization("1", "Москва ул Ленина 21", "www.1.ru", "89251231221", "Орг"));
        organizationSet.add(new Organization("2", "Питер ул Ленина 22", "www.10.ru", "29251231200", "Орг1"));

        this.entityManager.persist(new House("1", "str1", "12", "421", "11", "3", "test", "test", "12454", "test", "{guid : 1}", new Date(), new Date(), organizationSet));

        House house = this.houseRepository.getByHouseGuid("1");
        assertThat(house.getHouseGuid()).isEqualTo("1");
        assertThat(house.getStreetGuid()).isEqualTo("str1");
        assertThat(house.getHouseNumber()).isEqualTo("12");
        assertThat(house.getOrganizationsHouse()).isEqualTo(organizationSet);
    }
}