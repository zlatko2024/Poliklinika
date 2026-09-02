package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.UslugaRequest;
import rs.zr.sa.poliklinika.dto.UslugaResponse;
import rs.zr.sa.poliklinika.service.UslugaService;

import java.util.List;

/**
 * REST kontroler za upravljanje uslugama.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/usluge")
@RequiredArgsConstructor
public class UslugaController {

    private final UslugaService uslugaService;

    @GetMapping
    public ResponseEntity<List<UslugaResponse>> findAll() {
        return ResponseEntity.ok(uslugaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UslugaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(uslugaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UslugaResponse> save(@RequestBody UslugaRequest request) {
        return new ResponseEntity<>(uslugaService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UslugaResponse> update(@PathVariable Long id, @RequestBody UslugaRequest request) {
        return ResponseEntity.ok(uslugaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uslugaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}