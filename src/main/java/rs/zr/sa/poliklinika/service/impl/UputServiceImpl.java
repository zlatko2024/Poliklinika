package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.PacijentResponse;
import rs.zr.sa.poliklinika.dto.UputRequest;
import rs.zr.sa.poliklinika.dto.UputResponse;
import rs.zr.sa.poliklinika.entity.Pacijent;
import rs.zr.sa.poliklinika.entity.Uput;
import rs.zr.sa.poliklinika.repository.PacijentRepository;
import rs.zr.sa.poliklinika.repository.UputRepository;
import rs.zr.sa.poliklinika.service.UputService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Uput.
 * Sadrži poslovnu logiku za upravljanje medicinskim uputima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class UputServiceImpl implements UputService {

    private final UputRepository uputRepository;
    private final PacijentRepository pacijentRepository;

    /**
     * Vraća listu svih uputa evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link UputResponse} sa podacima o svim uputima
     */
    @Override
    public List<UputResponse> findAll() {
        return uputRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi uput na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator uputa
     * @return objekat tipa {@link UputResponse} sa podacima o pronađenom uputu
     * @throws RuntimeException ako uput sa datim ID-jem ne postoji
     */
    @Override
    public UputResponse findById(Long id) {
        Uput uput = uputRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Uput sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(uput);
    }

    /**
     * Čuva novi uput u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link UputRequest} koji sadrži podatke za kreiranje uputa
     * @return objekat tipa {@link UputResponse} sa podacima o sačuvanom uputu
     * @throws RuntimeException ako pacijent sa prosleđenim ID-jem ne postoji
     */
    @Override
    public UputResponse save(UputRequest request) {
        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        Uput uput = new Uput();
        uput.setBrojUputa(request.getBrojUputa());
        uput.setDatumIzdavanja(request.getDatumIzdavanja());
        uput.setDijagnoza(request.getDijagnoza());
        uput.setNapomena(request.getNapomena());
        uput.setPacijent(pacijent);

        Uput saved = uputRepository.save(uput);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o uputu na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator uputa koji se ažurira
     * @param request objekat tipa {@link UputRequest} koji sadrži nove podatke
     * @return objekat tipa {@link UputResponse} sa ažuriranim podacima o uputu
     * @throws RuntimeException ako uput ili povezani pacijent sa datim ID-jem ne postoje
     */
    @Override
    public UputResponse update(Long id, UputRequest request) {
        Uput uput = uputRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Uput sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        uput.setBrojUputa(request.getBrojUputa());
        uput.setDatumIzdavanja(request.getDatumIzdavanja());
        uput.setDijagnoza(request.getDijagnoza());
        uput.setNapomena(request.getNapomena());
        uput.setPacijent(pacijent);

        Uput updated = uputRepository.save(uput);
        return mapToResponse(updated);
    }

    /**
     * Briše uput iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator uputa koji se briše
     * @throws RuntimeException ako uput sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!uputRepository.existsById(id)) {
            throw new RuntimeException("Uput sa ID-jem " + id + " ne postoji.");
        }
        uputRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Uput} u odgovarajući DTO objekat {@link UputResponse}.
     *
     * @param u entitet uputa koji se mapira
     * @return mapirani objekat tipa {@link UputResponse}
     */
    private UputResponse mapToResponse(Uput u) {
        Pacijent pac = u.getPacijent();
        PacijentResponse pacijentResponse = new PacijentResponse(
                pac.getPacijentId(), pac.getIme(), pac.getPrezime(), pac.getJmbg(), pac.getEmail(), pac.getTelefon()
        );

        return new UputResponse(
                u.getUputId(), u.getBrojUputa(), u.getDatumIzdavanja(), u.getDijagnoza(), u.getNapomena(), pacijentResponse
        );
    }
}