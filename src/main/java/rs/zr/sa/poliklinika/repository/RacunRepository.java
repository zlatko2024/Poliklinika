package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Racun;

/**
 * Repozitorijum interfejs za entitet Racun.
 * @author Zlatko Radovanovic
 */
@Repository
public interface RacunRepository extends JpaRepository<Racun, Long> {
}