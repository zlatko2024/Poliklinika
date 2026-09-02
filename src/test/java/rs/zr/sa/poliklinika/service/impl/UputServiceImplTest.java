package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.UputRequest;
import rs.zr.sa.poliklinika.dto.UputResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.entity.Uput;
import rs.zr.sa.poliklinika.repository.PacijentRepository;
import rs.zr.sa.poliklinika.repository.UputRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UputServiceImplTest {

    @Mock
    private UputRepository uputRepository;

    @Mock
    private PacijentRepository pacijentRepository;

    @InjectMocks
    private UputServiceImpl uputService;

    private Uput uput;
    private Pacijent pacijent;
    private UputRequest uputRequest;

    @BeforeEach
    void setUp() {
        pacijent = new Pacijent();
        pacijent.setPacijentId(1L);
        pacijent.setIme("Petar");
        pacijent.setPrezime("Petrović");
        pacijent.setJmbg("0101990710001");
        pacijent.setEmail("petar@mail.com");
        pacijent.setTelefon("064123456");

        uput = new Uput();
        uput.setUputId(1L);
        uput.setBrojUputa("UPUT-01");
        uput.setDatumIzdavanja(LocalDate.now());
        uput.setDijagnoza("Hipertenzija");
        uput.setNapomena("Hitno");
        uput.setPacijent(pacijent);

        uputRequest = new UputRequest();
        uputRequest.setBrojUputa("UPUT-01");
        uputRequest.setDatumIzdavanja(LocalDate.now());
        uputRequest.setDijagnoza("Hipertenzija");
        uputRequest.setNapomena("Hitno");
        uputRequest.setPacijentId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih uputa")
    void findAll_ShouldReturnListOfUputResponses() {
        when(uputRepository.findAll()).thenReturn(List.of(uput));

        List<UputResponse> result = uputService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("UPUT-01", result.get(0).getBrojUputa());
        verify(uputRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje uputa po ID-ju")
    void findById_ExistingId_ShouldReturnUputResponse() {
        when(uputRepository.findById(1L)).thenReturn(Optional.of(uput));

        UputResponse result = uputService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUputId());
        assertEquals("UPUT-01", result.getBrojUputa());
        verify(uputRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada uput po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(uputRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uputService.findById(99L);
        });

        assertEquals("Uput sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(uputRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog uputa")
    void save_ValidRequest_ShouldReturnSavedUputResponse() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(uputRepository.save(any(Uput.class))).thenReturn(uput);

        UputResponse result = uputService.save(uputRequest);

        assertNotNull(result);
        assertEquals("UPUT-01", result.getBrojUputa());
        verify(pacijentRepository, times(1)).findById(1L);
        verify(uputRepository, times(1)).save(any(Uput.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako pacijent ne postoji")
    void save_NonExistingPacijent_ShouldThrowException() {
        when(pacijentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uputService.save(uputRequest);
        });

        assertEquals("Pacijent sa ID-jem 1 nije pronadjen.", exception.getMessage());
        verify(pacijentRepository, times(1)).findById(1L);
        verify(uputRepository, never()).save(any(Uput.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojećeg uputa")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedUputResponse() {
        when(uputRepository.findById(1L)).thenReturn(Optional.of(uput));
        when(pacijentRepository.findById(1L)).thenReturn(Optional.of(pacijent));
        when(uputRepository.save(any(Uput.class))).thenReturn(uput);

        uputRequest.setDijagnoza("Kontrola");
        UputResponse result = uputService.update(1L, uputRequest);

        assertNotNull(result);
        verify(uputRepository, times(1)).findById(1L);
        verify(pacijentRepository, times(1)).findById(1L);
        verify(uputRepository, times(1)).save(any(Uput.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako uput ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(uputRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uputService.update(99L, uputRequest);
        });

        assertEquals("Uput sa ID-jem 99 nije pronadjen za azuriranje.", exception.getMessage());
        verify(uputRepository, times(1)).findById(99L);
        verify(uputRepository, never()).save(any(Uput.class));
    }

    @Test
    @DisplayName("Uspešno brisanje uputa")
    void delete_ExistingId_ShouldDeleteUput() {
        when(uputRepository.existsById(1L)).thenReturn(true);
        doNothing().when(uputRepository).deleteById(1L);

        assertDoesNotThrow(() -> uputService.delete(1L));

        verify(uputRepository, times(1)).existsById(1L);
        verify(uputRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako uput ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(uputRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            uputService.delete(99L);
        });

        assertEquals("Uput sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(uputRepository, times(1)).existsById(99L);
        verify(uputRepository, never()).deleteById(anyLong());
    }
}