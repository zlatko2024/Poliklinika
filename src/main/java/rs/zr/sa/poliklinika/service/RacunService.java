package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.RacunRequest;
import rs.zr.sa.poliklinika.dto.RacunResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje racunima.
 * @author Zlatko Radovanovic
 */
public interface RacunService {
    List<RacunResponse> findAll();
    RacunResponse findById(Long id);
    RacunResponse save(RacunRequest request);
    RacunResponse update(Long id, RacunRequest request);
    void delete(Long id);
}