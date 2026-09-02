package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Uput;

/**
 * Repozitorijum interfejs za entitet Uput.
 * @author Zlatko Radovanovic
 */
@Repository
public interface UputRepository extends JpaRepository<Uput, Long> {
}