package de.thb.carsharing.entity;

import de.thb.carsharing.entity.Type.CarColor;
import de.thb.carsharing.entity.Type.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carsharing_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    private CarColor carColor;
    private short yearBuilt;
    private FuelType fuelType;
    private double xCoordinates;
    private double yCoordinates;
    private double pricePerHour;
    private boolean automatic;
    private boolean inService;
    private boolean available;
    private boolean open;
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Booking> bookingList;
}
