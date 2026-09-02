package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.PacijentRequest;
import rs.zr.sa.poliklinika.dto.PacijentResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.repository.PacijentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacijentServiceImplTest {

    @Mock
    private PacijentRepository pacijentRepository;

    @InjectMocks
    private PacijentServiceImpl pacijentService;

    private Pacijent pacijent;
    private PacijentRequest pacijentRequest;

    @BeforeEach
    void setUp() {
        pacijent = new Pacijent();
        pacijent.setPacijentId(1L);
        pacijent.setIme("Petar");
        pacijent.setPrezime("Petrović");
        pacijent.setJmbg("0101990710001");
        pacijent.setEmail("petar@mail.com");
        pacijent.setTelefon("064123456");

        pacijentRequest = new PacijentRequest();
        pacijentRequest.setIme("Petar");
        pacijentRequest.setPrezime("Petrović");
        pacijentRequest.setJmbg("0101990710001");
        pacijentRequest.setEmail("petar@mail.com");
        pacijentRequest.setTelefon("064123456");
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih pacijenata")
    void findAll_ShouldReturnListOfPacijentResponses() {
        when(pacijentRepository.findAll()).thenReturn(List.of(pacijent));

        List<PacijentResponse> result = pacijentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Petar", result.get(0).getIme());
        verify(pacijentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje pacijenta po ID-ju")
    void findById_ExistingId_ShouldReturnPacijentResponse() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));

        PacijentResponse result = pacijentService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getPacijentId());
        assertEquals("Petar", result.getIme());
        verify(pacijentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada pacijent po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(pacijentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacijentService.findById(99L);
        });

        assertEquals("Pacijent sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(pacijentRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog pacijenta")
    void save_ValidRequest_ShouldReturnSavedPacijentResponse() {
        when(pacijentRepository.save(any(Pacijent.class))).thenReturn(pacijent);

        PacijentResponse result = pacijentService.save(pacijentRequest);

        assertNotNull(result);
        assertEquals("Petar", result.getIme());
        verify(pacijentRepository, times(1)).save(any(Pacijent.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojećeg pacijenta")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedPacijentResponse() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(pacijentRepository.save(any(Pacijent.class))).thenReturn(pacijent);

        pacijentRequest.setIme("Marko");
        PacijentResponse result = pacijentService.update(1L, pacijentRequest);

        assertNotNull(result);
        verify(pacijentRepository, times(1)).findById(1L);
        verify(pacijentRepository, times(1)).save(any(Pacijent.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako pacijent ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(pacijentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacijentService.update(99L, pacijentRequest);
        });

        assertEquals("Pacijent sa ID-jem 99 nije pronadjen za azuriranje.", exception.getMessage());
        verify(pacijentRepository, times(1)).findById(99L);
        verify(pacijentRepository, never()).save(any(Pacijent.class));
    }

    @Test
    @DisplayName("Uspešno brisanje pacijenta")
    void delete_ExistingId_ShouldDeletePacijent() {
        when(pacijentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pacijentRepository).deleteById(1L);

        assertDoesNotThrow(() -> pacijentService.delete(1L));

        verify(pacijentRepository, times(1)).existsById(1L);
        verify(pacijentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako pacijent ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(pacijentRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacijentService.delete(99L);
        });

        assertEquals("Pacijent sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(pacijentRepository, times(1)).existsById(99L);
        verify(pacijentRepository, never()).deleteById(anyLong());
    }
}