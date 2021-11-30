package de.thb.carsharing.service;

import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(long id) {
        return customerRepository.findById(id);
    }

    public Customer addCustomer(String firstName, String lastName, Date registrationDate, String driversLicenceID) {
        return customerRepository.save(Customer.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .registrationDate(registrationDate)
                        .driversLicenceID(driversLicenceID)
                        .build());
    }

    public void deleteCarById(long id) { customerRepository.deleteById(id);}
}
