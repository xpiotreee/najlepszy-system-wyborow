package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.dbmigration.DbMigration;
import io.ebean.platform.sqlite.SQLitePlatform;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class DbSetup {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run() throws IOException {
        DbMigration dbMigration = DbMigration.create();
        dbMigration.setPlatform(new SQLitePlatform());
        dbMigration.setPathToResources("src/main/resources");
        String version = dbMigration.generateMigration();
    }

    public static void init() {
        String testUsername = "testowy";
        boolean isEmpty = AppProvider
                .getInstance()
                .getUserService()
                .getRepository()
                .findAll(testUsername, null)
                .isEmpty();
        System.out.println("DB init check " + isEmpty);
        if (!isEmpty) {
            System.out.println("Test user found skipping Db init.");
            return;
        }

        System.out.println("Db init");
        AppProvider.getInstance()
                .getUserService()
                .createUser(
                        testUsername,
                        testUsername + "@example.com",
                        "password",
                        "123123120"
                );
        AppProvider.getInstance()
                .getAdminService()
                .createUser(
                        "admin",
                        "admin@example.com",
                        "password"
                );

        ElectionService electionService = AppProvider.getInstance().getElectionService();
        CandidateService candidateService = AppProvider.getInstance().getCandidateService();

        Election election1 = electionService.createElection(
                "Wybory Prezydenckie 2023",
                "Wybory prezydenckie na kadencję 2023-2028",
                LocalDateTime.of(2023, 10, 1, 9, 0),
                LocalDateTime.of(2023, 10, 15, 20, 0),
                ResultVisibility.ALWAYS,
                List.of()
        );

        Election election2 = electionService.createElection(
                "Wybory Samorządowe 2024",
                "Wybory do władz samorządowych",
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().plusDays(5),
                ResultVisibility.AFTER_CLOSE,
                List.of()
        );

        Election election3 = electionService.createElection(
                "Wybory do Parlamentu Europejskiego 2025",
                "Wybory do Parlamentu Europejskiego",
                LocalDateTime.of(2025, 5, 1, 8, 0),
                LocalDateTime.of(2025, 5, 10, 22, 0),
                ResultVisibility.AFTER_VOTE,
                List.of()
        );

        // Candidates assigned to multiple elections
        candidateService.createCandidate(
                List.of(election1.getId(), election3.getId()), 
                "Jan Kowalski", 
                "Niezależny kandydat", 
                null, 
                null
        );
        
        candidateService.createCandidate(
                List.of(election1.getId(), election2.getId()), 
                "Anna Nowak", 
                "Kandydatka partii Zmian", 
                null, 
                null
        );
        
        candidateService.createCandidate(
                List.of(election2.getId(), election3.getId()), 
                "Piotr Zieliński", 
                "Aktywista społeczny", 
                null, 
                null
        );
    }
}
