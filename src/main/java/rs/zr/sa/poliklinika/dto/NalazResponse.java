package rs.zr.sa.poliklinika.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO odgovor koji vraca podatke o nalazu.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NalazResponse {

    private Long nalazId;
    private String opis;
    private String zakljucak;
    private String preporuka;
    private LocalDate datumNalaza;
    private PregledResponse pregled;
    private UputResponse uput;
}