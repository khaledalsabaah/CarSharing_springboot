package de.thb.carsharing.controller.form;

import de.thb.carsharing.entity.Type.CarColor;
import de.thb.carsharing.entity.Type.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarForm {
    @NotNull
    @NotEmpty
    private String preisPerHour;
    @NotNull
    @NotEmpty
    private String model;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="car_color")
    private CarColor carColor;
    @NotNull
    private short yearBuilt;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="fuel_type")
    private FuelType fuelType;
    @NotNull
    private double xCoordinates;
    @NotNull
    private double yCoordinates;
    @NotNull
    private boolean automatic;
}
