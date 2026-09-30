# Poliklinika - Spring Boot REST API

Kompletna serverska aplikacija razvijena u **Spring Boot** radnom okruzenju, namenjena digitalizaciji i upravljanju procesima u poliklinici (doktori, pacijenti, pregledi, nalazi, usluge i racuni).

## Tehnologije
* **Java** (Spring Boot, Spring Data JPA)
* **Baza podataka:** MySQL / H2 (za testove)
* **Validacija i bezbednost:** Jakarta Validation (`@NotBlank`, `@NotNull`)
* **Lombok** (smanjenje boilerplate koda)
* **Testiranje:** JUnit, Spring Boot Test (integracioni i web testovi)
* **Verzioniranje:** Git & GitHub

## Arhitektura Projekta
* `controller` – REST kontroleri (`DoktorController`, `NalazController`, `PacijentController`...) koji izlazu endpointe.
* `dto` – Objekti za prenos podataka (`Request` i `Response` klase).
* `entity` – JPA entiteti mapirani na bazu podataka (`Doktor`, `Nalaz`, `Pacijent`, `Pregled`, `Uput`, `Racun`, `Usluga`).
* `repository` – Spring Data JPA interfejsi za perzistenciju.
* `test` – Sveobuhvatni integracioni i web testovi za komponente sistema.

## Pokretanje
1. Podesite parametre konekcije sa bazom u `application.properties`.
2. Pokrenite glavnu klasu `CentarApp`.
