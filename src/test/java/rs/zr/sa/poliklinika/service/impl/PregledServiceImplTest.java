package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.PregledRequest;
import rs.zr.sa.poliklinika.dto.PregledResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Pregled;
import rs.zr.sa.poliklinika.repository.PacijentRepository;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PregledServiceImplTest {

    @Mock
    private PregledRepository pregledRepository;

    @Mock
    private PacijentRepository pacijentRepository;

    @Mock
    private PoliklinikaRepository poliklinikaRepository;

    @InjectMocks
    private PregledServiceImpl pregledService;

    private Pregled pregled;
    private Pacijent pacijent;
    private Poliklinika poliklinika;
    private PregledRequest pregledRequest;

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
        pregled.setVrstaPregleda("Kardiološki pregled");
        pregled.setStatus("Zakazan");
        pregled.setPacijent(pacijent);
        pregled.setPoliklinika(poliklinika);

        pregledRequest = new PregledRequest();
        pregledRequest.setDatum(LocalDate.now());
        pregledRequest.setVreme(LocalTime.of(10, 0));
        pregledRequest.setVrstaPregleda("Kardiološki pregled");
        pregledRequest.setStatus("Zakazan");
        pregledRequest.setPacijentId(1L);
        pregledRequest.setPoliklinikaId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih pregleda")
    void findAll_ShouldReturnListOfPregledResponses() {
        when(pregledRepository.findAll()).thenReturn(List.of(pregled));

        List<PregledResponse> result = pregledService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Kardiološki pregled", result.get(0).getVrstaPregleda());
        verify(pregledRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje pregleda po ID-ju")
    void findById_ExistingId_ShouldReturnPregledResponse() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));

        PregledResponse result = pregledService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getPregledId());
        assertEquals("Zakazan", result.getStatus());
        verify(pregledRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada pregled po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(pregledRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pregledService.findById(99L);
        });

        assertEquals("Pregled sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(pregledRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog pregleda")
    void save_ValidRequest_ShouldReturnSavedPregledResponse() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));
        when(pregledRepository.save(any(Pregled.class))).thenReturn(pregled);

        PregledResponse result = pregledService.save(pregledRequest);

        assertNotNull(result);
        assertEquals("Kardiološki pregled", result.getVrstaPregleda());
        verify(pacijentRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(pregledRepository, times(1)).save(any(Pregled.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako pacijent ne postoji")
    void save_NonExistingPacijent_ShouldThrowException() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pregledService.save(pregledRequest);
        });

        assertEquals("Pacijent sa ID-jem 1 nije pronadjen.", exception.getMessage());
        verify(pacijentRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, never()).findById(anyLong());
        verify(pregledRepository, never()).save(any(Pregled.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako poliklinika ne postoji")
    void save_NonExistingPoliklinika_ShouldThrowException() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pregledService.save(pregledRequest);
        });

        assertEquals("Poliklinika sa ID-jem 1 nije pronadjena.", exception.getMessage());
        verify(pacijentRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(pregledRepository, never()).save(any(Pregled.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojećeg pregleda")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedPregledResponse() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));
        when(pregledRepository.save(any(Pregled.class))).thenReturn(pregled);

        pregledRequest.setStatus("Završen");
        PregledResponse result = pregledService.update(1L, pregledRequest);

        assertNotNull(result);
        verify(pregledRepository, times(1)).findById(1L);
        verify(pacijentRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(pregledRepository, times(1)).save(any(Pregled.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako pregled ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(pregledRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pregledService.update(99L, pregledRequest);
        });

        assertEquals("Pregled sa ID-jem 99 nije pronadjen za azuriranje.", exception.getMessage());
        verify(pregledRepository, times(1)).findById(99L);
        verify(pregledRepository, never()).save(any(Pregled.class));
    }

    @Test
    @DisplayName("Uspešno brisanje pregleda")
    void delete_ExistingId_ShouldDeletePregled() {
        when(pregledRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pregledRepository).deleteById(1L);

        assertDoesNotThrow(() -> pregledService.delete(1L));

        verify(pregledRepository, times(1)).existsById(1L);
        verify(pregledRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako pregled ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(pregledRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pregledService.delete(99L);
        });

        assertEquals("Pregled sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(pregledRepository, times(1)).existsById(99L);
        verify(pregledRepository, never()).deleteById(anyLong());
    }
}