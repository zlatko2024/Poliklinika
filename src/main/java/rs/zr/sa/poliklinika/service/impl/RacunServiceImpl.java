package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.*;
import rs.zr.sa.poliklinika.entity.*;
import rs.zr.sa.poliklinika.repository.DoktorRepository;
import rs.zr.sa.poliklinika.repository.PregledRepository;
import rs.zr.sa.poliklinika.repository.RacunRepository;
import rs.zr.sa.poliklinika.service.RacunService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Racun.
 * Sadrži poslovnu logiku za upravljanje računima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class RacunServiceImpl implements RacunService {

    private final RacunRepository racunRepository;
    private final PregledRepository pregledRepository;
    private final DoktorRepository doktorRepository;

    /**
     * Vraća listu svih računa evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link RacunResponse} sa podacima o svim računima
     */
    @Override
    public List<RacunResponse> findAll() {
        return racunRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi račun na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator računa
     * @return objekat tipa {@link RacunResponse} sa podacima o pronađenom računu
     * @throws RuntimeException ako račun sa datim ID-jem ne postoji
     */
    @Override
    public RacunResponse findById(Long id) {
        Racun racun = racunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Racun sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(racun);
    }

    /**
     * Čuva novi račun u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link RacunRequest} koji sadrži podatke za kreiranje računa
     * @return objekat tipa {@link RacunResponse} sa podacima o sačuvanom računu
     * @throws RuntimeException ako povezani pregled ili doktor sa datim ID-jem ne postoje
     */
    @Override
    public RacunResponse save(RacunRequest request) {
        Pregled pregled = pregledRepository.findById(request.getPregledId())
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + request.getPregledId() + " nije pronadjen."));

        Doktor doktor = doktorRepository.findById(request.getDoktorId())
                .orElseThrow(() -> new RuntimeException("Doktor sa ID-jem " + request.getDoktorId() + " nije pronadjen."));

        Racun racun = new Racun();
        racun.setBrojRacuna(request.getBrojRacuna());
        racun.setDatumIzdavanja(request.getDatumIzdavanja());
        racun.setUkupanIznos(request.getUkupanIznos());
        racun.setStatusPlacanja(request.getStatusPlacanja());
        racun.setPregled(pregled);
        racun.setDoktor(doktor);

        Racun saved = racunRepository.save(racun);
        return mapToResponse(saved);
    }

    /**
     * Ažurira postojeće podatke o računu na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator računa koji se ažurira
     * @param request objekat tipa {@link RacunRequest} koji sadrži nove podatke
     * @return objekat tipa {@link RacunResponse} sa ažuriranim podacima o računu
     * @throws RuntimeException ako račun, povezani pregled ili doktor sa datim ID-jem ne postoje
     */
    @Override
    public RacunResponse update(Long id, RacunRequest request) {
        Racun racun = racunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Racun sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Pregled pregled = pregledRepository.findById(request.getPregledId())
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + request.getPregledId() + " nije pronadjen."));

        Doktor doktor = doktorRepository.findById(request.getDoktorId())
                .orElseThrow(() -> new RuntimeException("Doktor sa ID-jem " + request.getDoktorId() + " nije pronadjen."));

        racun.setBrojRacuna(request.getBrojRacuna());
        racun.setDatumIzdavanja(request.getDatumIzdavanja());
        racun.setUkupanIznos(request.getUkupanIznos());
        racun.setStatusPlacanja(request.getStatusPlacanja());
        racun.setPregled(pregled);
        racun.setDoktor(doktor);

        Racun updated = racunRepository.save(racun);
        return mapToResponse(updated);
    }

    /**
     * Briše račun iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator računa koji se briše
     * @throws RuntimeException ako račun sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!racunRepository.existsById(id)) {
            throw new RuntimeException("Racun sa ID-jem " + id + " ne postoji.");
        }
        racunRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Racun} u odgovarajući DTO objekat {@link RacunResponse}.
     *
     * @param r entitet računa koji se mapira
     * @return mapirani objekat tipa {@link RacunResponse}
     */
    private RacunResponse mapToResponse(Racun r) {
        Pregled pr = r.getPregled();
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

        Doktor dok = r.getDoktor();
        Usluga usl = dok.getUsluga();
        UslugaResponse uslugaResponse = new UslugaResponse(
                usl.getUslugaId(), usl.getNaziv(), usl.getCena(), poliklinikaResponse
        );
        DoktorResponse doktorResponse = new DoktorResponse(
                dok.getDoktorId(), dok.getIme(), dok.getPrezime(), dok.getBrojLicence(), dok.getSpecijalnost(), uslugaResponse
        );

        return new RacunResponse(
                r.getRacunId(), r.getBrojRacuna(), r.getDatumIzdavanja(), r.getUkupanIznos(), r.getStatusPlacanja(), pregledResponse, doktorResponse
        );
    }
}