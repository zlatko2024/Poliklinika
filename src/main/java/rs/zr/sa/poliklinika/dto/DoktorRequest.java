package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO zahtev za kreiranje ili azuriranje doktora.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoktorRequest {

    @NotBlank(message = "Ime doktora ne sme biti prazno")
    private String ime;

    @NotBlank(message = "Prezime doktora ne sme biti prazno")
    private String prezime;

    @NotBlank(message = "Broj licence ne sme biti prazan")
    private String brojLicence;

    @NotBlank(message = "Specijalnost doktora ne sme biti prazna")
    private String specijalnost;

    @NotNull(message = "ID usluge mora biti unet")
    private Long uslugaId;
}