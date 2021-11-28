package de.thb.carsharing.service;

import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Customer addCustomer(String firstName, String lastName) {
        return customerRepository.save(Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build());
    }

    public void deleteCarById(long id) { customerRepository.deleteById(id);}
}
