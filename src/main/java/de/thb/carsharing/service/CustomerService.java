package de.thb.carsharing.service;

import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.CreditCard;
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

    public Customer addCustomer(String email, String password, String firstName, String lastName, String driversLicenceID,
                                String phoneNumber, Date birthdate, String address, String zipcode, String city,
                                String creditCardNumber, String creditCardCSV, Date creditCardExpirationDate) {
        Customer newCustomer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .registrationDate(new Date())
                .driversLicenceID(driversLicenceID)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .address(address)
                .zipcode(zipcode)
                .city(city)
                .creditCard(new CreditCard(creditCardNumber, creditCardCSV, creditCardExpirationDate))
                .build();
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        return customerRepository.save(newCustomer);
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

}
