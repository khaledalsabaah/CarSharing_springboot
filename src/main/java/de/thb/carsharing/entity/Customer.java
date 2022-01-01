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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private Date registrationDate;
    private String driversLicenceID;
    private String phoneNumber;
    //birthplace
    private Date birthdate;
    private String address;
    private String zipcode;
    private String city;
    //gender

    @OneToOne
    private CreditCard creditCard;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Booking> bookingList;

//    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
//    private List<CreditCard> creditCardList;

}
