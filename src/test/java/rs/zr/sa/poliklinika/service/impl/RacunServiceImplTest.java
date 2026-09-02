package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.RacunRequest;
import rs.zr.sa.poliklinika.dto.RacunResponse;
import rs.zr.sa.poliklinika.entity.*;
import rs.zr.sa.poliklinika.repository.DoktorRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;
import rs.zr.sa.poliklinika.repository.RacunRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacunServiceImplTest {

    @Mock
    private RacunRepository racunRepository;

    @Mock
    private PregledRepository pregledRepository;

    @Mock
    private DoktorRepository doktorRepository;

    @InjectMocks
    private RacunServiceImpl racunService;

    private Racun racun;
    private Pregled pregled;
    private Doktor doktor;
    private Pacijent pacijent;
    private Poliklinika poliklinika;
    private Usluga usluga;
    private RacunRequest racunRequest;

    @BeforeEach
    void setUp() {
        pacijent = new Pacijent();
        pacijent.setPacijentId(1L);
        pacijent.setIme("Petar");
        pacijent.setPrezime("Petrović");
        pacijent.setJmbg("0101990710001");
        pacijent.setEmail("petar@mail.com");
        pacijent.setTelefon("064123456");

        poliklinika = new Poliklinika();
        poliklinika.setPoliklinikaId(1L);
        poliklinika.setNaziv("Poliklinika Centar");
        poliklinika.setAdresa("Nemanjina 1");
        poliklinika.setKontaktTelefon("023123456");

        pregled = new Pregled();
        pregled.setPregledId(1L);
        pregled.setDatum(LocalDate.now());
        pregled.setVreme(LocalTime.of(10, 0));
        pregled.setVrstaPregleda("Pregled");
        pregled.setStatus("Zakazan");
        pregled.setPacijent(pacijent);
        pregled.setPoliklinika(poliklinika);

        usluga = new Usluga();
        usluga.setUslugaId(1L);
        usluga.setNaziv("Specijalistički pregled");
        usluga.setCena(3000.00);
        usluga.setPoliklinika(poliklinika);

        doktor = new Doktor();
        doktor.setDoktorId(1L);
        doktor.setIme("Milan");
        doktor.setPrezime("Milanović");
        doktor.setBrojLicence("LIC-123");
        doktor.setSpecijalnost("Kardiolog");
        doktor.setUsluga(usluga);

        racun = new Racun();
        racun.setRacunId(1L);
        racun.setBrojRacuna("RAC-01");
        racun.setDatumIzdavanja(LocalDate.now());
        racun.setUkupanIznos(3000.00);
        racun.setStatusPlacanja("Plaćeno");
        racun.setPregled(pregled);
        racun.setDoktor(doktor);

        racunRequest = new RacunRequest();
        racunRequest.setBrojRacuna("RAC-01");
        racunRequest.setDatumIzdavanja(LocalDate.now());
        racunRequest.setUkupanIznos(3000.00);
        racunRequest.setStatusPlacanja("Plaćeno");
        racunRequest.setPregledId(1L);
        racunRequest.setDoktorId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih računa")
    void findAll_ShouldReturnListOfRacunResponses() {
        when(racunRepository.findAll()).thenReturn(List.of(racun));

        List<RacunResponse> result = racunService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("RAC-01", result.get(0).getBrojRacuna());
        verify(racunRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje računa po ID-ju")
    void findById_ExistingId_ShouldReturnRacunResponse() {
        when(racunRepository.findById(1L)).thenReturn(Optional.of(racun));

        RacunResponse result = racunService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getRacunId());
        assertEquals("RAC-01", result.getBrojRacuna());
        verify(racunRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada račun po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(racunRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            racunService.findById(99L);
        });

        assertEquals("Racun sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(racunRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog računa")
    void save_ValidRequest_ShouldReturnSavedRacunResponse() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(doktorRepository.findById(1L)).thenReturn(Optional.of(doktor));
        when(racunRepository.save(any(Racun.class))).thenReturn(racun);

        RacunResponse result = racunService.save(racunRequest);

        assertNotNull(result);
        assertEquals("RAC-01", result.getBrojRacuna());
        verify(pregledRepository, times(1)).findById(1L);
        verify(doktorRepository, times(1)).findById(1L);
        verify(racunRepository, times(1)).save(any(Racun.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako pregled ne postoji")
    void save_NonExistingPregled_ShouldThrowException() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            racunService.save(racunRequest);
        });

        assertEquals("Pregled sa ID-jem 1 nije pronadjen.", exception.getMessage());
        verify(pregledRepository, times(1)).findById(1L);
        verify(doktorRepository, never()).findById(anyLong());
        verify(racunRepository, never()).save(any(Racun.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako doktor ne postoji")
    void save_NonExistingDoktor_ShouldThrowException() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(doktorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            racunService.save(racunRequest);
        });

        assertEquals("Doktor sa ID-jem 1 nije pronadjen.", exception.getMessage());
        verify(pregledRepository, times(1)).findById(1L);
        verify(doktorRepository, times(1)).findById(1L);
        verify(racunRepository, never()).save(any(Racun.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojećeg računa")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedRacunResponse() {
        when(racunRepository.findById(1L)).thenReturn(Optional.of(racun));
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(doktorRepository.findById(1L)).thenReturn(Optional.of(doktor));
        when(racunRepository.save(any(Racun.class))).thenReturn(racun);

        racunRequest.setStatusPlacanja("Stornirano");
        RacunResponse result = racunService.update(1L, racunRequest);

        assertNotNull(result);
        verify(racunRepository, times(1)).findById(1L);
        verify(pregledRepository, times(1)).findById(1L);
        verify(doktorRepository, times(1)).findById(1L);
        verify(racunRepository, times(1)).save(any(Racun.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako račun ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(racunRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            racunService.update(99L, racunRequest);
        });

        assertEquals("Racun sa ID-jem 99 nije pronadjen za azuriranje.", exception.getMessage());
        verify(racunRepository, times(1)).findById(99L);
        verify(racunRepository, never()).save(any(Racun.class));
    }

    @Test
    @DisplayName("Uspešno brisanje računa")
    void delete_ExistingId_ShouldDeleteRacun() {
        when(racunRepository.existsById(1L)).thenReturn(true);
        doNothing().when(racunRepository).deleteById(1L);

        assertDoesNotThrow(() -> racunService.delete(1L));

        verify(racunRepository, times(1)).existsById(1L);
        verify(racunRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacajnje izuzetka pri brisanju ako račun ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(racunRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            racunService.delete(99L);
        });

        assertEquals("Racun sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(racunRepository, times(1)).existsById(99L);
        verify(racunRepository, never()).deleteById(anyLong());
    }
}