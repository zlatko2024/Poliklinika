package rs.zr.sa.poliklinika.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoktorTest {

    private Validator validator;
    private Usluga mockUsluga;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockUsluga = new Usluga(); // Pretpostavka da Usluga ima prazan konstruktor
    }

    @Test
    void getIme() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "Kardiolog", mockUsluga);
        assertEquals("Marko", doktor.getIme());
    }

    @Test
    void getPrezime() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "Kardiolog", mockUsluga);
        assertEquals("Markovic", doktor.getPrezime());
    }

    @Test
    void getBrojLicence() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "Kardiolog", mockUsluga);
        assertEquals("LIC123", doktor.getBrojLicence());
    }

    @Test
    void getSpecijalnost() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "Kardiolog", mockUsluga);
        assertEquals("Kardiolog", doktor.getSpecijalnost());
    }

    @Test
    void setIme() {
        Doktor d = new Doktor();
        d.setIme("Marko");
        assertEquals("Marko", d.getIme());
    }

    @Test
    void setPrezime() {
        Doktor d = new Doktor();
        d.setPrezime("Markovic");
        assertEquals("Markovic", d.getPrezime());
    }

    @Test
    void setBrojLicence() {
        Doktor d = new Doktor();
        d.setBrojLicence("LIC123");
        assertEquals("LIC123", d.getBrojLicence());
    }

    @Test
    void setSpecijalnost() {
        Doktor d = new Doktor();
        d.setSpecijalnost("Kardiolog");
        assertEquals("Kardiolog", d.getSpecijalnost());
    }

    @Test
    void setUsluga() {
        Doktor d = new Doktor();
        d.setUsluga(mockUsluga);
        assertEquals(mockUsluga, d.getUsluga());
    }

    @ParameterizedTest
    @CsvSource({
            "Marko, Markovic, LIC123, Kardiolog",
            "Ana, Antic, LIC456, Neurolog"
    })
    @DisplayName("Validacija treba da prodje za ispravne objekte Doktora")
    void validate_ValidDoktor_NoViolations(String ime, String prezime, String brojLicence, String specijalnost) {
        Doktor d = new Doktor(null, ime, prezime, brojLicence, specijalnost, mockUsluga);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(d);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je ime prazno")
    void validate_BlankIme_FailsValidation() {
        Doktor doktor = new Doktor(null, "", "Markovic", "LIC123", "Kardiolog", mockUsluga);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(doktor);

        assertFalse(violations.isEmpty(), "Neuspesno, prazno ime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ime doktora ne sme biti prazno", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je prezime prazno")
    void validate_BlankPrezime_FailsValidation() {
        Doktor doktor = new Doktor(null, "Marko", "", "LIC123", "Kardiolog", mockUsluga);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(doktor);

        assertFalse(violations.isEmpty(), "Neuspesno, prazno prezime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Prezime doktora ne sme biti prazno", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je broj licence prazan")
    void validate_BlankBrojLicence_FailsValidation() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "", "Kardiolog", mockUsluga);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(doktor);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan broj licence je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Broj licence ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je specijalnost prazna")
    void validate_BlankSpecijalnost_FailsValidation() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "", mockUsluga);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(doktor);

        assertFalse(violations.isEmpty(), "Neuspesno, prazna specijalnost je prosla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Specijalnost doktora ne sme biti prazna", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je usluga null")
    void validate_NullUsluga_FailsValidation() {
        Doktor doktor = new Doktor(null, "Marko", "Markovic", "LIC123", "Kardiolog", null);

        Set<ConstraintViolation<Doktor>> violations = validator.validate(doktor);

        assertFalse(violations.isEmpty(), "Neuspesno, null usluga je prosla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Usluga mora biti dodeljena doktoru", msg);
    }
}