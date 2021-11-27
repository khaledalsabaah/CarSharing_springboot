package de.thb.carsharing.controller.form;

import de.thb.carsharing.entity.Type.CarColor;
import de.thb.carsharing.entity.Type.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String model;
    @NotNull
    private CarColor carColor;
    @NotNull
    private short yearBuilt;
    @NotNull
    private FuelType fuelType;
    @NotNull
    private double xCoordinates;
    @NotNull
    private double yCoordinates;
    @NotNull
    private boolean isAutomatic;
    @NotNull
    private boolean isInService;
    @NotNull
    private boolean isAvailable;
}
