package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.DoktorRequest;
import rs.zr.sa.poliklinika.dto.DoktorResponse;
import rs.zr.sa.poliklinika.service.DoktorService;

import java.util.List;

/**
 * REST kontroler za upravljanje doktorima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/doktori")
@RequiredArgsConstructor
public class DoktorController {

    private final DoktorService doktorService;

    @GetMapping
    public ResponseEntity<List<DoktorResponse>> findAll() {
        return ResponseEntity.ok(doktorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoktorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doktorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DoktorResponse> save(@RequestBody DoktorRequest request) {
        return new ResponseEntity<>(doktorService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoktorResponse> update(@PathVariable Long id, @RequestBody DoktorRequest request) {
        return ResponseEntity.ok(doktorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doktorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}