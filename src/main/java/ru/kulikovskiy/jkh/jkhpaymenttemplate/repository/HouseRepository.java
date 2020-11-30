package ru.kulikovskiy.jkh.jkhpaymenttemplate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.House;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.OrganizationResponse;

import java.util.Set;

@Repository
public interface HouseRepository extends CrudRepository<House, String> {

    @Query("select o from House h " +
            "  join HouseOrganization ho on ho.houseOrganizationIdentity.houses_guid = h.houseGuid" +
            "  join Organization o on o.guid = ho.houseOrganizationIdentity.orgsGuid " +
            "  where h.houseGuid =:houseGuid")
    Set<Organization> getByOrganizationsHouse(@Param("houseGuid") String houseGuid);

    House getByHouseGuid(String houseGuid);
}
