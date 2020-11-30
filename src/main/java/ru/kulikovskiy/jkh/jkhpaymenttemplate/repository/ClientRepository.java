package ru.kulikovskiy.jkh.jkhpaymenttemplate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

}
