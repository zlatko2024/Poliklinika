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

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NalazTest {

    private Validator validator;
    private Pregled mockPregled;
    private Uput mockUput;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        mockPregled = new Pregled(); // Pretpostavka da Pregled ima prazan konstruktor
        mockUput = new Uput();       // Pretpostavka da Uput ima prazan konstruktor
    }

    @Test
    void getOpis() {
        Nalaz nalaz = new Nalaz(null, "Redovan pregled", "U redu", "Mirovanje", LocalDate.of(2026, 9, 3), mockPregled, mockUput);
        assertEquals("Redovan pregled", nalaz.getOpis());
    }

    @Test
    void getZakljucak() {
        Nalaz nalaz = new Nalaz(null, "Redovan pregled", "U redu", "Mirovanje", LocalDate.of(2026, 9, 3), mockPregled, mockUput);
        assertEquals("U redu", nalaz.getZakljucak());
    }

    @Test
    void getPreporuka() {
        Nalaz nalaz = new Nalaz(null, "Redovan pregled", "U redu", "Mirovanje", LocalDate.of(2026, 9, 3), mockPregled, mockUput);
        assertEquals("Mirovanje", nalaz.getPreporuka());
    }

    @Test
    void getDatumNalaza() {
        LocalDate datum = LocalDate.of(2026, 9, 3);
        Nalaz nalaz = new Nalaz(null, "Redovan pregled", "U redu", "Mirovanje", datum, mockPregled, mockUput);
        assertEquals(datum, nalaz.getDatumNalaza());
    }

    @Test
    void setOpis() {
        Nalaz n = new Nalaz();
        n.setOpis("Kontrolni pregled");
        assertEquals("Kontrolni pregled", n.getOpis());
    }

    @Test
    void setDatumNalaza() {
        Nalaz n = new Nalaz();
        LocalDate datum = LocalDate.now();
        n.setDatumNalaza(datum);
        assertEquals(datum, n.getDatumNalaza());
    }

    @Test
    void setPregled() {
        Nalaz n = new Nalaz();
        n.setPregled(mockPregled);
        assertEquals(mockPregled, n.getPregled());
    }

    @Test
    void setUput() {
        Nalaz n = new Nalaz();
        n.setUput(mockUput);
        assertEquals(mockUput, n.getUput());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravan objekat Nalaza")
    void validate_ValidNalaz_NoViolations() {
        Nalaz n = new Nalaz(null, "Opis nalaza", "Zakljucak", "Preporuka", LocalDate.now(), mockPregled, mockUput);

        Set<ConstraintViolation<Nalaz>> violations = validator.validate(n);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je opis prazan")
    void validate_BlankOpis_FailsValidation() {
        Nalaz nalaz = new Nalaz(null, "", "Zakljucak", "Preporuka", LocalDate.now(), mockPregled, mockUput);

        Set<ConstraintViolation<Nalaz>> violations = validator.validate(nalaz);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan opis je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Opis nalaza ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je datum nalaza null")
    void validate_NullDatumNalaza_FailsValidation() {
        Nalaz nalaz = new Nalaz(null, "Opis nalaza", "Zakljucak", "Preporuka", null, mockPregled, mockUput);

        Set<ConstraintViolation<Nalaz>> violations = validator.validate(nalaz);

        assertFalse(violations.isEmpty(), "Neuspesno, null datum je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum nalaza ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je pregled null")
    void validate_NullPregled_FailsValidation() {
        Nalaz nalaz = new Nalaz(null, "Opis nalaza", "Zakljucak", "Preporuka", LocalDate.now(), null, mockUput);

        Set<ConstraintViolation<Nalaz>> violations = validator.validate(nalaz);

        assertFalse(violations.isEmpty(), "Neuspesno, null pregled je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Pregled mora biti dodeljen nalazu", msg);
    }
}