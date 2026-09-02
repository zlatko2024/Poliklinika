package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO zahtev za kreiranje ili azuriranje usluge.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UslugaRequest {

    @NotBlank(message = "Naziv usluge ne sme biti prazan")
    private String naziv;

    @NotNull(message = "Cena usluge mora biti uneta")
    @Min(value = 1, message = "Cena usluge mora biti najmanje 1")
    private Double cena;

    @NotNull(message = "ID poliklinike mora biti unet")
    private Long poliklinikaId;
}