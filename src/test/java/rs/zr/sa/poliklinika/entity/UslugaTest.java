package rs.zr.sa.poliklinika.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UslugaTest {

    private Validator validator;
    private Poliklinika mockPoliklinika;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockPoliklinika = new Poliklinika(); // Pretpostavka da Poliklinika ima prazan konstruktor
    }

    @Test
    void getNaziv() {
        Usluga usluga = new Usluga(null, "Opšti pregled", 2500.0, mockPoliklinika);
        assertEquals("Opšti pregled", usluga.getNaziv());
    }

    @Test
    void getCena() {
        Usluga usluga = new Usluga(null, "Opšti pregled", 2500.0, mockPoliklinika);
        assertEquals(2500.0, usluga.getCena());
    }

    @Test
    void setNaziv() {
        Usluga u = new Usluga();
        u.setNaziv("Ultrazvuk");
        assertEquals("Ultrazvuk", u.getNaziv());
    }

    @Test
    void setCena() {
        Usluga u = new Usluga();
        u.setCena(3000.0);
        assertEquals(3000.0, u.getCena());
    }

    @Test
    void setPoliklinika() {
        Usluga u = new Usluga();
        u.setPoliklinika(mockPoliklinika);
        assertEquals(mockPoliklinika, u.getPoliklinika());
    }

    @Test
    @DisplayName("Validacija treba da prođe za ispravan objekat Usluge")
    void validate_ValidUsluga_NoViolations() {
        Usluga u = new Usluga(null, "Opšti pregled", 2500.0, mockPoliklinika);

        Set<ConstraintViolation<Usluga>> violations = validator.validate(u);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je naziv usluge prazan")
    void validate_BlankNaziv_FailsValidation() {
        Usluga usluga = new Usluga(null, "", 2500.0, mockPoliklinika);

        Set<ConstraintViolation<Usluga>> violations = validator.validate(usluga);

        assertFalse(violations.isEmpty(), "Neuspešno, prazan naziv je prošao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Naziv usluge ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je cena manja od 1")
    void validate_MinCena_FailsValidation() {
        Usluga usluga = new Usluga(null, "Opšti pregled", 0.0, mockPoliklinika);

        Set<ConstraintViolation<Usluga>> violations = validator.validate(usluga);

        assertFalse(violations.isEmpty(), "Neuspešno, nula ili negativna cena je prošla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Cena usluge mora biti najmanje 1", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je poliklinika null")
    void validate_NullPoliklinika_FailsValidation() {
        Usluga usluga = new Usluga(null, "Opšti pregled", 2500.0, null);

        Set<ConstraintViolation<Usluga>> violations = validator.validate(usluga);

        assertFalse(violations.isEmpty(), "Neuspešno, null poliklinika je prošla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Poliklinika mora biti dodeljena usluzi", msg);
    }
}