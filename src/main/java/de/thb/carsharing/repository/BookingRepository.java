package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Booking.class, idClass = Long.class)
public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findByCustomerIs(Customer customer);
}
