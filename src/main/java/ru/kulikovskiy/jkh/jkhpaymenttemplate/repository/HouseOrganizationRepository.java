package ru.kulikovskiy.jkh.jkhpaymenttemplate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.HouseOrganization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.HouseOrganizationIdentity;

@Repository
public interface HouseOrganizationRepository extends CrudRepository<HouseOrganization, HouseOrganizationIdentity> {
}
