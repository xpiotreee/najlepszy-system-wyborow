# Najlepszy System Wyborów

Aplikacja desktopowa typu e-voting stworzona w technologii JavaFX, umożliwiająca przeprowadzanie bezpiecznych i przejrzystych wyborów lokalnych. System oferuje rozdzielone panele dla administratorów i użytkowników, obsługę różnych typów głosowań oraz estetyczny, tradycyjny interfejs graficzny.

## Funkcjonalności

### Dla Użytkownika
*   **Rejestracja i Logowanie:** Bezpieczne zakładanie konta (z hashowaniem haseł) oraz logowanie.
*   **Przeglądanie Wyborów:** Lista nadchodzących, trwających i zakończonych głosowań w formie czytelnych kart.
*   **Głosowanie:**
    *   Wybory Jednokrotnego Wyboru (Single Choice).
    *   Wybory Wielokrotnego Wyboru (Multiple Choice) z limitem głosów.
    *   Walidacja uprawnień (możliwość oddania głosu tylko raz).
*   **Profil Użytkownika:**
    *   Edycja danych osobowych i hasła.
    *   Historia oddanych głosów z możliwością podglądu szczegółów wyborów.
*   **Widoczność Wyników:** Podgląd liczby głosów zależny od konfiguracji wyborów (np. dopiero po zakończeniu lub po oddaniu głosu).

### Dla Administratora
*   **Panel Zarządzania (Dashboard):** Szybki dostęp do kluczowych sekcji systemu.
*   **Zarządzanie Wyborami:** Tworzenie i edycja wyborów (tytuł, opis, daty, typ, widoczność wyników).
*   **Zarządzanie Kandydatami:** Dodawanie, edycja i usuwanie kandydatów; przypisywanie ich do wielu wyborów jednocześnie.
*   **Zarządzanie Użytkownikami:** Podgląd, edycja i usuwanie kont użytkowników.
*   **Audyt Głosów:** Przegląd wszystkich oddanych głosów w systemie z możliwością ich edycji (np. w przypadku błędów administracyjnych).

---

## Dokumentacja Projektu

Projekt oparty jest na architekturze warstwowej (Controller-Service-Repository), wykorzystując **Ebean ORM** do komunikacji z bazą danych **SQLite**.

### Struktura Katalogów (src/main/java/...)

*   **controllers/**: Warstwa prezentacji, obsługująca logikę widoków FXML.
    *   admin/: Kontrolery panelu administratora (elections, candidates, users, votes).
    *   user/: Kontrolery panelu użytkownika (auth, elections, profile).
    *   NavigationController: Interfejs do zarządzania nawigacją.
    *   MainController: Główny kontroler layoutu (BorderPane), obsługujący menu.
*   **models/**: Klasy domenowe (Encje) mapowane na tabele bazy danych.
    *   User: Dane użytkownika (Imię, Nazwisko, Email, PESEL, Hasło).
    *   Election: Konfiguracja głosowania (Typ, Daty, Widoczność).
    *   Candidate: Kandydaci biorący udział w wyborach.
    *   Vote: Rekord pojedynczego głosu (Użytkownik -> Wybory -> Kandydat).
*   **repositories/**: Warstwa dostępu do danych. Abstrakcja nad Ebean ORM.
    *   Interfejsy (UserRepository, VoteRepository...) i ich implementacje w impl/.
*   **services/**: Warstwa logiki biznesowej.
    *   Odpowiada za walidację (np. czy użytkownik już głosował), transakcje i logikę aplikacji (np. rejestracja).
*   **utils/**: Klasy pomocnicze, np. DateUtils do formatowania dat czy SecurityUtils do hashowania haseł.
*   **AppProvider**: Klasa Singleton (Dependency Injection), dostarczająca instancje serwisów i kontrolerów w całej aplikacji.
*   **DbSetup**: Klasa narzędziowa do inicjalizacji bazy danych i seedowania danych testowych.

### Zasoby (src/main/resources/...)

*   **views/**: Pliki widoków .fxml definiujące interfejs użytkownika.
*   **css/**: Arkusze stylów (main.css, home.css) definiujące wygląd aplikacji (kolory Warm Oak, Aged Paper).
*   **assets/**: Grafiki (tła).
*   **dbmigration/**: Skrypty migracyjne SQL generowane przez Ebean.

---

## Możliwe Ulepszenia i Rozwój

Aplikacja została zaprojektowana w sposób modułowy, co ułatwia jej dalszy rozwój. Oto potencjalne ścieżki ewolucji:

### 1. Warstwa API i Wersja Online
Obecnie aplikacja działa lokalnie (monolit desktopowy). Aby umożliwić dostęp online:
*   **REST API / GraphQL:** Należy wystawić warstwę Service poprzez kontrolery REST (np. używając Spring Boot). Logika biznesowa (UserService, VoteService) jest już odseparowana od UI, co ułatwia to zadanie.
*   **Autoryzacja:** Implementacja JWT (JSON Web Tokens) zamiast stanowej sesji w pamięci (UserService.loggedIn).

### 2. Nowoczesny Frontend
Po stworzeniu API, można zastąpić (lub uzupełnić) interfejs JavaFX nowoczesną aplikacją webową:
*   **Technologie:** React, Vue.js.
*   **Zalety:** Dostępność z przeglądarki, responsywność mobilna (RWD).

### 3. Bezpieczeństwo
*   **Weryfikacja tożsamości:** Integracja z Profilem Zaufanym lub e-Dowodem.

### 4. Skalowalność Bazy Danych
*   Prosta migracja z SQLite na PostgreSQL lub MySQL (Ebean wspiera wiele różnych baz danych) w przypadku wdrożenia sieciowego, aby obsłużyć dostęp wielu użytkowników.