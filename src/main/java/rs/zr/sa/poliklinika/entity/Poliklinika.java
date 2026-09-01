package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

/**
 * Predstavlja polikliniku koja sadrzi informacije o nazivu, adresi i kontakt telefonu.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poliklinika")
@Getter
@Setter
@ToString
public class Poliklinika {

    /**
     * Jedinstveni identifikator poliklinike u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poliklinika_id")
    private Long poliklinikaId;

    /**
     * Naziv poliklinike
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Naziv poliklinike ne sme biti prazan")
    @Column(name = "naziv", nullable = false)
    private String naziv;

    /**
     * Adresa na kojoj se nalazi poliklinika
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Adresa poliklinike ne sme biti prazna")
    @Column(name = "adresa", nullable = false)
    private String adresa;

    /**
     * Kontakt telefon poliklinike
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Kontakt telefon ne sme biti prazan")
    @Column(name = "kontakt_telefon", nullable = false)
    private String kontaktTelefon;

    /**
     * Lista pregleda zakazanih u poliklinici
     */
    @OneToMany(mappedBy = "poliklinika", cascade = CascadeType.ALL)
    private List<Pregled> pregledi;

}