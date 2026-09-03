package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.dto.UslugaRequest;
import rs.zr.sa.poliklinika.dto.UslugaResponse;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Usluga;
import rs.zr.sa.poliklinika.repository.PoliklinikaRepository;
import rs.zr.sa.poliklinika.repository.UslugaRepository;
import rs.zr.sa.poliklinika.service.UslugaService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Usluga.
 * Sadrži poslovnu logiku za upravljanje medicinskim uslugama, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class UslugaServiceImpl implements UslugaService {

    private final UslugaRepository uslugaRepository;
    private final PoliklinikaRepository poliklinikaRepository;

    /**
     * Vraća listu svih usluga evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link UslugaResponse} sa podacima o svim uslugama
     */
    @Override
    public List<UslugaResponse> findAll() {
        return uslugaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi uslugu na osnovu njenog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator usluge
     * @return objekat tipa {@link UslugaResponse} sa podacima o pronađenoj usluzi
     * @throws RuntimeException ako usluga sa datim ID-jem ne postoji
     */
    @Override
    public UslugaResponse findById(Long id) {
        Usluga usluga = uslugaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + id + " nije pronadjena."));
        return mapToResponse(usluga);
    }

    /**
     * Čuva novu uslugu u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link UslugaRequest} koji sadrži podatke za kreiranje usluge
     * @return objekat tipa {@link UslugaResponse} sa podacima o sačuvanoj usluzi
     * @throws RuntimeException ako poliklinika sa prosleđenim ID-jem ne postoji
     */
    @Override
    public UslugaResponse save(UslugaRequest request) {
        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        Usluga usluga = new Usluga();
        usluga.setNaziv(request.getNaziv());
        usluga.setCena(request.getCena());
        usluga.setPoliklinika(poliklinika);

        Usluga saved = uslugaRepository.save(usluga);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o usluzi na osnovu njenog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator usluge koja se ažurira
     * @param request objekat tipa {@link UslugaRequest} koji sadrži nove podatke
     * @return objekat tipa {@link UslugaResponse} sa ažuriranim podacima o usluzi
     * @throws RuntimeException ako usluga ili povezana poliklinika sa datim ID-jem ne postoje
     */
    @Override
    public UslugaResponse update(Long id, UslugaRequest request) {
        Usluga usluga = uslugaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + id + " nije pronadjena za azuriranje."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("PoliklinikaApp sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        usluga.setNaziv(request.getNaziv());
        usluga.setCena(request.getCena());
        usluga.setPoliklinika(poliklinika);

        Usluga updated = uslugaRepository.save(usluga);
        return mapToResponse(updated);
    }

    /**
     * Briše uslugu iz sistema na osnovu njenog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator usluge koja se briše
     * @throws RuntimeException ako usluga sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!uslugaRepository.existsById(id)) {
            throw new RuntimeException("Usluga sa ID-jem " + id + " ne postoji.");
        }
        uslugaRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Usluga} u odgovarajući DTO objekat {@link UslugaResponse}.
     *
     * @param u entitet usluge koji se mapira
     * @return mapirani objekat tipa {@link UslugaResponse}
     */
    private UslugaResponse mapToResponse(Usluga u) {
        Poliklinika p = u.getPoliklinika();
        PoliklinikaResponse poliklinikaResponse = new PoliklinikaResponse(
                p.getPoliklinikaId(), p.getNaziv(), p.getAdresa(), p.getKontaktTelefon()
        );
        return new UslugaResponse(u.getUslugaId(), u.getNaziv(), u.getCena(), poliklinikaResponse);
    }
}