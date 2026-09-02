package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Nalaz;

/**
 * Repozitorijum interfejs za entitet Nalaz.
 * @author Zlatko Radovanovic
 */
@Repository
public interface NalazRepository extends JpaRepository<Nalaz, Long> {
}