package ru.kulikovskiy.jkh.jkhpaymenttemplate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    Organization getByInn(@Param("inn") String inn);
}
