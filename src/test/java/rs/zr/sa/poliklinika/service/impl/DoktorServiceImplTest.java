package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.DoktorRequest;
import rs.zr.sa.poliklinika.dto.DoktorResponse;
import rs.zr.sa.poliklinika.entity.Doktor;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Usluga;
import rs.zr.sa.poliklinika.repository.DoktorRepository;
import rs.zr.sa.poliklinika.repository.UslugaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoktorServiceImplTest {

    @Mock
    private DoktorRepository doktorRepository;

    @Mock
    private UslugaRepository uslugaRepository;

    @InjectMocks
    private DoktorServiceImpl doktorService;

    private Doktor doktor;
    private Usluga usluga;
    private Poliklinika poliklinika;
    private DoktorRequest doktorRequest;

    @BeforeEach
    void setUp() {
        poliklinika = new Poliklinika(1L, "Poliklinika Centar", "Nemanjina 1", "023123456", null);
        usluga = new Usluga(1L, "Kardiološki pregled", 3000.0, poliklinika);

        doktor = new Doktor(1L, "Marko", "Marković", "LIC123", "Kardiolog", usluga);

        doktorRequest = new DoktorRequest();
        doktorRequest.setIme("Marko");
        doktorRequest.setPrezime("Marković");
        doktorRequest.setBrojLicence("LIC123");
        doktorRequest.setSpecijalnost("Kardiolog");
        doktorRequest.setUslugaId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih doktora")
    void findAll_ShouldReturnListOfDoktorResponses() {
        when(doktorRepository.findAll()).thenReturn(List.of(doktor));

        List<DoktorResponse> result = doktorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Marko", result.get(0).getIme());
        verify(doktorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje doktora po ID-ju")
    void findById_ExistingId_ShouldReturnDoktorResponse() {
        when(doktorRepository.findById(1L)).thenReturn(Optional.of(doktor));

        DoktorResponse result = doktorService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getDoktorId());
        assertEquals("Marko", result.getIme());
        verify(doktorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada doktor po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(doktorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doktorService.findById(99L);
        });

        assertEquals("Doktor sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(doktorRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog doktora")
    void save_ValidRequest_ShouldReturnSavedDoktorResponse() {
        when(uslugaRepository.findById(1L)).thenReturn(Optional.of(usluga));
        when(doktorRepository.save(any(Doktor.class))).thenReturn(doktor);

        DoktorResponse result = doktorService.save(doktorRequest);

        assertNotNull(result);
        assertEquals("Marko", result.getIme());
        verify(uslugaRepository, times(1)).findById(1L);
        verify(doktorRepository, times(1)).save(any(Doktor.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako usluga ne postoji")
    void save_NonExistingUsluga_ShouldThrowException() {
        when(uslugaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doktorService.save(doktorRequest);
        });

        assertEquals("Usluga sa ID-jem 1 nije pronadjena.", exception.getMessage());
        verify(uslugaRepository, times(1)).findById(1L);
        verify(doktorRepository, never()).save(any(Doktor.class));
    }

    @Test
    @DisplayName("Uspešno brisanje doktora")
    void delete_ExistingId_ShouldDeleteDoktor() {
        when(doktorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(doktorRepository).deleteById(1L);

        assertDoesNotThrow(() -> doktorService.delete(1L));

        verify(doktorRepository, times(1)).existsById(1L);
        verify(doktorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako doktor ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(doktorRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doktorService.delete(99L);
        });

        assertEquals("Doktor sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(doktorRepository, times(1)).existsById(99L);
        verify(doktorRepository, never()).deleteById(anyLong());
    }
}