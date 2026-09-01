package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Predstavlja uput izdat pacijentu u poliklinici.
 * Sadrzi informacije o broju uputa, datumu izdavanja, dijagnozi i napomeni.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uput")
@Getter
@Setter
@ToString
public class Uput {

    /**
     * Jedinstveni identifikator uputa u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uput_id")
    private Long uputId;

    /**
     * Broj uputa
     * Nedozvoljene vrednosti: Vrednost ne sme biti null niti prazan string
     */
    @NotBlank(message = "Broj uputa ne sme biti prazan")
    @Column(name = "broj_uputa", nullable = false, unique = true)
    private String brojUputa;

    /**
     * Datum izdavanja uputa
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Datum izdavanja ne sme biti null")
    @Column(name = "datum_izdavanja", nullable = false)
    private LocalDate datumIzdavanja;

    /**
     * Dijagnoza na uputu
     * Nedozvoljene vrednosti: Vrednost ne sme biti null niti prazan string
     */
    @NotBlank(message = "Dijagnoza ne sme biti prazna")
    @Column(name = "dijagnoza", nullable = false)
    private String dijagnoza;

    /**
     * Napomena uz uput
     */
    @Column(name = "napomena")
    private String napomena;

    /**
     * Pacijent kom je uput izdat
     */
    @NotNull(message = "Pacijent mora biti dodeljen uputu")
    @ManyToOne
    @JoinColumn(name = "pacijent_id", nullable = false)
    private Pacijent pacijent;

}