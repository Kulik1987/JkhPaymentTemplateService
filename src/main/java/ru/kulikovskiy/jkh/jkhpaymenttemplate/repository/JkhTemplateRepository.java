package ru.kulikovskiy.jkh.jkhpaymenttemplate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;

import java.util.List;
import java.util.Optional;

public interface JkhTemplateRepository extends CrudRepository<JkhTemplate, Integer> {
    JkhTemplate findByClientIdAndHouseCodeAndIsActiveNotNull(@Param("clientId") String clientId, @Param("houseCode") String houseCode);

    Optional<JkhTemplate> findByIdAndIsActiveNotNull(@Param("id") Integer id);

    List<JkhTemplate> findAllByClientId(@Param("clientId") String clientId);
}
