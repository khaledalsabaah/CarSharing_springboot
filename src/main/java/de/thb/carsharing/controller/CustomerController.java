package de.thb.carsharing.controller;

import de.thb.carsharing.controller.form.AddCustomerForm;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.service.CustomerService;
import de.thb.carsharing.controller.form.AddCustomerForm;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Controller

@AllArgsConstructor
public class CustomerController{

    private final CustomerService customerService;

    @GetMapping("customers")
    public String showcustomers(Model model){
        List<Customer> customerList= customerService.getAllCustomers();
        model.addAttribute("customers", customerList);
        return "customers";
    }
    @GetMapping("customers/{id}")
    public String showcustomerDetail(@PathVariable("id") Long id, Model model){

        Customer customer;
        if(customerService.getCustomerById(id).isPresent())
            customer= customerService.getCustomerById(id).get();
        else
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        model.addAttribute("customer", customer);
        return "details";
    }

    @GetMapping("signup")
    public String showSignup(Model model){
        return "signup";
    }
    @PostMapping("addcustomerform")
    public String addCustomer(@Valid AddCustomerForm form, BindingResult result){
        //if(result.hasErrors())
        customerService.addCustomer(form.getEmail(),form.getPassword(),form.getFirstName(), form.getLastName() , form.getDriversLicenceID(), form.getPhoneNumber(),
                form.getBirthdate(), form.getAddress(), form.getZipcode(), form.getCity(),
                form.getCreditCardNumber(),form.getCreditCardCSV(),form.getCreditCardExpirationDate());

        return "redirect:/index";
    }
}