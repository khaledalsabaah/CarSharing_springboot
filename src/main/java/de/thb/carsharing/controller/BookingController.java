package de.thb.carsharing.controller;
import de.thb.carsharing.controller.form.AddCustomerForm;
import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.Customer;
import de.thb.carsharing.entity.Type.BookingStatus;
import de.thb.carsharing.entity.User;
import de.thb.carsharing.security.ExampleUserDetails;
import de.thb.carsharing.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @RequestMapping("addbooking")
    public String addBooking(@RequestParam("carid") long carid, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ExampleUserDetails user = (ExampleUserDetails)authentication.getPrincipal();
        long userId = user.getId();
        Booking booking = bookingService.addBooking(carid, userId);
        if(booking != null){
            model.addAttribute("booking", booking);
        }
        return "bookingdetails";
    }
    @GetMapping("bookings")
    public String showBookings(Model model){
        List<Booking> bookingList= bookingService.getAllBookings();
        model.addAttribute("bookings", bookingList);
        return "bookings";
    }
    @GetMapping("bookings/{id}")
    public String showBookingDetails(@PathVariable("id") long id, Model model){

        Booking booking;
        if(bookingService.getBookingById(id).isPresent()){
            booking= bookingService.getBookingById(id).get();
            model.addAttribute("booking", booking);
        }
        else {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        return "bookingdetails";
    }
/*
    @GetMapping("bookings/{id}")
    public String showBookingStatus(@PathVariable("id") Long id, Model model){

        BookingStatus bookingStatus;
        if(bookingService.getBookingById(id).isPresent())
            bookingStatus= bookingService.getBookingStatus(id);
        else
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        model.addAttribute("bookingstatus", bookingStatus);
        return "status";
    }
*/
    @RequestMapping("cancelbooking")
    public String cancelBooking(@RequestParam("id") Long id){
        if(bookingService.getBookingById(id).isPresent()){
            bookingService.cancelBooking(id);
        }
        else{
            throw new ResponseStatusException((HttpStatus.NOT_FOUND),"Es ist ein Fehler bei der Stornierung aufgetreten, bitte melden Sie Sich beim Kundendienst!");
        }
        return "redirect:/";
    }
    @RequestMapping("completebooking")
    public String completeBooking(@RequestParam("id") Long id){
        if(bookingService.getBookingById(id).isPresent()){
            bookingService.finishBooking(id);
        }
        else{
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        return "redirect:/bookings";
    }
}
