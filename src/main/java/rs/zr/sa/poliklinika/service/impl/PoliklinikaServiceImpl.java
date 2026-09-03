package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.PoliklinikaRequest;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;
import rs.zr.sa.poliklinika.service.PoliklinikaService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet PoliklinikaApp.
 * Sadrži poslovnu logiku za upravljanje podacima o poliklinikama, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PoliklinikaServiceImpl implements PoliklinikaService {

    private final PoliklinikaRepository poliklinikaRepository;

    /**
     * Vraća listu svih poliklinika evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link PoliklinikaResponse} koja sadrži podatke o svim poliklinikama
     */
    @Override
    public List<PoliklinikaResponse> findAll() {
        return poliklinikaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi polikliniku na osnovu njenog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator poliklinike
     * @return objekat tipa {@link PoliklinikaResponse} sa podacima o pronađenoj poliklinici
     * @throws RuntimeException ako poliklinika sa datim ID-jem ne postoji
     */
    @Override
    public PoliklinikaResponse findById(Long id) {
        Poliklinika poliklinika = poliklinikaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + id + " nije pronadjena."));
        return mapToResponse(poliklinika);
    }

    /**
     * Čuva novu polikliniku u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link PoliklinikaRequest} koji sadrži podatke za kreiranje poliklinike
     * @return objekat tipa {@link PoliklinikaResponse} sa podacima o sačuvanoj poliklinici
     */
    @Override
    public PoliklinikaResponse save(PoliklinikaRequest request) {
        Poliklinika poliklinika = new Poliklinika();
        poliklinika.setNaziv(request.getNaziv());
        poliklinika.setAdresa(request.getAdresa());
        poliklinika.setKontaktTelefon(request.getKontaktTelefon());

        Poliklinika saved = poliklinikaRepository.save(poliklinika);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o poliklinici na osnovu njenog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator poliklinike koja se ažurira
     * @param request objekat tipa {@link PoliklinikaRequest} koji sadrži nove podatke
     * @return objekat tipa {@link PoliklinikaResponse} sa ažuriranim podacima o poliklinici
     * @throws RuntimeException ako poliklinika sa datim ID-jem ne postoji
     */
    @Override
    public PoliklinikaResponse update(Long id, PoliklinikaRequest request) {
        Poliklinika poliklinika = poliklinikaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + id + " nije pronadjena za azuriranje."));

        poliklinika.setNaziv(request.getNaziv());
        poliklinika.setAdresa(request.getAdresa());
        poliklinika.setKontaktTelefon(request.getKontaktTelefon());

        Poliklinika updated = poliklinikaRepository.save(poliklinika);
        return mapToResponse(updated);
    }

    /**
     * Briše polikliniku iz sistema na osnovu njenog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator poliklinike koja se briše
     * @throws RuntimeException ako poliklinika sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!poliklinikaRepository.existsById(id)) {
            throw new RuntimeException("PoliklinikaApp sa ID-jem " + id + " ne postoji.");
        }
        poliklinikaRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Poliklinika} u odgovarajući DTO objekat {@link PoliklinikaResponse}.
     *
     * @param p entitet poliklinike koji se mapira
     * @return mapirani objekat tipa {@link PoliklinikaResponse}
     */
    private PoliklinikaResponse mapToResponse(Poliklinika p) {
        return new PoliklinikaResponse(p.getPoliklinikaId(), p.getNaziv(), p.getAdresa(), p.getKontaktTelefon());
    }
}