package de.thb.carsharing.service;

import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.Type.BookingStatus;
import de.thb.carsharing.repository.BookingRepository;
import de.thb.carsharing.repository.CarRepository;
import de.thb.carsharing.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookingsByCustomerId(long id) {
        List<Booking> bookingList = (List<Booking>) bookingRepository
                .findByCustomerIs(customerRepository.findById(id).get());
        bookingList.sort((t1, t2) -> t2.getBookTime().compareTo(t1.getBookTime()));
        return bookingList;
    }

    public Booking addBooking(long carID, long customerID) {
        if (carRepository.existsById(carID) && customerRepository.existsById(customerID)) {
            List<Booking> bookingList = customerRepository.findById(customerID).get().getBookingList();
            List<Booking> conflictingBookings = bookingList.stream()
                    .filter(booking -> booking.getBookingStatus().equals(BookingStatus.BOOKED) ||
                            booking.getBookingStatus().equals(BookingStatus.STARTED)).collect(Collectors.toList());
            if (conflictingBookings.isEmpty()) {
                carRepository.findById(carID).get().setAvailable(false);
                Booking booking = bookingRepository.save(Booking.builder()
                        .car(carRepository.findById(carID).get())
                        .customer(customerRepository.findById(customerID).get())
                        .bookTime(new Date())
                        .bookingStatus(BookingStatus.BOOKED)
                        .cost(0)
                        .build());
                threadPoolTaskScheduler.schedule(
                        new CancelBookingAfterFifteenMinutes(booking.getId()),
                        new Date(booking.getBookTime().getTime() + TimeUnit.MINUTES.toMillis(15)));
                return booking;
            } else {
                throw new IllegalStateException("Sie haben bereits eine aktive Buchung");
            }
        } else {
            throw new IllegalArgumentException("Das Auto oder der Kunde existiert nicht");
        }
    }

    public boolean deleteBookingById(long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    public boolean startBooking(long id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setBookingStatus(BookingStatus.STARTED);
            booking.setStartTime(new Date());
            booking.getCar().setOpen(true);
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    public boolean cancelBooking(long id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setBookingStatus(BookingStatus.CANCELLED);
            booking.getCar().setAvailable(true);
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    class CancelBookingAfterFifteenMinutes implements Runnable {

        private long id;

        public CancelBookingAfterFifteenMinutes(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            if (bookingRepository.existsById(id)) {
                if (bookingRepository.findById(id).get().getBookingStatus().equals(BookingStatus.BOOKED)) {
                    cancelBooking(id);
                }
            }
        }
    }

    public boolean finishBooking(long id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setBookingStatus(BookingStatus.FINISHED);
            booking.setEndTime(new Date());

            long duration = booking.getEndTime().getTime() - booking.getStartTime().getTime();
            double durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration + 59999);
            booking.setCost((booking.getCar().getPricePerHour() / 60) * durationInMinutes);

            booking.getCar().setAvailable(true);
            booking.getCar().setOpen(false);
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    public BookingStatus getBookingStatus(long id) {
        if (bookingRepository.existsById(id))
            return bookingRepository.findById(id).get().getBookingStatus();
        else
            return BookingStatus.NONEXISTENT;
    }
}
