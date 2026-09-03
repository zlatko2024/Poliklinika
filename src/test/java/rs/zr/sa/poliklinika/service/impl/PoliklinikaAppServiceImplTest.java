package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.PoliklinikaRequest;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoliklinikaAppServiceImplTest {

    @Mock
    private PoliklinikaRepository poliklinikaRepository;

    @InjectMocks
    private PoliklinikaServiceImpl poliklinikaService;

    private Poliklinika poliklinika;
    private PoliklinikaRequest poliklinikaRequest;

    @BeforeEach
    void setUp() {
        poliklinika = new Poliklinika();
        poliklinika.setPoliklinikaId(1L);
        poliklinika.setNaziv("PoliklinikaApp Centar");
        poliklinika.setAdresa("Nemanjina 1");
        poliklinika.setKontaktTelefon("023123456");

        poliklinikaRequest = new PoliklinikaRequest();
        poliklinikaRequest.setNaziv("PoliklinikaApp Centar");
        poliklinikaRequest.setAdresa("Nemanjina 1");
        poliklinikaRequest.setKontaktTelefon("023123456");
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih poliklinika")
    void findAll_ShouldReturnListOfPoliklinikaResponses() {
        when(poliklinikaRepository.findAll()).thenReturn(List.of(poliklinika));

        List<PoliklinikaResponse> result = poliklinikaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PoliklinikaApp Centar", result.get(0).getNaziv());
        verify(poliklinikaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje poliklinike po ID-ju")
    void findById_ExistingId_ShouldReturnPoliklinikaResponse() {
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));

        PoliklinikaResponse result = poliklinikaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getPoliklinikaId());
        assertEquals("PoliklinikaApp Centar", result.getNaziv());
        verify(poliklinikaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada poliklinika po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(poliklinikaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            poliklinikaService.findById(99L);
        });

        assertEquals("PoliklinikaApp sa ID-jem 99 nije pronadjena.", exception.getMessage());
        verify(poliklinikaRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje nove poliklinike")
    void save_ValidRequest_ShouldReturnSavedPoliklinikaResponse() {
        when(poliklinikaRepository.save(any(Poliklinika.class))).thenReturn(poliklinika);

        PoliklinikaResponse result = poliklinikaService.save(poliklinikaRequest);

        assertNotNull(result);
        assertEquals("PoliklinikaApp Centar", result.getNaziv());
        verify(poliklinikaRepository, times(1)).save(any(Poliklinika.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojeće poliklinike")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedPoliklinikaResponse() {
        when(poliklinikaRepository.findById(1L)).thenReturn(Optional.of(poliklinika));
        when(poliklinikaRepository.save(any(Poliklinika.class))).thenReturn(poliklinika);

        poliklinikaRequest.setNaziv("Nova PoliklinikaApp");
        PoliklinikaResponse result = poliklinikaService.update(1L, poliklinikaRequest);

        assertNotNull(result);
        verify(poliklinikaRepository, times(1)).findById(1L);
        verify(poliklinikaRepository, times(1)).save(any(Poliklinika.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako poliklinika ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(poliklinikaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            poliklinikaService.update(99L, poliklinikaRequest);
        });

        assertEquals("PoliklinikaApp sa ID-jem 99 nije pronadjena za azuriranje.", exception.getMessage());
        verify(poliklinikaRepository, times(1)).findById(99L);
        verify(poliklinikaRepository, never()).save(any(Poliklinika.class));
    }

    @Test
    @DisplayName("Uspešno brisanje poliklinike")
    void delete_ExistingId_ShouldDeletePoliklinika() {
        when(poliklinikaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(poliklinikaRepository).deleteById(1L);

        assertDoesNotThrow(() -> poliklinikaService.delete(1L));

        verify(poliklinikaRepository, times(1)).existsById(1L);
        verify(poliklinikaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako poliklinika ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(poliklinikaRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            poliklinikaService.delete(99L);
        });

        assertEquals("PoliklinikaApp sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(poliklinikaRepository, times(1)).existsById(99L);
        verify(poliklinikaRepository, never()).deleteById(anyLong());
    }
}