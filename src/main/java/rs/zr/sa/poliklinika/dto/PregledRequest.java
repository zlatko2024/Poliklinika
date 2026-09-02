package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO zahtev za kreiranje ili azuriranje pregleda.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PregledRequest {

    @NotNull(message = "Datum pregleda ne sme biti null")
    private LocalDate datum;

    @NotNull(message = "Vreme pregleda ne sme biti null")
    private LocalTime vreme;

    @NotBlank(message = "Vrsta pregleda ne sme biti prazna")
    private String vrstaPregleda;

    @NotBlank(message = "Status pregleda ne sme biti prazan")
    private String status;

    @NotNull(message = "ID pacijenta mora biti unet")
    private Long pacijentId;

    @NotNull(message = "ID poliklinike mora biti unet")
    private Long poliklinikaId;
}