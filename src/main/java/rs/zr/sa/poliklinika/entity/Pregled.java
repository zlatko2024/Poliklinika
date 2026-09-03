package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Predstavlja pregled zakazan u poliklinici na odredjeni datum i vreme, sa vrstom pregleda i statusom.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pregled")
@ToString
public class Pregled {

    /**
     * Jedinstveni identifikator pregleda u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pregled_id")
    private Long pregledId;

    /**
     * Datum održavanja pregleda
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Datum pregleda ne sme biti null")
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    /**
     * Vreme održavanja pregleda
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Vreme pregleda ne sme biti null")
    @Column(name = "vreme", nullable = false)
    private LocalTime vreme;

    /**
     * Vrsta pregleda
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadržati samo prazne znakove
     */
    @NotBlank(message = "Vrsta pregleda ne sme biti prazna")
    @Column(name = "vrsta_pregleda", nullable = false)
    private String vrstaPregleda;

    /**
     * Status pregleda
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadržati samo prazne znakove
     */
    @NotBlank(message = "Status pregleda ne sme biti prazan")
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Pacijent na kog glasi pregled
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Pacijent mora biti dodeljen pregledu")
    @ManyToOne
    @JoinColumn(name = "pacijent_id", nullable = false)
    private Pacijent pacijent;

    /**
     * PoliklinikaApp u kojoj se obavlja pregled
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "PoliklinikaApp mora biti dodeljena pregledu")
    @ManyToOne
    @JoinColumn(name = "poliklinika_id", nullable = false)
    private Poliklinika poliklinika;

}