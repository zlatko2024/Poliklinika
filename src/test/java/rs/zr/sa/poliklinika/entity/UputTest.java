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

class UputTest {

    private Validator validator;
    private Pacijent mockPacijent;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockPacijent = new Pacijent(); // Pretpostavka da Pacijent ima prazan konstruktor
    }

    @Test
    void getBrojUputa() {
        Uput uput = new Uput(null, "UP-2026-002", LocalDate.of(2026, 9, 3), "Specijalistički pregled", "Hitno", mockPacijent);
        assertEquals("UP-2026-002", uput.getBrojUputa());
    }

    @Test
    void getDatumIzdavanja() {
        LocalDate datum = LocalDate.of(2026, 9, 3);
        Uput uput = new Uput(null, "UP-2026-002", datum, "Specijalistički pregled", "Hitno", mockPacijent);
        assertEquals(datum, uput.getDatumIzdavanja());
    }

    @Test
    void getDijagnoza() {
        Uput uput = new Uput(null, "UP-2026-002", LocalDate.of(2026, 9, 3), "Specijalistički pregled", "Hitno", mockPacijent);
        assertEquals("Specijalistički pregled", uput.getDijagnoza());
    }

    @Test
    void getNapomena() {
        Uput uput = new Uput(null, "UP-2026-002", LocalDate.of(2026, 9, 3), "Specijalistički pregled", "Hitno", mockPacijent);
        assertEquals("Hitno", uput.getNapomena());
    }

    @Test
    void setBrojUputa() {
        Uput u = new Uput();
        u.setBrojUputa("UP-2026-003");
        assertEquals("UP-2026-003", u.getBrojUputa());
    }

    @Test
    void setDijagnoza() {
        Uput u = new Uput();
        u.setDijagnoza("Kontrolni pregled");
        assertEquals("Kontrolni pregled", u.getDijagnoza());
    }

    @Test
    void setPacijent() {
        Uput u = new Uput();
        u.setPacijent(mockPacijent);
        assertEquals(mockPacijent, u.getPacijent());
    }

    @Test
    @DisplayName("Validacija treba da prođe za ispravan objekat Uputa")
    void validate_ValidUput_NoViolations() {
        Uput u = new Uput(null, "UP-2026-002", LocalDate.now(), "Specijalistički pregled", "Hitno", mockPacijent);

        Set<ConstraintViolation<Uput>> violations = validator.validate(u);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je broj uputa prazan")
    void validate_BlankBrojUputa_FailsValidation() {
        Uput uput = new Uput(null, "", LocalDate.now(), "Specijalistički pregled", "Hitno", mockPacijent);

        Set<ConstraintViolation<Uput>> violations = validator.validate(uput);

        assertFalse(violations.isEmpty(), "Neuspešno, prazan broj uputa je prošao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Broj uputa ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je datum izdavanja null")
    void validate_NullDatumIzdavanja_FailsValidation() {
        Uput uput = new Uput(null, "UP-2026-002", null, "Specijalistički pregled", "Hitno", mockPacijent);

        Set<ConstraintViolation<Uput>> violations = validator.validate(uput);

        assertFalse(violations.isEmpty(), "Neuspešno, null datum je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum izdavanja ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je dijagnoza prazna")
    void validate_BlankDijagnoza_FailsValidation() {
        Uput uput = new Uput(null, "UP-2026-002", LocalDate.now(), "", "Hitno", mockPacijent);

        Set<ConstraintViolation<Uput>> violations = validator.validate(uput);

        assertFalse(violations.isEmpty(), "Neuspešno, prazna dijagnoza je prošla");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Dijagnoza ne sme biti prazna", msg);
    }

    @Test
    @DisplayName("Validacija treba da feiluje kada je pacijent null")
    void validate_NullPacijent_FailsValidation() {
        Uput uput = new Uput(null, "UP-2026-002", LocalDate.now(), "Specijalistički pregled", "Hitno", null);

        Set<ConstraintViolation<Uput>> violations = validator.validate(uput);

        assertFalse(violations.isEmpty(), "Neuspešno, null pacijent je prošao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Pacijent mora biti dodeljen uputu", msg);
    }
}