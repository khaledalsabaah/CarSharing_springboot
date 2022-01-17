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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
        return (List<Booking>) bookingRepository.findByCustomerIs(customerRepository.findById(id).get());
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
                return null; //TODO Exceptions
            }
        } else {
            return null;
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
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    public boolean cancelBooking(long id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setBookingStatus(BookingStatus.CANCELLED);
            carRepository.findById(booking.getCar().getId()).get().setAvailable(true);
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    class CancelBookingAfterFifteenMinutes implements  Runnable {

        private long id;

        public CancelBookingAfterFifteenMinutes(long id){
            this.id = id;
        }

        @Override
        public void run() {
            if (bookingRepository.existsById(id)){
                Booking booking = bookingRepository.findById(id).get();
                if (booking.getBookingStatus().equals(BookingStatus.BOOKED)) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);
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
            long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            booking.setCost((booking.getCar().getPricePerHour() / 60) * durationInMinutes);

            carRepository.findById(booking.getCar().getId()).get().setAvailable(true);
            carRepository.findById(booking.getCar().getId()).get().setOpen(false);
            bookingRepository.save(booking);
            return true;
        } else
            return false;
    }

    public BookingStatus getBookingStatus(long id) {
        if (bookingRepository.findById(id).isPresent())
            return bookingRepository.findById(id).get().getBookingStatus();
        else
            return BookingStatus.NONEXISTENT;
    }

    //TODO: calculateCarDistance()
}
