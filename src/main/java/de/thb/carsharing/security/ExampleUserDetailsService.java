package de.thb.carsharing.security;

import de.thb.carsharing.entity.Admin;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.repository.AdminRepository;
import de.thb.carsharing.repository.CustomerRepository;
import de.thb.carsharing.security.authority.AdminAuthority;
import de.thb.carsharing.security.authority.UserAuthority;
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

		Optional<Admin> adminuser = adminRepository.findByEmail(username);

		if(adminuser.isPresent()) {
			return ExampleUserDetails.builder()
					.username(adminuser.get().getEmail())
					.password(adminuser.get().getPassword())
					.authorities(List.of(new AdminAuthority()))
					.enabled(adminuser.get().isEnabled())
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.build();
		} else {
			Optional<Customer> customeruser = customerRepository.findByEmail(username);
			if(customeruser.isPresent()){
				return ExampleUserDetails.builder()
						.username(customeruser.get().getEmail())
						.password(customeruser.get().getPassword())
						.authorities(List.of(new UserAuthority()))
						.enabled(customeruser.get().isEnabled())
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.build();
			} else {
				throw new UsernameNotFoundException("User not found!");
			}

		}
    }
}
