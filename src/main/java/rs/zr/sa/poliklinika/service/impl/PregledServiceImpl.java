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
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PregledServiceImpl implements PregledService {

    private final PregledRepository pregledRepository;
    private final PacijentRepository pacijentRepository;
    private final PoliklinikaRepository poliklinikaRepository;

    @Override
    public List<PregledResponse> findAll() {
        return pregledRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PregledResponse findById(Long id) {
        Pregled pregled = pregledRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(pregled);
    }

    @Override
    public PregledResponse save(PregledRequest request) {
        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

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

    @Override
    public PregledResponse update(Long id, PregledRequest request) {
        Pregled pregled = pregledRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregled sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Pacijent pacijent = pacijentRepository.findById(request.getPacijentId())
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + request.getPacijentId() + " nije pronadjen."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        pregled.setDatum(request.getDatum());
        pregled.setVreme(request.getVreme());
        pregled.setVrstaPregleda(request.getVrstaPregleda());
        pregled.setStatus(request.getStatus());
        pregled.setPacijent(pacijent);
        pregled.setPoliklinika(poliklinika);

        Pregled updated = pregledRepository.save(pregled);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!pregledRepository.existsById(id)) {
            throw new RuntimeException("Pregled sa ID-jem " + id + " ne postoji.");
        }
        pregledRepository.deleteById(id);
    }

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