package rs.zr.sa.poliklinika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Predstavlja doktora koji radi u poliklinici.
 * Sadrzi informacije o imenu, prezimenu, broju licence, specijalnosti i usluzi koju pruza.
 * @author Zlatko Radovanovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doktor")
@Getter
@Setter
@ToString
public class Doktor {

    /**
     * Jedinstveni identifikator doktora u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doktor_id")
    private Long doktorId;

    /**
     * Ime doktora
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Ime doktora ne sme biti prazno")
    @Column(name = "ime", nullable = false)
    private String ime;

    /**
     * Prezime doktora
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Prezime doktora ne sme biti prazno")
    @Column(name = "prezime", nullable = false)
    private String prezime;

    /**
     * Broj licence doktora
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Broj licence ne sme biti prazan")
    @Column(name = "broj_licence", nullable = false)
    private String brojLicence;

    /**
     * Specijalnost doktora
     * Nedozvoljene vrednosti: Vrednost ne sme biti null, prazan string, niti sadrzati samo prazne znakove
     */
    @NotBlank(message = "Specijalnost doktora ne sme biti prazna")
    @Column(name = "specijalnost", nullable = false)
    private String specijalnost;

    /**
     * Usluga koju doktor pruza
     * Nedozvoljene vrednosti: Vrednost ne sme biti null
     */
    @NotNull(message = "Usluga mora biti dodeljena doktoru")
    @ManyToOne
    @JoinColumn(name = "usluga_id", nullable = false)
    private Usluga usluga;

}