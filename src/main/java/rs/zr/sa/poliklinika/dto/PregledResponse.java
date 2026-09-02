package rs.zr.sa.poliklinika.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO odgovor koji vraca podatke o pregledu.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PregledResponse {

    private Long pregledId;
    private LocalDate datum;
    private LocalTime vreme;
    private String vrstaPregleda;
    private String status;
    private PacijentResponse pacijent;
    private PoliklinikaResponse poliklinika;
}