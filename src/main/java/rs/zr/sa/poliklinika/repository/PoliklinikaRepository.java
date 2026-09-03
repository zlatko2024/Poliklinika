package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Poliklinika;

/**
 * Repozitorijum interfejs za entitet PoliklinikaApp.
 * @author Zlatko Radovanovic
 */
@Repository
public interface PoliklinikaRepository extends JpaRepository<Poliklinika, Long> {
}