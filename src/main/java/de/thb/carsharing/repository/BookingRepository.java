package de.thb.carsharing.repository;

import de.thb.carsharing.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Booking.class, idClass = Long.class)
public interface BookingRepository extends CrudRepository<Booking, Long> {
    //Queries
}