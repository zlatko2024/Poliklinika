package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Pacijent;

/**
 * Repozitorijum interfejs za entitet Pacijent.
 * @author Zlatko Radovanovic
 */
@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long> {
}