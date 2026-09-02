package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.PacijentRequest;
import rs.zr.sa.poliklinika.dto.PacijentResponse;
import rs.zr.sa.poliklinika.service.PacijentService;

import java.util.List;

/**
 * REST kontroler za upravljanje pacijentima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/pacijenti")
@RequiredArgsConstructor
public class PacijentController {

    private final PacijentService pacijentService;

    @GetMapping
    public ResponseEntity<List<PacijentResponse>> findAll() {
        return ResponseEntity.ok(pacijentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacijentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pacijentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PacijentResponse> save(@RequestBody PacijentRequest request) {
        return new ResponseEntity<>(pacijentService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacijentResponse> update(@PathVariable Long id, @RequestBody PacijentRequest request) {
        return ResponseEntity.ok(pacijentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacijentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}