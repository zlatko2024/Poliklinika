package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.PacijentRequest;
import rs.zr.sa.poliklinika.dto.PacijentResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje pacijentima.
 * @author Zlatko Radovanovic
 */
public interface PacijentService {
    List<PacijentResponse> findAll();
    PacijentResponse findById(Long id);
    PacijentResponse save(PacijentRequest request);
    PacijentResponse update(Long id, PacijentRequest request);
    void delete(Long id);
}