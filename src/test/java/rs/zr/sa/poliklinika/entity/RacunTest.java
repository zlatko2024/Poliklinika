package rs.zr.sa.poliklinika.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RacunTest {

    private Validator validator;
    private Pregled mockPregled;
    private Doktor mockDoktor;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockPregled = new Pregled();   // Pretpostavka da Pregled ima prazan konstruktor
        mockDoktor = new Doktor();     // Pretpostavka da Doktor ima prazan konstruktor
    }

    @Test
    void getBrojRacuna() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.of(2026, 9, 3), 5000.0, "Plaćeno", mockPregled, mockDoktor);
        assertEquals("RAC-2026-001", racun.getBrojRacuna());
    }

    @Test
    void getDatumIzdavanja() {
        LocalDate datum = LocalDate.of(2026, 9, 3);
        Racun racun = new Racun(null, "RAC-2026-001", datum, 5000.0, "Plaćeno", mockPregled, mockDoktor);
        assertEquals(datum, racun.getDatumIzdavanja());
    }

    @Test
    void getUkupanIznos() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.of(2026, 9, 3), 5000.0, "Plaćeno", mockPregled, mockDoktor);
        assertEquals(5000.0, racun.getUkupanIznos());
    }

    @Test
    void getStatusPlacanja() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.of(2026, 9, 3), 5000.0, "Plaćeno", mockPregled, mockDoktor);
        assertEquals("Plaćeno", racun.getStatusPlacanja());
    }

    @Test
    void setBrojRacuna() {
        Racun r = new Racun();
        r.setBrojRacuna("RAC-2026-002");
        assertEquals("RAC-2026-002", r.getBrojRacuna());
    }

    @Test
    void setUkupanIznos() {
        Racun r = new Racun();
        r.setUkupanIznos(3500.0);
        assertEquals(3500.0, r.getUkupanIznos());
    }

    @Test
    void setStatusPlacanja() {
        Racun r = new Racun();
        r.setStatusPlacanja("Nije plaćeno");
        assertEquals("Nije plaćeno", r.getStatusPlacanja());
    }

    @Test
    @DisplayName("Validacija treba da prođe za ispravan objekat Računa")
    void validate_ValidRacun_NoViolations() {
        Racun r = new Racun(null, "RAC-2026-001", LocalDate.now(), 5000.0, "Plaćeno", mockPregled, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je broj računa prazan")
    void validate_BlankBrojRacuna_FailsValidation() {
        Racun racun = new Racun(null, "", LocalDate.now(), 5000.0, "Plaćeno", mockPregled, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, prazan broj računa je prošao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Broj racuna ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je datum izdavanja null")
    void validate_NullDatumIzdavanja_FailsValidation() {
        Racun racun = new Racun(null, "RAC-2026-001", null, 5000.0, "Plaćeno", mockPregled, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, null datum je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum izdavanja racuna ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je ukupan iznos manji od nule")
    void validate_NegativeUkupanIznos_FailsValidation() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.now(), -150.0, "Plaćeno", mockPregled, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, negativan iznos je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Ukupan iznos mora biti veci ili jednak nuli", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je status plaćanja prazan")
    void validate_BlankStatusPlacanja_FailsValidation() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.now(), 5000.0, "", mockPregled, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, prazan status plaćanja je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Status placanja ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je pregled null")
    void validate_NullPregled_FailsValidation() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.now(), 5000.0, "Plaćeno", null, mockDoktor);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, null pregled je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Pregled mora biti dodeljen racunu", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je doktor null")
    void validate_NullDoktor_FailsValidation() {
        Racun racun = new Racun(null, "RAC-2026-001", LocalDate.now(), 5000.0, "Plaćeno", mockPregled, null);

        Set<ConstraintViolation<Racun>> violations = validator.validate(racun);

        assertFalse(violations.isEmpty(), "Neuspešno, null doktor je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Doktor mora biti dodeljen racunu", msg);
    }
}