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
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Client;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.ClientRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JkhPaymentTemplateService.class)
public class ClientRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void checkFindById() throws Exception {
        this.entityManager.persist(new Client("4", "Kim", "In", "Moscow", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 11, 11)));
        Client client = this.clientRepository.findById("4").get();
        assertThat(client.getId()).isEqualTo("4");
        assertThat(client.getFirstName()).isEqualTo("Kim");
        assertThat(client.getLastName()).isEqualTo("In");
        assertThat(client.getAddressDefault()).isEqualTo("Moscow");
        assertThat(client.getCreateDt()).isEqualTo(LocalDate.of(2020, 01, 01));
        assertThat(client.getUpdateDt()).isEqualTo(LocalDate.of(2020, 11, 11));
    }
}