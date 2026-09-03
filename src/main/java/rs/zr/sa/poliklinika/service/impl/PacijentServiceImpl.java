package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.PacijentRequest;
import rs.zr.sa.poliklinika.dto.PacijentResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.repository.PacijentRepository;
import rs.zr.sa.poliklinika.service.PacijentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Pacijent.
 * Sadrži poslovnu logiku za upravljanje podacima o pacijentima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PacijentServiceImpl implements PacijentService {

    private final PacijentRepository pacijentRepository;

    /**
     * Vraća listu svih pacijenata evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link PacijentResponse} koja sadrži podatke o svim pacijentima
     */
    @Override
    public List<PacijentResponse> findAll() {
        return pacijentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi pacijenta na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator pacijenta
     * @return objekat tipa {@link PacijentResponse} sa podacima o pronađenom pacijentu
     * @throws RuntimeException ako pacijent sa datim ID-jem ne postoji
     */
    @Override
    public PacijentResponse findById(Long id) {
        Pacijent pacijent = pacijentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(pacijent);
    }

    /**
     * Čuva novog pacijenta u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link PacijentRequest} koji sadrži podatke za kreiranje pacijenta
     * @return objekat tipa {@link PacijentResponse} sa podacima o sačuvanom pacijentu
     */
    @Override
    public PacijentResponse save(PacijentRequest request) {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme(request.getIme());
        pacijent.setPrezime(request.getPrezime());
        pacijent.setJmbg(request.getJmbg());
        pacijent.setEmail(request.getEmail());
        pacijent.setTelefon(request.getTelefon());

        Pacijent saved = pacijentRepository.save(pacijent);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o pacijentu na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator pacijenta koji se ažurira
     * @param request objekat tipa {@link PacijentRequest} koji sadrži nove podatke
     * @return objekat tipa {@link PacijentResponse} sa ažuriranim podacima o pacijentu
     * @throws RuntimeException ako pacijent sa datim ID-jem ne postoji
     */
    @Override
    public PacijentResponse update(Long id, PacijentRequest request) {
        Pacijent pacijent = pacijentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + id + " nije pronadjen za azuriranje."));

        pacijent.setIme(request.getIme());
        pacijent.setPrezime(request.getPrezime());
        pacijent.setJmbg(request.getJmbg());
        pacijent.setEmail(request.getEmail());
        pacijent.setTelefon(request.getTelefon());

        Pacijent updated = pacijentRepository.save(pacijent);
        return mapToResponse(updated);
    }

    /**
     * Briše pacijenta iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator pacijenta koji se briše
     * @throws RuntimeException ako pacijent sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!pacijentRepository.existsById(id)) {
            throw new RuntimeException("Pacijent sa ID-jem " + id + " ne postoji.");
        }
        pacijentRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Pacijent} u odgovarajući DTO objekat {@link PacijentResponse}.
     *
     * @param p entitet pacijenta koji se mapira
     * @return mapirani objekat tipa {@link PacijentResponse}
     */
    private PacijentResponse mapToResponse(Pacijent p) {
        return new PacijentResponse(p.getPacijentId(), p.getIme(), p.getPrezime(), p.getJmbg(), p.getEmail(), p.getTelefon());
    }
}