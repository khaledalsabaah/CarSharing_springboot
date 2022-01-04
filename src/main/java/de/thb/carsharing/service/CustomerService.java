package de.thb.carsharing.service;

import de.thb.carsharing.entity.Booking;
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

    public Customer addCustomer(String firstName, String lastName,String driversLicenceID) {
        return customerRepository.save(Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .driversLicenceID(driversLicenceID)
                .build());
    }

    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }

    public boolean modifyAccountDetails(long id, String firstName, String lastName, String driversLicenceID) {
        if (customerRepository.findById(id).isPresent()) { //TODO existsById(id) als alternative
            Customer customer = customerRepository.findById(id).get();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setDriversLicenceID(driversLicenceID);
            customerRepository.save(customer);
            return true;
        } else
            return false;
    }

    //TODO - login

}
