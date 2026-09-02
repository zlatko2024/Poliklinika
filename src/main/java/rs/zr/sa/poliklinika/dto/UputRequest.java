package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO zahtev za kreiranje ili azuriranje uputa.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UputRequest {

    @NotBlank(message = "Broj uputa ne sme biti prazan")
    private String brojUputa;

    @NotNull(message = "Datum izdavanja ne sme biti null")
    private LocalDate datumIzdavanja;

    @NotBlank(message = "Dijagnoza ne sme biti prazna")
    private String dijagnoza;

    private String napomena;

    @NotNull(message = "ID pacijenta mora biti unet")
    private Long pacijentId;
}