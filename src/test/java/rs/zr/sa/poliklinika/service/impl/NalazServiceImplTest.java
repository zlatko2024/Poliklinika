package rs.zr.sa.poliklinika.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.zr.sa.poliklinika.dto.NalazRequest;
import rs.zr.sa.poliklinika.dto.NalazResponse;
import rs.zr.sa.poliklinika.entity.*;
import rs.zr.sa.poliklinika.repository.NalazRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;
import rs.zr.sa.poliklinika.repository.UputRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NalazServiceImplTest {

    @Mock
    private NalazRepository nalazRepository;

    @Mock
    private PregledRepository pregledRepository;

    @Mock
    private UputRepository uputRepository;

    @InjectMocks
    private NalazServiceImpl nalazService;

    private Nalaz nalaz;
    private Pregled pregled;
    private Uput uput;
    private Pacijent pacijent;
    private Poliklinika poliklinika;
    private NalazRequest nalazRequest;

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
        poliklinika.setNaziv("PoliklinikaApp Centar");
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

        uput = new Uput();
        uput.setUputId(1L);
        uput.setBrojUputa("UP-01");
        uput.setDatumIzdavanja(LocalDate.now());
        uput.setDijagnoza("Dijagnoza");
        uput.setNapomena("Napomena");
        uput.setPacijent(pacijent);

        nalaz = new Nalaz();
        nalaz.setNalazId(1L);
        nalaz.setOpis("Opis nalaza");
        nalaz.setZakljucak("Zaključak");
        nalaz.setPreporuka("Preporuka");
        nalaz.setDatumNalaza(LocalDate.now());
        nalaz.setPregled(pregled);
        nalaz.setUput(uput);

        nalazRequest = new NalazRequest();
        nalazRequest.setOpis("Opis nalaza");
        nalazRequest.setZakljucak("Zaključak");
        nalazRequest.setPreporuka("Preporuka");
        nalazRequest.setDatumNalaza(LocalDate.now());
        nalazRequest.setPregledId(1L);
        nalazRequest.setUputId(1L);
    }

    @Test
    @DisplayName("Uspešno pronalaženje svih nalaza")
    void findAll_ShouldReturnListOfNalazResponses() {
        when(nalazRepository.findAll()).thenReturn(List.of(nalaz));

        List<NalazResponse> result = nalazService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Opis nalaza", result.get(0).getOpis());
        verify(nalazRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Uspešno pronalaženje nalaza po ID-ju")
    void findById_ExistingId_ShouldReturnNalazResponse() {
        when(nalazRepository.findById(1L)).thenReturn(Optional.of(nalaz));

        NalazResponse result = nalazService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getNalazId());
        assertEquals("Zaključak", result.getZakljucak());
        verify(nalazRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka kada nalaz po ID-ju ne postoji")
    void findById_NonExistingId_ShouldThrowException() {
        when(nalazRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nalazService.findById(99L);
        });

        assertEquals("Nalaz sa ID-jem 99 nije pronadjen.", exception.getMessage());
        verify(nalazRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Uspešno čuvanje novog nalaza sa uputom")
    void save_ValidRequestWithUput_ShouldReturnSavedNalazResponse() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(uputRepository.findById(1L)).thenReturn(Optional.of(uput));
        when(nalazRepository.save(any(Nalaz.class))).thenReturn(nalaz);

        NalazResponse result = nalazService.save(nalazRequest);

        assertNotNull(result);
        assertEquals("Opis nalaza", result.getOpis());
        verify(pregledRepository, times(1)).findById(1L);
        verify(uputRepository, times(1)).findById(1L);
        verify(nalazRepository, times(1)).save(any(Nalaz.class));
    }

    @Test
    @DisplayName("Uspešno čuvanje novog nalaza bez uputa (uputId je null)")
    void save_ValidRequestWithoutUput_ShouldReturnSavedNalazResponse() {
        nalazRequest.setUputId(null);
        nalaz.setUput(null);

        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(nalazRepository.save(any(Nalaz.class))).thenReturn(nalaz);

        NalazResponse result = nalazService.save(nalazRequest);

        assertNotNull(result);
        assertNull(result.getUput());
        verify(pregledRepository, times(1)).findById(1L);
        verify(uputRepository, never()).findById(anyLong());
        verify(nalazRepository, times(1)).save(any(Nalaz.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri čuvanju ako pregled ne postoji")
    void save_NonExistingPregled_ShouldThrowException() {
        when(pregledRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nalazService.save(nalazRequest);
        });

        assertEquals("Pregled sa ID-jem 1 nije pronadjen.", exception.getMessage());
        verify(pregledRepository, times(1)).findById(1L);
        verify(nalazRepository, never()).save(any(Nalaz.class));
    }

    @Test
    @DisplayName("Uspešno ažuriranje postojećeg nalaza")
    void update_ExistingIdAndValidRequest_ShouldReturnUpdatedNalazResponse() {
        when(nalazRepository.findById(1L)).thenReturn(Optional.of(nalaz));
        when(pregledRepository.findById(1L)).thenReturn(Optional.of(pregled));
        when(uputRepository.findById(1L)).thenReturn(Optional.of(uput));
        when(nalazRepository.save(any(Nalaz.class))).thenReturn(nalaz);

        nalazRequest.setOpis("Ažuriran opis");
        NalazResponse result = nalazService.update(1L, nalazRequest);

        assertNotNull(result);
        verify(nalazRepository, times(1)).findById(1L);
        verify(pregledRepository, times(1)).findById(1L);
        verify(uputRepository, times(1)).findById(1L);
        verify(nalazRepository, times(1)).save(any(Nalaz.class));
    }

    @Test
    @DisplayName("Bacanje izuzetka pri ažuriranju ako nalaz ne postoji")
    void update_NonExistingId_ShouldThrowException() {
        when(nalazRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nalazService.update(99L, nalazRequest);
        });

        assertEquals("Nalaz sa ID-jem 99 nije pronadjen za azuriranje.", exception.getMessage());
        verify(nalazRepository, times(1)).findById(99L);
        verify(nalazRepository, never()).save(any(Nalaz.class));
    }

    @Test
    @DisplayName("Uspešno brisanje nalaza")
    void delete_ExistingId_ShouldDeleteNalaz() {
        when(nalazRepository.existsById(1L)).thenReturn(true);
        doNothing().when(nalazRepository).deleteById(1L);

        assertDoesNotThrow(() -> nalazService.delete(1L));

        verify(nalazRepository, times(1)).existsById(1L);
        verify(nalazRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Bacanje izuzetka pri brisanju ako nalaz ne postoji")
    void delete_NonExistingId_ShouldThrowException() {
        when(nalazRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nalazService.delete(99L);
        });

        assertEquals("Nalaz sa ID-jem 99 ne postoji.", exception.getMessage());
        verify(nalazRepository, times(1)).existsById(99L);
        verify(nalazRepository, never()).deleteById(anyLong());
    }
}