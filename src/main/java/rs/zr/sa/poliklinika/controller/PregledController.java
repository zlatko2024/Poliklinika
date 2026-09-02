package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.PregledRequest;
import rs.zr.sa.poliklinika.dto.PregledResponse;
import rs.zr.sa.poliklinika.service.PregledService;

import java.util.List;

/**
 * REST kontroler za upravljanje pregledima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/pregledi")
@RequiredArgsConstructor
public class PregledController {

    private final PregledService pregledService;

    @GetMapping
    public ResponseEntity<List<PregledResponse>> findAll() {
        return ResponseEntity.ok(pregledService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PregledResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pregledService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PregledResponse> save(@RequestBody PregledRequest request) {
        return new ResponseEntity<>(pregledService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PregledResponse> update(@PathVariable Long id, @RequestBody PregledRequest request) {
        return ResponseEntity.ok(pregledService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pregledService.delete(id);
        return ResponseEntity.noContent().build();
    }
}