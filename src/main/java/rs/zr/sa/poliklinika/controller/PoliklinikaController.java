package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.PoliklinikaRequest;
import rs.zr.sa.poliklinika.dto.PoliklinikaResponse;
import rs.zr.sa.poliklinika.service.PoliklinikaService;

import java.util.List;

/**
 * REST kontroler za upravljanje poliklinikama.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/poliklinike")
@RequiredArgsConstructor
public class PoliklinikaController {

    private final PoliklinikaService poliklinikaService;

    @GetMapping
    public ResponseEntity<List<PoliklinikaResponse>> findAll() {
        return ResponseEntity.ok(poliklinikaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliklinikaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(poliklinikaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PoliklinikaResponse> save(@RequestBody PoliklinikaRequest request) {
        return new ResponseEntity<>(poliklinikaService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PoliklinikaResponse> update(@PathVariable Long id, @RequestBody PoliklinikaRequest request) {
        return ResponseEntity.ok(poliklinikaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        poliklinikaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}