package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Long.class)
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCreditCard_Number(String creditCardNumber);

}