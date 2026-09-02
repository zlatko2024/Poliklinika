package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.PoliklinikaRequest;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje poliklinikama.
 * @author Zlatko Radovanovic
 */
public interface PoliklinikaService {
    List<PoliklinikaResponse> findAll();
    PoliklinikaResponse findById(Long id);
    PoliklinikaResponse save(PoliklinikaRequest request);
    PoliklinikaResponse update(Long id, PoliklinikaRequest request);
    void delete(Long id);
}