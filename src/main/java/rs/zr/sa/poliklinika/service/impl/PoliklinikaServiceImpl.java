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
 * Implementacija servisnog sloja za entitet Poliklinika.
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PoliklinikaServiceImpl implements PoliklinikaService {

    private final PoliklinikaRepository poliklinikaRepository;

    @Override
    public List<PoliklinikaResponse> findAll() {
        return poliklinikaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PoliklinikaResponse findById(Long id) {
        Poliklinika poliklinika = poliklinikaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + id + " nije pronadjena."));
        return mapToResponse(poliklinika);
    }

    @Override
    public PoliklinikaResponse save(PoliklinikaRequest request) {
        Poliklinika poliklinika = new Poliklinika();
        poliklinika.setNaziv(request.getNaziv());
        poliklinika.setAdresa(request.getAdresa());
        poliklinika.setKontaktTelefon(request.getKontaktTelefon());

        Poliklinika saved = poliklinikaRepository.save(poliklinika);
        return mapToResponse(saved);
    }

    @Override
    public PoliklinikaResponse update(Long id, PoliklinikaRequest request) {
        Poliklinika poliklinika = poliklinikaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poliklinika sa ID-jem " + id + " nije pronadjena za azuriranje."));

        poliklinika.setNaziv(request.getNaziv());
        poliklinika.setAdresa(request.getAdresa());
        poliklinika.setKontaktTelefon(request.getKontaktTelefon());

        Poliklinika updated = poliklinikaRepository.save(poliklinika);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!poliklinikaRepository.existsById(id)) {
            throw new RuntimeException("Poliklinika sa ID-jem " + id + " ne postoji.");
        }
        poliklinikaRepository.deleteById(id);
    }

    private PoliklinikaResponse mapToResponse(Poliklinika p) {
        return new PoliklinikaResponse(p.getPoliklinikaId(), p.getNaziv(), p.getAdresa(), p.getKontaktTelefon());
    }
}