package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Admin.class, idClass = Long.class)
public interface AdminRepository extends CrudRepository<Admin, Long> {
    // Queries
}
