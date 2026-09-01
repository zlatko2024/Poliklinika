package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Predstavlja uslugu koju pruza poliklinika.
 * Sadrzi informacije o nazivu, ceni i poliklinici u kojoj se pruza.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usluga")
@Getter
@Setter
@ToString
public class Usluga {
    /**
     * Jedinstveni identifikator usluge u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usluga_id")
    private Long uslugaId;

    /**
     * Naziv usluge u poliklinici
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Naziv usluge ne sme biti prazan")
    @Column(name = "naziv", nullable = false)
    private String naziv;

    /**
     * Cena usluge izrazena u dinarima
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, niti manja od 1
     */
    @NotNull(message = "Cena usluge mora biti uneta")
    @Min(value = 1, message = "Cena usluge mora biti najmanje 1")
    @Column(name = "cena", nullable = false)
    private Double cena;

    /**
     * Poliklinika u kojoj se usluga pruza
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Poliklinika mora biti dodeljena usluzi")
    @ManyToOne
    @JoinColumn(name = "poliklinika_id", nullable = false)
    private Poliklinika poliklinika;

}