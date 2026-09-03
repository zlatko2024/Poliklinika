package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.PacijentResponse;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.dto.PregledRequest;
import rs.zr.sa.poliklinika.dto.PregledResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Pregled;
import rs.zr.sa.poliklinika.repository.PacijentRepository;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;
import rs.zr.sa.poliklinika.service.PregledService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Pregled.
 * Sadrži poslovnu logiku za upravljanje zakazanim pregledima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PregledServiceImpl implements PregledService {

    private final PregledRepository pregledRepository;
    private final PacijentRepository pacijentRepository;
    private final PoliklinikaRepository poliklinikaRepository;

    /**
     * Vraća listu svih pregleda evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link PregledResponse} koja sadrži podatke o svim pregledima
     */
    @Override
    public List<PregledResponse> findAll() {
        return pregledRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi pregled na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator pregleda
     * @return objekat tipa {@link PregledResponse} sa podacima o pronađenom pregledu
     * @throws RuntimeException ako pregled sa datim ID-jem ne postoji
     */
    @Override
    public PregledResponse findById(Long id) {
        Pregled pregled = pregledRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(pregled);
    }

    /**
     * Čuva novi pregled u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link PregledRequest} koji sadrži podatke za kreiranje pregleda
     * @return objekat tipa {@link PregledResponse} sa podacima o sačuvanom pregledu
     * @throws RuntimeException ako pacijent ili poliklinika sa prosleđenim ID-jem ne postoje
     */
    @Override
    public PregledResponse save(PregledRequest request) {
        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        Pregled pregled = new Pregled();
        pregled.setDatum(request.getDatum());
        pregled.setVreme(request.getVreme());
        pregled.setVrstaPregleda(request.getVrstaPregleda());
        pregled.setStatus(request.getStatus());
        pregled.setPacijent(pacijent);
        pregled.setPoliklinika(poliklinika);

        Pregled saved = pregledRepository.save(pregled);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o pregledu na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator pregleda koji se ažurira
     * @param request objekat tipa {@link PregledRequest} koji sadrži nove podatke
     * @return objekat tipa {@link PregledResponse} sa ažuriranim podacima o pregledu
     * @throws RuntimeException ako pregled, pacijent ili poliklinika sa datim ID-jem ne postoje
     */
    @Override
    public PregledResponse update(Long id, PregledRequest request) {
        Pregled pregled = pregledRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        pregled.setDatum(request.getDatum());
        pregled.setVreme(request.getVreme());
        pregled.setVrstaPregleda(request.getVrstaPregleda());
        pregled.setStatus(request.getStatus());
        pregled.setPacijent(pacijent);
        pregled.setPoliklinika(poliklinika);

        Pregled updated = pregledRepository.save(pregled);
        return mapToResponse(updated);
    }

    /**
     * Briše pregled iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator pregleda koji se briše
     * @throws RuntimeException ako pregled sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!pregledRepository.existsById(id)) {
            throw new RuntimeException("Pregled sa ID-jem " + id + " ne postoji.");
        }
        pregledRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Pregled} u odgovarajući DTO objekat {@link PregledResponse}.
     *
     * @param p entitet pregleda koji se mapira
     * @return mapirani objekat tipa {@link PregledResponse}
     */
    private PregledResponse mapToResponse(Pregled p) {
        Pacijent pac = p.getPacijent();
        PacijentResponse pacijentResponse = new PacijentResponse(
                pac.getPacijentId(), pac.getIme(), pac.getPrezime(), pac.getJmbg(), pac.getEmail(), pac.getTelefon()
        );

        Poliklinika pol = p.getPoliklinika();
        PoliklinikaResponse poliklinikaResponse = new PoliklinikaResponse(
                pol.getPoliklinikaId(), pol.getNaziv(), pol.getAdresa(), pol.getKontaktTelefon()
        );

        return new PregledResponse(
                p.getPregledId(), p.getDatum(), p.getVreme(), p.getVrstaPregleda(), p.getStatus(), pacijentResponse, poliklinikaResponse
        );
    }
}