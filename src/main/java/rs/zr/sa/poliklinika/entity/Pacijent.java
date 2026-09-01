package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Predstavlja pacijenta poliklinike.
 * Sadrzi informacije o imenu, prezimenu, JMBG-u, email-u pacijenta, kao i broj telefona
 * @author Zlatko Radovanovic
 */
@Entity
@Table(name = "pacijent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Pacijent {

    /**
     * Jedinstveni identifikator pacijenta u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pacijent_id")
    private Long pacijentId;

    /**
     * Ime pacijenta poliklinike
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string,  niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Ime pacijenta ne sme biti prazno")
    @Column(name = "ime", nullable = false)
    private String ime;

    /**
     * Prezime pacijenta poliklinike
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string,  niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Prezime pacijenta ne sme biti prazno")
    @Column(name = "prezime", nullable = false)
    private String prezime;

    /**
     * JMBG pacijenta
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string,  niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "JMBG ne sme biti prazan")
    @Column(name = "jmbg", nullable = false, unique = true)
    private String jmbg;

    /**
     * Email pacijenta
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string,  niti sadrzati samo prazne znakove
     * Vrednost mora pratiti ispravan email format
     */
    @NotBlank(message = "Email ne sme biti prazan")
    @Email(message = "Email mora biti u ispravnom formatu")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Broj telefona pacijenta
     */
    @Column(name = "telefon")
    private String telefon;

}