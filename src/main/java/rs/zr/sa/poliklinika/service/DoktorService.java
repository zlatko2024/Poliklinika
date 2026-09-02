package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.DoktorRequest;
import rs.zr.sa.poliklinika.dto.DoktorResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje doktorima.
 * @author Zlatko Radovanovic
 */
public interface DoktorService {
    List<DoktorResponse> findAll();
    DoktorResponse findById(Long id);
    DoktorResponse save(DoktorRequest request);
    DoktorResponse update(Long id, DoktorRequest request);
    void delete(Long id);
}