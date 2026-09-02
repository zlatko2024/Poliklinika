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
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class PacijentServiceImpl implements PacijentService {

    private final PacijentRepository pacijentRepository;

    @Override
    public List<PacijentResponse> findAll() {
        return pacijentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PacijentResponse findById(Long id) {
        Pacijent pacijent = pacijentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacijent sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(pacijent);
    }

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

    @Override
    public void delete(Long id) {
        if (!pacijentRepository.existsById(id)) {
            throw new RuntimeException("Pacijent sa ID-jem " + id + " ne postoji.");
        }
        pacijentRepository.deleteById(id);
    }

    private PacijentResponse mapToResponse(Pacijent p) {
        return new PacijentResponse(p.getPacijentId(), p.getIme(), p.getPrezime(), p.getJmbg(), p.getEmail(), p.getTelefon());
    }
}