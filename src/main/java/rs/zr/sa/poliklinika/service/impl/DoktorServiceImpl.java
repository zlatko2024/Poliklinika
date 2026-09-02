package rs.zr.sa.poliklinika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.zr.sa.poliklinika.dto.DoktorRequest;
import rs.zr.sa.poliklinika.dto.DoktorResponse;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.dto.UslugaResponse;
import rs.zr.sa.poliklinika.entity.Doktor;
import rs.zr.sa.poliklinika.entity.Poliklinika;
import rs.zr.sa.poliklinika.entity.Usluga;
import rs.zr.sa.poliklinika.repository.DoktorRepository;
import rs.zr.sa.poliklinika.repository.UslugaRepository;
import rs.zr.sa.poliklinika.service.DoktorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacija servisnog sloja za entitet Doktor.
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class DoktorServiceImpl implements DoktorService {

    private final DoktorRepository doktorRepository;
    private final UslugaRepository uslugaRepository;

    @Override
    public List<DoktorResponse> findAll() {
        return doktorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoktorResponse findById(Long id) {
        Doktor doktor = doktorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(doktor);
    }

    @Override
    public DoktorResponse save(DoktorRequest request) {
        Usluga usluga = uslugaRepository.findById(request.getUslugaId())
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + request.getUslugaId() + " nije pronadjena."));

        Doktor doktor = new Doktor();
        doktor.setIme(request.getIme());
        doktor.setPrezime(request.getPrezime());
        doktor.setBrojLicence(request.getBrojLicence());
        doktor.setSpecijalnost(request.getSpecijalnost());
        doktor.setUsluga(usluga);

        Doktor saved = doktorRepository.save(doktor);
        return mapToResponse(saved);
    }

    @Override
    public DoktorResponse update(Long id, DoktorRequest request) {
        Doktor doktor = doktorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor sa ID-jem " + id + " nije pronadjen za azuriranje."));

        Usluga usluga = uslugaRepository.findById(request.getUslugaId())
                .orElseThrow(() -> new RuntimeException("Usluga sa ID-jem " + request.getUslugaId() + " nije pronadjena."));

        doktor.setIme(request.getIme());
        doktor.setPrezime(request.getPrezime());
        doktor.setBrojLicence(request.getBrojLicence());
        doktor.setSpecijalnost(request.getSpecijalnost());
        doktor.setUsluga(usluga);

        Doktor updated = doktorRepository.save(doktor);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!doktorRepository.existsById(id)) {
            throw new RuntimeException("Doktor sa ID-jem " + id + " ne postoji.");
        }
        doktorRepository.deleteById(id);
    }

    private DoktorResponse mapToResponse(Doktor d) {
        Usluga u = d.getUsluga();
        Poliklinika p = u.getPoliklinika();

        PoliklinikaResponse poliklinikaResponse = new PoliklinikaResponse(
                p.getPoliklinikaId(), p.getNaziv(), p.getAdresa(), p.getKontaktTelefon()
        );
        UslugaResponse uslugaResponse = new UslugaResponse(
                u.getUslugaId(), u.getNaziv(), u.getCena(), poliklinikaResponse
        );

        return new DoktorResponse(
                d.getDoktorId(), d.getIme(), d.getPrezime(), d.getBrojLicence(), d.getSpecijalnost(), uslugaResponse
        );
    }
}