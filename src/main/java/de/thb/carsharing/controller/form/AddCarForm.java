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
import javax.validation.constraints.*;
import java.awt.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarForm {
    @NotNull(message = "Preis per hour darf nicht leer sein!")
    private double preisPerHour;
    @NotNull
    @NotEmpty(message = "Model darf nicht leer sein!")
    private String model;
    @NotNull(message = "Car Color darf nicht leer sein!")
    @Enumerated(EnumType.STRING)
    @Column(name="car_color")
    private CarColor carColor;
    @NotNull
    @Min(value=2000, message="bitte ein gültige Nummer eingeben")
    @Max(value=3000, message="bitte ein gültige Nummer eingeben")
    private short yearBuilt;
    @NotNull(message = "Fuel Type darf nicht leer sein!")
    @Enumerated(EnumType.STRING)
    @Column(name="fuel_type")
    private FuelType fuelType;
    @NotNull
    @DecimalMin(value = "0.0", message="bitte ein gültige Nummer eingeben")
    private double xCoordinates;
    @NotNull
    @DecimalMin(value = "0.0", message="bitte ein gültige Nummer eingeben")
    private double yCoordinates;
    private boolean automatic;
}
