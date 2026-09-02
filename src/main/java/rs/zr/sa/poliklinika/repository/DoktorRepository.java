package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Doktor;

/**
 * Repozitorijum interfejs za entitet Doktor.
 * @author Zlatko Radovanovic
 */
@Repository
public interface DoktorRepository extends JpaRepository<Doktor, Long> {
}