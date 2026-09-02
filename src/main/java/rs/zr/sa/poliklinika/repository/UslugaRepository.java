package rs.zr.sa.poliklinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.zr.sa.poliklinika.entity.Usluga;

/**
 * Repozitorijum interfejs za entitet Usluga.
 * @author Zlatko Radovanovic
 */
@Repository
public interface UslugaRepository extends JpaRepository<Usluga, Long> {
}