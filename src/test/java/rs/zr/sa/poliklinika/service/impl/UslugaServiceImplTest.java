package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.UslugaRequest;
import rs.zr.sa.poliklinika.dto.UslugaResponse;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Usluga;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;
import rs.zr.sa.poliklinika.repository.UslugaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UslugaServiceImplTest {

    @Mock
    private UslugaRepository uslugaRepository;

    @Mock
    private PoliklinikaRepository poliklinikaRepository;

    @InjectMocks
    private UslugaServiceImpl uslugaService;

    private Usluga usluga;
    private Poliklinika poliklinika;
    private UslugaRequest uslugaRequest;

    @BeforeEach
    void setUp() {
        poliklinika = new Poliklinika();
        poliklinika.setPoliklinikaId(1L);
        poliklinika.setNaziv("PoliklinikaApp Centar");
        poliklinika.setAdresa("Nemanjina 1");
        poliklinika.setKontaktTelefon("023123456");

        usluga = new Usluga();
        usluga.setUslugaId(1L);
        usluga.setNaziv("Specijalistički pregled");
        usluga.setCena(3000.0);
        usluga.setPoliklinika(poliklinika);

        uslugaRequest = new UslugaRequest();
        uslugaRequest.setNaziv("Specijalistički pregled");
        uslugaRequest.setCena(3000.0);
        uslugaRequest.setPoliklinikaId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih usluga")
    void findAll_ShouldReturnListOfUslugaResponses() {
        when(uslugaRepository.findAll()).thenReturn(List.of(usluga));

        List<UslugaResponse> result = uslugaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Specijalistički pregled", result.get(0).getNaziv());
        verify(uslugaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje usluge po ID-ju")
    void findById_ExistingId_ShouldReturnUslugaResponse() {
        when(uslugaRepository.findById(1L)).thenReturn(Optional.of(usluga));

        UslugaResponse result = uslugaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUslugaId());
        assertEquals("Specijalistički pregled", result.getNaziv());
        verify(uslugaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada usluga po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(uslugaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uslugaService.findById(99L);
        });

        assertEquals("Usluga sa ID-jem 99 nije pronadjena.", exception.getMessage());
        verify(uslugaRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje nove usluge")
    void save_ValidRequest_ShouldReturnSavedUslugaResponse() {
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));
        when(uslugaRepository.save(any(Usluga.class))).thenReturn(usluga);

        UslugaResponse result = uslugaService.save(uslugaRequest);

        assertNotNull(result);
        assertEquals("Specijalistički pregled", result.getNaziv());
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(uslugaRepository, times(1)).save(any(Usluga.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako poliklinika ne postoji")
    void save_NonExistingPoliklinika_ShouldThrowException() {
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uslugaService.save(uslugaRequest);
        });

        assertEquals("PoliklinikaApp sa ID-jem 1 nije pronadjena.", exception.getMessage());
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(uslugaRepository, never()).save(any(Usluga.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojeće usluge")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedUslugaResponse() {
        when(uslugaRepository.findById(1L)).thenReturn(Optional.of(usluga));
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));
        when(uslugaRepository.save(any(Usluga.class))).thenReturn(usluga);

        uslugaRequest.setNaziv("Kontrolni pregled");
        UslugaResponse result = uslugaService.update(1L, uslugaRequest);

        assertNotNull(result);
        verify(uslugaRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(uslugaRepository, times(1)).save(any(Usluga.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako usluga ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(uslugaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uslugaService.update(99L, uslugaRequest);
        });

        assertEquals("Usluga sa ID-jem 99 nije pronadjena za azuriranje.", exception.getMessage());
        verify(uslugaRepository, times(1)).findById(99L);
        verify(uslugaRepository, never()).save(any(Usluga.class));
    }

    @Test
    @DisplayName("Uspešno brisanje usluge")
    void delete_ExistingId_ShouldDeleteUsluga() {
        when(uslugaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(uslugaRepository).deleteById(1L);

        assertDoesNotThrow(() -> uslugaService.delete(1L));

        verify(uslugaRepository, times(1)).existsById(1L);
        verify(uslugaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako usluga ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(uslugaRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uslugaService.delete(99L);
        });

        assertEquals("Usluga sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(uslugaRepository, times(1)).existsById(99L);
        verify(uslugaRepository, never()).deleteById(anyLong());
    }
}