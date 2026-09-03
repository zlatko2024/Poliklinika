package rs.zr.sa.poliklinika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Glavna klasa za pokretanje poliklinika aplikacije.
 * @author Zlatko Radovanovic
 */
@SpringBootApplication
public class PoliklinikaApp {
    public static void main(String[] args) {
        SpringApplication.run(PoliklinikaApp.class, args);
    }
}