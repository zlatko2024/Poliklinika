package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Predstavlja nalaz lekara nakon obavljenog pregleda.
 * Sadrzi informacije o opisu, zakljucku, preporuci i datumu nalaza.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nalaz")
@Getter
@Setter
@ToString
public class Nalaz {

    /**
     * Jedinstveni identifikator nalaza u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nalaz_id")
    private Long nalazId;

    /**
     * Opis nalaza
     * Nedozvoljene vrednosti: Vrednost ne sme biti null niti prazan string
     */
    @NotBlank(message = "Opis nalaza ne sme biti prazan")
    @Column(name = "opis", nullable = false)
    private String opis;

    /**
     * Zakljucak nalaza
     */
    @Column(name = "zakljucak")
    private String zakljucak;

    /**
     * Preporuka lekara
     */
    @Column(name = "preporuka")
    private String preporuka;

    /**
     * Datum izdavanja nalaza
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Datum nalaza ne sme biti null")
    @Column(name = "datum_nalaza", nullable = false)
    private LocalDate datumNalaza;

    /**
     * Pregled na koji se nalaz odnosi
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Pregled mora biti dodeljen nalazu")
    @OneToOne
    @JoinColumn(name = "pregled_id", unique = true, nullable = false)
    private Pregled pregled;

    /**
     * Uput povezan sa ovim nalazom
     */
    @ManyToOne
    @JoinColumn(name = "uput_id")
    private Uput uput;

}