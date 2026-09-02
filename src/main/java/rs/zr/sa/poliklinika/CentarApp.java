package rs.zr.sa.poliklinika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Glavna klasa za pokretanje poliklinika aplikacije.
 * @author Zlatko Radovanovic
 */
@SpringBootApplication
public class CentarApp {
    public static void main(String[] args) {
        SpringApplication.run(CentarApp.class, args);
    }
}