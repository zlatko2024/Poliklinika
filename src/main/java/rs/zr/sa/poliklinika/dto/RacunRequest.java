package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO zahtev za kreiranje ili azuriranje racuna.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RacunRequest {

    @NotBlank(message = "Broj racuna ne sme biti prazan")
    private String brojRacuna;

    @NotNull(message = "Datum izdavanja racuna ne sme biti null")
    private LocalDate datumIzdavanja;

    @NotNull(message = "Ukupan iznos racuna ne sme biti null")
    @PositiveOrZero(message = "Ukupan iznos mora biti veci ili jednak nuli")
    private Double ukupanIznos;

    @NotBlank(message = "Status placanja ne sme biti prazan")
    private String statusPlacanja;

    @NotNull(message = "ID pregleda mora biti unet")
    private Long pregledId;

    @NotNull(message = "ID doktora mora biti unet")
    private Long doktorId;
}