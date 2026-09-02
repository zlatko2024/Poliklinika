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

class PacijentTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getIme() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "1234567890123", "petar@gmail.com", "064111222");
        assertEquals("Petar", pacijent.getIme());
    }

    @Test
    void getPrezime() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "1234567890123", "petar@gmail.com", "064111222");
        assertEquals("Petrovic", pacijent.getPrezime());
    }

    @Test
    void getJmbg() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "1234567890123", "petar@gmail.com", "064111222");
        assertEquals("1234567890123", pacijent.getJmbg());
    }

    @Test
    void getEmail() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "1234567890123", "petar@gmail.com", "064111222");
        assertEquals("petar@gmail.com", pacijent.getEmail());
    }

    @Test
    void setIme() {
        Pacijent p = new Pacijent();
        p.setIme("Petar");
        assertEquals("Petar", p.getIme());
    }

    @Test
    void setPrezime() {
        Pacijent p = new Pacijent();
        p.setPrezime("Petrovic");
        assertEquals("Petrovic", p.getPrezime());
    }

    @Test
    void setEmail() {
        Pacijent p = new Pacijent();
        p.setEmail("petar@gmail.com");
        assertEquals("petar@gmail.com", p.getEmail());
    }

    @ParameterizedTest
    @CsvSource({
            "Petar, Petrovic, 1234567890123, petar@gmail.com, 064111222",
            "Ana, Antic, 9876543210987, ana@yahoo.com, ''"
    })
    @DisplayName("Validacija treba da prodje za ispravne objekte Pacijenta")
    void validate_ValidPacijent_NoViolations(String ime, String prezime, String jmbg, String email, String telefon) {
        Pacijent p = new Pacijent(null, ime, prezime, jmbg, email, telefon);

        Set<ConstraintViolation<Pacijent>> violations = validator.validate(p);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je ime prazno")
    void validate_BlankIme_FailsValidation() {
        Pacijent pacijent = new Pacijent(null, "", "Petrovic", "1234567890123", "petar@gmail.com", "064111222");

        Set<ConstraintViolation<Pacijent>> violations = validator.validate(pacijent);

        assertFalse(violations.isEmpty(), "Neuspesno, prazno ime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ime pacijenta ne sme biti prazno", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je prezime prazno")
    void validate_BlankPrezime_FailsValidation() {
        Pacijent pacijent = new Pacijent(null, "Petar", "", "1234567890123", "petar@gmail.com", "064111222");

        Set<ConstraintViolation<Pacijent>> violations = validator.validate(pacijent);

        assertFalse(violations.isEmpty(), "Neuspesno, prazno prezime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Prezime pacijenta ne sme biti prazno", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je JMBG prazan")
    void validate_BlankJmbg_FailsValidation() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "", "petar@gmail.com", "064111222");

        Set<ConstraintViolation<Pacijent>> violations = validator.validate(pacijent);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan JMBG je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("JMBG ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je email u neispravnom formatu")
    void validate_InvalidEmailFormat_FailsValidation() {
        Pacijent pacijent = new Pacijent(null, "Petar", "Petrovic", "1234567890123", "neispravanemail", "064111222");

        Set<ConstraintViolation<Pacijent>> violations = validator.validate(pacijent);

        assertFalse(violations.isEmpty(), "Neispravan email je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Email mora biti u ispravnom formatu", msg);
    }

}