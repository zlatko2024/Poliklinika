package rs.zr.sa.poliklinika.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO odgovor koji vraca podatke o uputu.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UputResponse {

    private Long uputId;
    private String brojUputa;
    private LocalDate datumIzdavanja;
    private String dijagnoza;
    private String napomena;
    private PacijentResponse pacijent;
}