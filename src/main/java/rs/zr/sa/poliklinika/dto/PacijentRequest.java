package rs.zr.sa.poliklinika.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO zahtev za kreiranje ili azuriranje pacijenta.
 * @author Zlatko Radovanovic
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacijentRequest {

    @NotBlank(message = "Ime pacijenta ne sme biti prazno")
    private String ime;

    @NotBlank(message = "Prezime pacijenta ne sme biti prazno")
    private String prezime;

    @NotBlank(message = "JMBG ne sme biti prazan")
    private String jmbg;

    @NotBlank(message = "Email ne sme biti prazan")
    @Email(message = "Email mora biti u ispravnom formatu")
    private String email;

    private String telefon;
}