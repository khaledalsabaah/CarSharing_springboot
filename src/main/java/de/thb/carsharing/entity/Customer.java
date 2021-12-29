package de.thb.carsharing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carsharing_customer")
public class Customer extends User {
    private String firstName;
    private String lastName;
    private Date registrationDate;
    private String driversLicenceID;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Booking> bookingList;
//    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
//    private List<CreditCard> creditCardList;
}
