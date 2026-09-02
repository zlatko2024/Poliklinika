package rs.zr.sa.poliklinika.dto;

import lombok.*;

/**
 * DTO odgovor koji vraca podatke o pacijentu.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacijentResponse {

    private Long pacijentId;
    private String ime;
    private String prezime;
    private String jmbg;
    private String email;
    private String telefon;
}