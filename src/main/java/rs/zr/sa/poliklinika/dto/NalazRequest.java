package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO zahtev za kreiranje ili azuriranje nalaza.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NalazRequest {

    @NotBlank(message = "Opis nalaza ne sme biti prazan")
    private String opis;

    private String zakljucak;

    private String preporuka;

    @NotNull(message = "Datum nalaza ne sme biti null")
    private LocalDate datumNalaza;

    @NotNull(message = "ID pregleda mora biti unet")
    private Long pregledId;

    private Long uputId;
}