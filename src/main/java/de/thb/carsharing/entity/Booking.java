package de.thb.carsharing.entity;

import de.thb.carsharing.entity.Type.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carsharing_booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date startTime;
    private Date endTime;
    private BookingStatus bookingStatus;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Customer customer;
}