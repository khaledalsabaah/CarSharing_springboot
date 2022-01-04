package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Admin;
import de.thb.carsharing.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Admin.class, idClass = Long.class)
public interface AdminRepository extends CrudRepository<Admin, Long> {
    // Queries

    Optional<Admin> findByEmail(String email);
}
