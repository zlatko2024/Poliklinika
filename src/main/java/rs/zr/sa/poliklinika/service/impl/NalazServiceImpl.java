package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.*;
import rs.zr.sa.poliklinika.entity.*;
import rs.zr.sa.poliklinika.repository.NalazRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;
import rs.zr.sa.poliklinika.repository.UputRepository;
import rs.zr.sa.poliklinika.service.NalazService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Nalaz.
 * Sadrži poslovnu logiku za upravljanje medicinskim nalazima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u odgovarajuće DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class NalazServiceImpl implements NalazService {

    private final NalazRepository nalazRepository;
    private final PregledRepository pregledRepository;
    private final UputRepository uputRepository;

    /**
     * Vraća listu svih nalaza evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link NalazResponse} sa podacima o svim nalazima
     */
    @Override
    public List<NalazResponse> findAll() {
        return nalazRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi nalaz na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator nalaza
     * @return objekat tipa {@link NalazResponse} sa podacima o pronađenom nalažu
     * @throws RuntimeException ako nalaz sa datim ID-jem ne postoji
     */
    @Override
    public NalazResponse findById(Long id) {
        Nalaz nalaz = nalazRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nalaz sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(nalaz);
    }

    /**
     * Čuva novi nalaz u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link NalazRequest} koji sadrži podatke za kreiranje nalaza
     * @return objekat tipa {@link NalazResponse} sa podacima o sačuvanom nalažu
     * @throws RuntimeException ako povezani pregled ili opcioni uput sa datim ID-jem ne postoje
     */
    @Override
    public NalazResponse save(NalazRequest request) {
        Pregled pregled = pregledRepository.findById(request.getPregledId())
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + request.getPregledId() + " nije pronadjen."));

        Uput uput = null;
        if (request.getUputId() != null) {
            uput = uputRepository.findById(request.getUputId())
                    .orElseThrow(() -> new RuntimeException("Uput sa ID-jem " + request.getUputId() + " nije pronadjen."));
        }

        Nalaz nalaz = new Nalaz();
        nalaz.setOpis(request.getOpis());
        nalaz.setZakljucak(request.getZakljucak());
        nalaz.setPreporuka(request.getPreporuka());
        nalaz.setDatumNalaza(request.getDatumNalaza());
        nalaz.setPregled(pregled);
        nalaz.setUput(uput);

        Nalaz saved = nalazRepository.save(nalaz);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o nalažu na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator nalaza koji se ažurira
     * @param request objekat tipa {@link NalazRequest} koji sadrži nove podatke
     * @return objekat tipa {@link NalazResponse} sa ažuriranim podacima
     * @throws RuntimeException ako nalaz, povezani pregled ili opcioni uput sa datim ID-jem ne postoje
     */
    @Override
    public NalazResponse update(Long id, NalazRequest request) {
        Nalaz nalaz = nalazRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nalaz sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Pregled pregled = pregledRepository.findById(request.getPregledId())
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + request.getPregledId() + " nije pronadjen."));

        Uput uput = null;
        if (request.getUputId() != null) {
            uput = uputRepository.findById(request.getUputId())
                    .orElseThrow(() -> new RuntimeException("Uput sa ID-jem " + request.getUputId() + " nije pronadjen."));
        }

        nalaz.setOpis(request.getOpis());
        nalaz.setZakljucak(request.getZakljucak());
        nalaz.setPreporuka(request.getPreporuka());
        nalaz.setDatumNalaza(request.getDatumNalaza());
        nalaz.setPregled(pregled);
        nalaz.setUput(uput);

        Nalaz updated = nalazRepository.save(nalaz);
        return mapToResponse(updated);
    }

    /**
     * Briše nalaz iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator nalaza koji se briše
     * @throws RuntimeException ako nalaz sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!nalazRepository.existsById(id)) {
            throw new RuntimeException("Nalaz sa ID-jem " + id + " ne postoji.");
        }
        nalazRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Nalaz} u odgovarajući DTO objekat {@link NalazResponse}.
     *
     * @param n entitet nalaza koji se mapira
     * @return mapirani objekat tipa {@link NalazResponse}
     */
    private NalazResponse mapToResponse(Nalaz n) {
        Pregled pr = n.getPregled();
        Pacijent pac = pr.getPacijent();
        Poliklinika pol = pr.getPoliklinika();

        PacijentResponse pacijentResponse = new PacijentResponse(
                pac.getPacijentId(), pac.getIme(), pac.getPrezime(), pac.getJmbg(), pac.getEmail(), pac.getTelefon()
        );
        PoliklinikaResponse poliklinikaResponse = new PoliklinikaResponse(
                pol.getPoliklinikaId(), pol.getNaziv(), pol.getAdresa(), pol.getKontaktTelefon()
        );
        PregledResponse pregledResponse = new PregledResponse(
                pr.getPregledId(), pr.getDatum(), pr.getVreme(), pr.getVrstaPregleda(), pr.getStatus(), pacijentResponse, poliklinikaResponse
        );

        UputResponse uputResponse = null;
        if (n.getUput() != null) {
            Uput up = n.getUput();
            uputResponse = new UputResponse(
                    up.getUputId(), up.getBrojUputa(), up.getDatumIzdavanja(), up.getDijagnoza(), up.getNapomena(), pacijentResponse
            );
        }

        return new NalazResponse(
                n.getNalazId(), n.getOpis(), n.getZakljucak(), n.getPreporuka(), n.getDatumNalaza(), pregledResponse, uputResponse
        );
    }
}