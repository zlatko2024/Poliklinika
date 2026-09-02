package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Pregled;

/**
 * Repozitorijum interfejs za entitet Pregled.
 * @author Zlatko Radovanovic
 */
@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long> {
}