package rs.zr.sa.poliklinika.dto;

import lombok.*;

/**
 * DTO odgovor koji vraca podatke o doktoru.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoktorResponse {

    private Long doktorId;
    private String ime;
    private String prezime;
    private String brojLicence;
    private String specijalnost;
    private UslugaResponse usluga;
}