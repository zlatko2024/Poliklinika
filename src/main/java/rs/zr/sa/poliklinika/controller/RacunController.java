package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.RacunRequest;
import rs.zr.sa.poliklinika.dto.RacunResponse;
import rs.zr.sa.poliklinika.service.RacunService;

import java.util.List;

/**
 * REST kontroler za upravljanje racunima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/racuni")
@RequiredArgsConstructor
public class RacunController {

    private final RacunService racunService;

    @GetMapping
    public ResponseEntity<List<RacunResponse>> findAll() {
        return ResponseEntity.ok(racunService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacunResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(racunService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RacunResponse> save(@RequestBody RacunRequest request) {
        return new ResponseEntity<>(racunService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RacunResponse> update(@PathVariable Long id, @RequestBody RacunRequest request) {
        return ResponseEntity.ok(racunService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        racunService.delete(id);
        return ResponseEntity.noContent().build();
    }
}