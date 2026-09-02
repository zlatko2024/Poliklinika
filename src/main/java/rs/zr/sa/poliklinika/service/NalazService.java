package rs.zr.sa.poliklinika.service;

import rs.zr.sa.poliklinika.dto.NalazRequest;
import rs.zr.sa.poliklinika.dto.NalazResponse;

import java.util.List;

/**
 * Servisni interfejs za upravljanje nalazima.
 * @author Zlatko Radovanovic
 */
public interface NalazService {
    List<NalazResponse> findAll();
    NalazResponse findById(Long id);
    NalazResponse save(NalazRequest request);
    NalazResponse update(Long id, NalazRequest request);
    void delete(Long id);
}