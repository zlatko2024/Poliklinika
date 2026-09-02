package rs.zr.sa.poliklinika.dto;

import lombok.*;

/**
 * DTO odgovor koji vraca podatke o usluzi.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UslugaResponse {

    private Long uslugaId;
    private String naziv;
    private Double cena;
    private PoliklinikaResponse poliklinika;
}