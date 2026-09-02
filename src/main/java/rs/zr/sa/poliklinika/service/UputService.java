package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.UputRequest;
import rs.zr.sa.poliklinika.dto.UputResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje uputima.
 * @author Zlatko Radovanovic
 */
public interface UputService {
    List<UputResponse> findAll();
    UputResponse findById(Long id);
    UputResponse save(UputRequest request);
    UputResponse update(Long id, UputRequest request);
    void delete(Long id);
}