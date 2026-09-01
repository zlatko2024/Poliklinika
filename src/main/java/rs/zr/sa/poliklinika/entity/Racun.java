package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

/**
 * Predstavlja fiskalni racun izdat za pregled, sa brojem racuna, datumom izdavanja, ukupnim iznosom i statusom placanja.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "racun")
@Getter
@Setter
@ToString
public class Racun {

    /**
     * Jedinstveni identifikator racuna u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "racun_id")
    private Long racunId;

    /**
     * Broj racuna
     * Nedozvoljene vrednosti: Vrednost ne sme biti null niti prazan string
     */
    @NotBlank(message = "Broj racuna ne sme biti prazan")
    @Column(name = "broj_racuna", nullable = false, unique = true)
    private String brojRacuna;

    /**
     * Datum kada je racun izdat
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Datum izdavanja racuna ne sme biti null")
    @Column(name = "datum_izdavanja", nullable = false)
    private LocalDate datumIzdavanja;

    /**
     * Ukupan iznos racuna izrazen u dinarima
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, niti manja od nule
     */
    @NotNull(message = "Ukupan iznos racuna ne sme biti null")
    @PositiveOrZero(message = "Ukupan iznos mora biti veci ili jednak nuli")
    @Column(name = "ukupan_iznos", nullable = false)
    private Double ukupanIznos;

    /**
     * Status da li je racun placen ili nije
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Status placanja ne sme biti prazan")
    @Column(name = "status_placanja", nullable = false)
    private String statusPlacanja;

    /**
     * Pregled na koji se racun odnosi
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Pregled mora biti dodeljen racunu")
    @OneToOne
    @JoinColumn(name = "pregled_id", nullable = false, unique = true)
    private Pregled pregled;
/**
     * Doktor koji je izdao racun / obavio pregled
     */
    @NotNull(message = "Doktor mora biti dodeljen racunu")
    @ManyToOne
    @JoinColumn(name = "doktor_id", nullable = false)
    private Doktor doktor;
}