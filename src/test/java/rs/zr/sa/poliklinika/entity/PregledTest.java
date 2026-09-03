package rs.zr.sa.poliklinika.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PregledTest {

    private Validator validator;
    private Pacijent mockPacijent;
    private Poliklinika mockPoliklinika;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockPacijent = new Pacijent();
        mockPoliklinika = new Poliklinika();
    }

    @Test
    void getDatum() {
        LocalDate datum = LocalDate.of(2026, 9, 3);
        LocalTime vreme = LocalTime.of(10, 30);
        Pregled pregled = new Pregled(null, datum, vreme, "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);
        assertEquals(datum, pregled.getDatum());
    }

    @Test
    void getVreme() {
        LocalDate datum = LocalDate.of(2026, 9, 3);
        LocalTime vreme = LocalTime.of(10, 30);
        Pregled pregled = new Pregled(null, datum, vreme, "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);
        assertEquals(vreme, pregled.getVreme());
    }

    @Test
    void getVrstaPregleda() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);
        assertEquals("Kardiološki pregled", pregled.getVrstaPregleda());
    }

    @Test
    void getStatus() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);
        assertEquals("Zakazan", pregled.getStatus());
    }

    @Test
    void setDatum() {
        Pregled p = new Pregled();
        LocalDate datum = LocalDate.now();
        p.setDatum(datum);
        assertEquals(datum, p.getDatum());
    }

    @Test
    @DisplayName("Testiranje nedozvoljene null vrednosti za setDatum sa validacijom")
    void setDatum_Null_ShouldFailValidation() {
        Pregled p = new Pregled();
        p.setDatum(null);
        p.setVreme(LocalTime.now());
        p.setVrstaPregleda("Kardiološki pregled");
        p.setStatus("Zakazan");
        p.setPacijent(mockPacijent);
        p.setPoliklinika(mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    void setVreme() {
        Pregled p = new Pregled();
        LocalTime vreme = LocalTime.of(12, 0);
        p.setVreme(vreme);
        assertEquals(vreme, p.getVreme());
    }

    @Test
    @DisplayName("Testiranje nedozvoljene null vrednosti za setVreme sa validacijom")
    void setVreme_Null_ShouldFailValidation() {
        Pregled p = new Pregled();
        p.setDatum(LocalDate.now());
        p.setVreme(null);
        p.setVrstaPregleda("Kardiološki pregled");
        p.setStatus("Zakazan");
        p.setPacijent(mockPacijent);
        p.setPoliklinika(mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    void setVrstaPregleda() {
        Pregled p = new Pregled();
        p.setVrstaPregleda("Ultrazvuk");
        assertEquals("Ultrazvuk", p.getVrstaPregleda());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Testiranje nedozvoljenih vrednosti za setVrstaPregleda sa validacijom")
    void setVrstaPregleda_InvalidValues_ShouldFailValidation(String invalidVrsta) {
        Pregled p = new Pregled();
        p.setDatum(LocalDate.now());
        p.setVreme(LocalTime.now());
        p.setVrstaPregleda(invalidVrsta);
        p.setStatus("Zakazan");
        p.setPacijent(mockPacijent);
        p.setPoliklinika(mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    void setStatus() {
        Pregled p = new Pregled();
        p.setStatus("Završen");
        assertEquals("Završen", p.getStatus());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Testiranje nedozvoljenih vrednosti za setStatus sa validacijom")
    void setStatus_InvalidValues_ShouldFailValidation(String invalidStatus) {
        Pregled p = new Pregled();
        p.setDatum(LocalDate.now());
        p.setVreme(LocalTime.now());
        p.setVrstaPregleda("Kardiološki pregled");
        p.setStatus(invalidStatus);
        p.setPacijent(mockPacijent);
        p.setPoliklinika(mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(p);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravan objekat Pregleda")
    void validate_ValidPregled_NoViolations() {
        Pregled p = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(p);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je datum pregleda null")
    void validate_NullDatum_FailsValidation() {
        Pregled pregled = new Pregled(null, null, LocalTime.now(), "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, null datum je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum pregleda ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je vreme pregleda null")
    void validate_NullVreme_FailsValidation() {
        Pregled pregled = new Pregled(null, LocalDate.now(), null, "Kardiološki pregled", "Zakazan", mockPacijent, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, null vreme je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Vreme pregleda ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je vrsta pregleda prazna")
    void validate_BlankVrstaPregleda_FailsValidation() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "", "Zakazan", mockPacijent, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, prazna vrsta pregleda je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Vrsta pregleda ne sme biti prazna", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je status pregleda prazan")
    void validate_BlankStatus_FailsValidation() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "", mockPacijent, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan status je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Status pregleda ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je pacijent null")
    void validate_NullPacijent_FailsValidation() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "Zakazan", null, mockPoliklinika);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, null pacijent je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Pacijent mora biti dodeljen pregledu", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je poliklinika null")
    void validate_NullPoliklinika_FailsValidation() {
        Pregled pregled = new Pregled(null, LocalDate.now(), LocalTime.now(), "Kardiološki pregled", "Zakazan", mockPacijent, null);

        Set<ConstraintViolation<Pregled>> violations = validator.validate(pregled);

        assertFalse(violations.isEmpty(), "Neuspesno, null poliklinika je prosla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Poliklinika mora biti dodeljena pregledu", msg);
    }
}