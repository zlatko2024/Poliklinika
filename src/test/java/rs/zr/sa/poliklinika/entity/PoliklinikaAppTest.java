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
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PoliklinikaAppTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getNaziv() {
        Poliklinika poliklinika = new Poliklinika(null, "Centar Zdravlja", "Nemanjina 10", "023111222", null);
        assertEquals("Centar Zdravlja", poliklinika.getNaziv());
    }

    @Test
    void getAdresa() {
        Poliklinika poliklinika = new Poliklinika(null, "Centar Zdravlja", "Nemanjina 10", "023111222", null);
        assertEquals("Nemanjina 10", poliklinika.getAdresa());
    }

    @Test
    void getKontaktTelefon() {
        Poliklinika poliklinika = new Poliklinika(null, "Centar Zdravlja", "Nemanjina 10", "023111222", null);
        assertEquals("023111222", poliklinika.getKontaktTelefon());
    }

    @Test
    void setNaziv() {
        Poliklinika p = new Poliklinika();
        p.setNaziv("MediGroup");
        assertEquals("MediGroup", p.getNaziv());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Testiranje nedozvoljenih vrednosti za setNaziv sa validacijom")
    void setNaziv_InvalidValues_ShouldFailValidation(String invalidNaziv) {
        Poliklinika p = new Poliklinika();
        p.setNaziv(invalidNaziv);
        p.setAdresa("Nemanjina 10");
        p.setKontaktTelefon("023111222");

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    void setAdresa() {
        Poliklinika p = new Poliklinika();
        p.setAdresa("Bulevar Oslobođenja 5");
        assertEquals("Bulevar Oslobođenja 5", p.getAdresa());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Testiranje nedozvoljenih vrednosti za setAdresa sa validacijom")
    void setAdresa_InvalidValues_ShouldFailValidation(String invalidAdresa) {
        Poliklinika p = new Poliklinika();
        p.setNaziv("Centar Zdravlja");
        p.setAdresa(invalidAdresa);
        p.setKontaktTelefon("023111222");

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    void setKontaktTelefon() {
        Poliklinika p = new Poliklinika();
        p.setKontaktTelefon("011333444");
        assertEquals("011333444", p.getKontaktTelefon());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Testiranje nedozvoljenih vrednosti za setKontaktTelefon sa validacijom")
    void setKontaktTelefon_InvalidValues_ShouldFailValidation(String invalidTelefon) {
        Poliklinika p = new Poliklinika();
        p.setNaziv("Centar Zdravlja");
        p.setAdresa("Nemanjina 10");
        p.setKontaktTelefon(invalidTelefon);

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "PoliklinikaApp A, Ulica 1, 023123456",
            "PoliklinikaApp B, Ulica 2, 021654321"
    })
    @DisplayName("Validacija treba da prodje za ispravne objekte Poliklinike")
    void validate_ValidPoliklinika_NoViolations(String naziv, String adresa, String kontaktTelefon) {
        Poliklinika p = new Poliklinika(null, naziv, adresa, kontaktTelefon, null);

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(p);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je naziv prazan")
    void validate_BlankNaziv_FailsValidation() {
        Poliklinika poliklinika = new Poliklinika(null, "", "Nemanjina 10", "023111222", null);

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(poliklinika);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan naziv je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Naziv poliklinike ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je adresa prazna")
    void validate_BlankAdresa_FailsValidation() {
        Poliklinika poliklinika = new Poliklinika(null, "Centar Zdravlja", "", "023111222", null);

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(poliklinika);

        assertFalse(violations.isEmpty(), "Neuspesno, prazna adresa je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Adresa poliklinike ne sme biti prazna", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je kontakt telefon prazan")
    void validate_BlankKontaktTelefon_FailsValidation() {
        Poliklinika poliklinika = new Poliklinika(null, "Centar Zdravlja", "Nemanjina 10", "", null);

        Set<ConstraintViolation<Poliklinika>> violations = validator.validate(poliklinika);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan kontakt telefon je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Kontakt telefon ne sme biti prazan", msg);
    }
}