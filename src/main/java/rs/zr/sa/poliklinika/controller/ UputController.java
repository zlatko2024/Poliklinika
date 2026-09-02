package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.UputRequest;
import rs.zr.sa.poliklinika.dto.UputResponse;
import rs.zr.sa.poliklinika.service.UputService;

import java.util.List;

/**
 * REST kontroler za upravljanje uputima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/uputi")
@RequiredArgsConstructor
public class UputController {

    private final UputService uputService;

    @GetMapping
    public ResponseEntity<List<UputResponse>> findAll() {
        return ResponseEntity.ok(uputService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UputResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(uputService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UputResponse> save(@RequestBody UputRequest request) {
        return new ResponseEntity<>(uputService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UputResponse> update(@PathVariable Long id, @RequestBody UputRequest request) {
        return ResponseEntity.ok(uputService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uputService.delete(id);
        return ResponseEntity.noContent().build();
    }
}