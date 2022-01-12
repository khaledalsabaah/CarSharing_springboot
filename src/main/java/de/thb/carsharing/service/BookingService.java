package de.thb.carsharing.service;

import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.Car;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.entity.Type.BookingStatus;
import de.thb.carsharing.repository.BookingRepository;
import de.thb.carsharing.repository.CarRepository;
import de.thb.carsharing.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookingsByCustomerId(long id) {
        return (List<Booking>) bookingRepository.findByCustomerIs(customerRepository.findById(id).get());
    }

    public Booking addBooking(long carID, long customerID) { //TODO: IDs anstatt Entities
        if(carRepository.existsById(carID) && customerRepository.existsById(customerID)){
            return bookingRepository.save(Booking.builder()
                    .car(carRepository.findById(carID).get())
                    .customer(customerRepository.findById(customerID).get())
                    .startTime(new Date())
                    .bookingStatus(BookingStatus.ORDERED)
                    .build());
        } else {
            return null;
        }
    }

    public void deleteCarById(long id) {
        bookingRepository.deleteById(id);
    }

    public void updateBookingStatus(long id, BookingStatus bookingStatus) {
        if (bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setBookingStatus(bookingStatus);
            bookingRepository.save(booking);
        }
    }

    public BookingStatus getBookingStatus(long id) {
        if (bookingRepository.findById(id).isPresent())
            return bookingRepository.findById(id).get().getBookingStatus();
        else
            return BookingStatus.NONEXISTENT;
    }

    //TODO: calculateCarDistance()
}
