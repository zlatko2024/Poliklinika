package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO zahtev za kreiranje ili azuriranje poliklinike.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PoliklinikaRequest {

    @NotBlank(message = "Naziv poliklinike ne sme biti prazan")
    private String naziv;

    @NotBlank(message = "Adresa poliklinike ne sme biti prazna")
    private String adresa;

    @NotBlank(message = "Kontakt telefon ne sme biti prazan")
    private String kontaktTelefon;
}