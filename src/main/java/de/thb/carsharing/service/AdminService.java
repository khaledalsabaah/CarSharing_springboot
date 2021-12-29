package de.thb.carsharing.service;

import de.thb.carsharing.entity.Admin;
import de.thb.carsharing.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<Admin> getAdminById(long id) {
        return adminRepository.findById(id);
    }



    //TODO - login

}
