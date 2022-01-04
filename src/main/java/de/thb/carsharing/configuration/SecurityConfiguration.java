package de.thb.carsharing.configuration;

import de.thb.carsharing.repository.AdminRepository;
import de.thb.carsharing.repository.CustomerRepository;
import de.thb.carsharing.security.ExampleUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private CustomerRepository customerRepository;
    @Autowired
	private AdminRepository adminRepository;
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new ExampleUserDetailsService(adminRepository, customerRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
