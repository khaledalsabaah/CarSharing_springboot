package de.thb.carsharing.security;

import de.thb.carsharing.entity.Admin;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.repository.AdminRepository;
import de.thb.carsharing.repository.CustomerRepository;
import de.thb.carsharing.security.authority.AdminAuthority;
import de.thb.carsharing.security.authority.CustomerAuthority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ExampleUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> adminUser = adminRepository.findByEmail(username);

        if (adminUser.isPresent()) {
            return ExampleUserDetails.builder()
                    .username(adminUser.get().getEmail())
                    .password(adminUser.get().getPassword())
                    .authorities(List.of(new AdminAuthority()))
                    .enabled(adminUser.get().isEnabled())
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .id(adminUser.get().getId())
                    .build();
        } else {
            Optional<Customer> customerUser = customerRepository.findByEmail(username);
            if (customerUser.isPresent()) {
                return ExampleUserDetails.builder()
                        .username(customerUser.get().getEmail())
                        .password(customerUser.get().getPassword())
                        .authorities(List.of(new CustomerAuthority()))
                        .enabled(customerUser.get().isEnabled())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .id(customerUser.get().getId())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found!");
            }
        }
    }
}
