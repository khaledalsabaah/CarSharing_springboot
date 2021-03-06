package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Car.class, idClass = Long.class)
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByAvailableTrue();
}