package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.UslugaRequest;
import rs.zr.sa.poliklinika.dto.UslugaResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje uslugama.
 * @author Zlatko Radovanovic
 */
public interface UslugaService {
    List<UslugaResponse> findAll();
    UslugaResponse findById(Long id);
    UslugaResponse save(UslugaRequest request);
    UslugaResponse update(Long id, UslugaRequest request);
    void delete(Long id);
}