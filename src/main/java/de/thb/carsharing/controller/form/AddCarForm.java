package de.thb.carsharing.controller.form;

import de.thb.carsharing.entity.Type.CarColor;
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
    /*
    @NotNull
    @NotEmpty
    private short yearBuilt;
    @NotNull
    @NotEmpty
    private FuelType fuelType;
    @NotNull
    @NotEmpty
    private String location;
    @NotNull
    @NotEmpty
    private boolean isAutomatic;
    @NotNull
    @NotEmpty
    private boolean isAvailable;
    */


}
