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
 * Sadrži poslovnu logiku za upravljanje podacima o doktorima, uključujući
 * pretragu, kreiranje, ažuriranje, brisanje i mapiranje entiteta u DTO objekte.
 *
 * @author Zlatko Radovanovic
 */
@Service
@RequiredArgsConstructor
public class DoktorServiceImpl implements DoktorService {

    private final DoktorRepository doktorRepository;
    private final UslugaRepository uslugaRepository;

    /**
     * Vraća listu svih doktora evidentiranih u sistemu.
     *
     * @return lista objekata tipa {@link DoktorResponse} koja sadrži podatke o svim doktorima
     */
    @Override
    public List<DoktorResponse> findAll() {
        return doktorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi doktora na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator doktora
     * @return objekat tipa {@link DoktorResponse} sa podacima o pronađenom doktoru
     * @throws RuntimeException ako doktor sa datim ID-jem ne postoji
     */
    @Override
    public DoktorResponse findById(Long id) {
        Doktor doktor = doktorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor sa ID-jem " + id + " nije pronadjen."));
        return mapToResponse(doktor);
    }

    /**
     * Čuva novog doktora u bazi podataka na osnovu prosleđenih podataka.
     *
     * @param request objekat tipa {@link DoktorRequest} koji sadrži podatke za kreiranje doktora
     * @return objekat tipa {@link DoktorResponse} sa podacima o sačuvanom doktoru
     * @throws RuntimeException ako usluga sa prosleđenim ID-jem ne postoji
     */
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

    /**
     * Ažurira postojeće podatke o doktoru na osnovu njegovog ID-ja i novih podataka.
     *
     * @param id jedinstveni identifikator doktora koji se ažurira
     * @param request objekat tipa {@link DoktorRequest} koji sadrži nove podatke
     * @return objekat tipa {@link DoktorResponse} sa ažuriranim podacima o doktoru
     * @throws RuntimeException ako doktor ili povezana usluga sa datim ID-jem ne postoje
     */
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

    /**
     * Briše doktora iz sistema na osnovu njegovog jedinstvenog identifikatora.
     *
     * @param id jedinstveni identifikator doktora koji se briše
     * @throws RuntimeException ako doktor sa datim ID-jem ne postoji
     */
    @Override
    public void delete(Long id) {
        if (!doktorRepository.existsById(id)) {
            throw new RuntimeException("Doktor sa ID-jem " + id + " ne postoji.");
        }
        doktorRepository.deleteById(id);
    }

    /**
     * Pomoćna metoda za mapiranje entiteta {@link Doktor} u odgovarajući DTO objekat {@link DoktorResponse}.
     *
     * @param d entitet doktora koji se mapira
     * @return mapirani objekat tipa {@link DoktorResponse}
     */
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