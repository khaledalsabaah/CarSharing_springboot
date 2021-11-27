package de.thb.carsharing.controller.form;

import de.thb.carsharing.entity.Type.CarColor;
import de.thb.carsharing.entity.Type.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerForm {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    private Date registrationDate;
    @NotNull
    @NotEmpty
    private String driversLicenceID;
}
