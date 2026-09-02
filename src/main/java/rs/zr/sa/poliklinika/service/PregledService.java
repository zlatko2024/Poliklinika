package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.PregledRequest;
import rs.zr.sa.poliklinika.dto.PregledResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje pregledima.
 * @author Zlatko Radovanovic
 */
public interface PregledService {
    List<PregledResponse> findAll();
    PregledResponse findById(Long id);
    PregledResponse save(PregledRequest request);
    PregledResponse update(Long id, PregledRequest request);
    void delete(Long id);
}