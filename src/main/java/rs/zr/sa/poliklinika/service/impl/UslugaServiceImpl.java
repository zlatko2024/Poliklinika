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
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class UslugaServiceImpl implements UslugaService {

    private final UslugaRepository uslugaRepository;
    private final PoliklinikaRepository poliklinikaRepository;

    @Override
    public List<UslugaResponse> findAll() {
        return uslugaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UslugaResponse findById(Long id) {
        Usluga usluga = uslugaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + id + " nije pronadjena."));
        return mapToResponse(usluga);
    }

    @Override
    public UslugaResponse save(UslugaRequest request) {
        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        Usluga usluga = new Usluga();
        usluga.setNaziv(request.getNaziv());
        usluga.setCena(request.getCena());
        usluga.setPoliklinika(poliklinika);

        Usluga saved = uslugaRepository.save(usluga);
        return mapToResponse(saved);
    }

    @Override
    public UslugaResponse update(Long id, UslugaRequest request) {
        Usluga usluga = uslugaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + id + " nije pronadjena za azuriranje."));

        Poliklinika poliklinika = poliklinikaRepository.findById(request.getPoliklinikaId())
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + request.getPoliklinikaId() + " nije pronadjena."));

        usluga.setNaziv(request.getNaziv());
        usluga.setCena(request.getCena());
        usluga.setPoliklinika(poliklinika);

        Usluga updated = uslugaRepository.save(usluga);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!uslugaRepository.existsById(id)) {
            throw new RuntimeException("Usluga sa ID-jem " + id + " ne postoji.");
        }
        uslugaRepository.deleteById(id);
    }

    private UslugaResponse mapToResponse(Usluga u) {
        Poliklinika p = u.getPoliklinika();
        PoliklinikaResponse poliklinikaResponse = new PoliklinikaResponse(
                p.getPoliklinikaId(), p.getNaziv(), p.getAdresa(), p.getKontaktTelefon()
        );
        return new UslugaResponse(u.getUslugaId(), u.getNaziv(), u.getCena(), poliklinikaResponse);
    }
}