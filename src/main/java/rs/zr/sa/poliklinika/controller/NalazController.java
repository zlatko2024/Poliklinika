package rs.zr.sa.poliklinika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zr.sa.poliklinika.dto.NalazRequest;
import rs.zr.sa.poliklinika.dto.NalazResponse;
import rs.zr.sa.poliklinika.service.NalazService;

import java.util.List;

/**
 * REST kontroler za upravljanje nalazima.
 * @author Zlatko Radovanovic
 */
@RestController
@RequestMapping("/api/nalazi")
@RequiredArgsConstructor
public class NalazController {

    private final NalazService nalazService;

    @GetMapping
    public ResponseEntity<List<NalazResponse>> findAll() {
        return ResponseEntity.ok(nalazService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NalazResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(nalazService.findById(id));
    }

    @PostMapping
    public ResponseEntity<NalazResponse> save(@RequestBody NalazRequest request) {
        return new ResponseEntity<>(nalazService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NalazResponse> update(@PathVariable Long id, @RequestBody NalazRequest request) {
        return ResponseEntity.ok(nalazService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        nalazService.delete(id);
        return ResponseEntity.noContent().build();
    }
}