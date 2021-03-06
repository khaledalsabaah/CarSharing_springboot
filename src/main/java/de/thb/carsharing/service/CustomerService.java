package de.thb.carsharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private final PasswordEncoder passwordEncoder;

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
        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$"))
            throw new IllegalArgumentException("Das Passwort erfüllt die Vorgaben nicht");
        if(customerRepository.findByCreditCard_Number(creditCardNumber).isPresent())
            throw new IllegalArgumentException("Die Kreditkarte ist bereits in Benutzung");
        if (customerRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Die Email ist bereits in Benutzung");
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
        newCustomer.setPassword(passwordEncoder.encode(password));
        newCustomer.setEnabled(true);
        return customerRepository.save(newCustomer);
    }

    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }

    public boolean modifyAccountDetails(long id, String firstName, String lastName, String driversLicenceID) {
        if (customerRepository.existsById(id)) {
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
