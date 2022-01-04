package de.thb.carsharing.entity;

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
@Entity(name = "carsharing_credit_card")
public class CreditCard {
    @Id
    private String number;
    private String csv;
    private Date expirationDate;
}
