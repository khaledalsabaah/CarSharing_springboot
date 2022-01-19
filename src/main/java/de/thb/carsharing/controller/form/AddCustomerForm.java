package de.thb.carsharing.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerForm {
    @NotNull
    @NotEmpty
    //@NotBlank(message = "dieses Feld darf nicht leer sein")
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String driversLicenceID;
    @NotNull
    @NotEmpty
    private String email;
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Size(min=8)
    private String password;
    @NotNull
    private Date birthdate;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    @NotEmpty
    private String zipcode;
    @NotNull
    @NotEmpty
    private String city;
    //Credit card
    //@Id
    @NotNull
    @NotEmpty
    private String creditCardNumber;
    @NotNull
    @NotEmpty
    private String creditCardCSV;
    @NotNull
    private Date creditCardExpirationDate;
}
