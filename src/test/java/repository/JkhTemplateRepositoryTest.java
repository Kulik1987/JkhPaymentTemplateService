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
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.repository.JkhTemplateRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JkhPaymentTemplateService.class)
public class JkhTemplateRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JkhTemplateRepository jkhTemplateRepository;

    private JkhTemplate jkhTemplate;

    @Before
    public void initObjects() {
        jkhTemplate = new JkhTemplate("my", "2", "house_code3", "{id: 1}", "1");

    }

    @Test
    public void checkFindByClientIdAndHouseCodeAndIsActiveNotNull() throws Exception {
        this.entityManager.persist(jkhTemplate);
        JkhTemplate jkhTemplateResponse = this.jkhTemplateRepository.findByClientIdAndHouseCodeAndIsActiveNotNull("2", "house_code3");
        assertThat(jkhTemplateResponse.getId()).isEqualTo(1);
        assertThat(jkhTemplateResponse.getClientId()).isEqualTo("2");
        assertThat(jkhTemplateResponse.getHouseCode()).isEqualTo("house_code3");
        assertThat(jkhTemplateResponse.getIsActive()).isEqualTo("1");
        assertThat(jkhTemplateResponse.getNameTemplate()).isEqualTo("my");
        assertThat(jkhTemplateResponse.getTemplateJson()).isEqualTo("{id: 1}");
    }

    @Test
    public void checkFindByIdAndIsActiveNotNull() {
        this.entityManager.persist(jkhTemplate);
        JkhTemplate jkhTemplateResponse = this.jkhTemplateRepository.findByIdAndIsActiveNotNull(1).get();
        assertThat(jkhTemplateResponse.getId()).isEqualTo(1);
        assertThat(jkhTemplateResponse.getClientId()).isEqualTo("2");
        assertThat(jkhTemplateResponse.getHouseCode()).isEqualTo("house_code3");
        assertThat(jkhTemplateResponse.getIsActive()).isEqualTo("1");
        assertThat(jkhTemplateResponse.getNameTemplate()).isEqualTo("my");
        assertThat(jkhTemplateResponse.getTemplateJson()).isEqualTo("{id: 1}");
    }

    @Test
    public void checkFindAllByClientId() {
        this.entityManager.persist(jkhTemplate);
        List<JkhTemplate> jkhTemplateList = this.jkhTemplateRepository.findAllByClientId("2");
        JkhTemplate jkhTemplateResponse = jkhTemplateList.get(0);
        assertThat(jkhTemplateResponse.getId()).isEqualTo(1);
        assertThat(jkhTemplateResponse.getClientId()).isEqualTo("2");
        assertThat(jkhTemplateResponse.getHouseCode()).isEqualTo("house_code3");
        assertThat(jkhTemplateResponse.getIsActive()).isEqualTo("1");
        assertThat(jkhTemplateResponse.getNameTemplate()).isEqualTo("my");
        assertThat(jkhTemplateResponse.getTemplateJson()).isEqualTo("{id: 1}");
    }
}