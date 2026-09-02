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
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class RacunServiceImpl implements RacunService {

    private final RacunRepository racunRepository;
    private final PregledRepository pregledRepository;
    private final DoktorRepository doktorRepository;

    @Override
    public List<RacunResponse> findAll() {
        return racunRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RacunResponse findById(Long id) {
        Racun racun = racunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Racun sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(racun);
    }

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

    @Override
    public void delete(Long id) {
        if (!racunRepository.existsById(id)) {
            throw new RuntimeException("Racun sa ID-jem " + id + " ne postoji.");
        }
        racunRepository.deleteById(id);
    }

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