package rs.zr.sa.poliklinika.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO odgovor koji vraca podatke o racunu.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RacunResponse {

    private Long racunId;
    private String brojRacuna;
    private LocalDate datumIzdavanja;
    private Double ukupanIznos;
    private String statusPlacanja;
    private PregledResponse pregled;
    private DoktorResponse doktor;
}