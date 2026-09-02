package rs.zr.sa.poliklinika.dto;

import lombok.*;

/**
 * DTO odgovor koji vraca podatke o poliklinici.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PoliklinikaResponse {

    private Long poliklinikaId;
    private String naziv;
    private String adresa;
    private String kontaktTelefon;
}