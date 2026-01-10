package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.dbmigration.DbMigration;
import io.ebean.platform.sqlite.SQLitePlatform;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election.ElectionType;
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
                "Wybory prezydenckie na kadencję 2023-2028. Prezydent jest najwyższym przedstawicielem Rzeczypospolitej Polskiej i gwarantem ciągłości władzy państwowej. Czuwa nad przestrzeganiem Konstytucji, stoi na straży suwerenności i bezpieczeństwa państwa oraz nienaruszalności i niepodzielności jego terytorium. Głosowanie jest powszechne, równe, bezpośrednie i odbywa się w głosowaniu tajnym.",
                LocalDateTime.of(2023, 10, 1, 9, 0),
                LocalDateTime.of(2023, 10, 15, 20, 0),
                ResultVisibility.ALWAYS,
                ElectionType.SINGLE_CHOICE,
                1,
                List.of()
        );

        Election election2 = electionService.createElection(
                "Wybory Samorządowe 2024",
                "Wybory do władz samorządowych szczebla gminnego, powiatowego i wojewódzkiego. Twój głos ma realny wpływ na rozwój Twojej okolicy, inwestycje w infrastrukturę, edukację oraz kulturę lokalną. W ramach Budżetu Obywatelskiego decydujesz, które projekty zostaną sfinansowane ze środków publicznych w nadchodzącym roku budżetowym.",
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().plusDays(5),
                ResultVisibility.AFTER_CLOSE,
                ElectionType.MULTIPLE_CHOICE,
                2,
                List.of()
        );

        Election election3 = electionService.createElection(
                "Wybory do Parlamentu Europejskiego 2025",
                "Wybory posłów do Parlamentu Europejskiego, jedynej instytucji UE wybieranej bezpośrednio przez obywateli. Parlament Europejski współdecyduje o prawach wpływających na życie codzienne w Unii, od wspierania gospodarki i walki z ubóstwem, po zmiany klimatu i bezpieczeństwo. Wybierz swoich przedstawicieli w Europie.",
                LocalDateTime.of(2025, 5, 1, 8, 0),
                LocalDateTime.of(2025, 5, 10, 22, 0),
                ResultVisibility.AFTER_VOTE,
                ElectionType.MULTIPLE_CHOICE,
                3,
                List.of()
        );

        // Candidates assigned to multiple elections
        candidateService.createCandidate(
                List.of(election1.getId(), election3.getId()), 
                "Jan Kowalski", 
                "Niezależny kandydat z wieloletnim doświadczeniem w administracji publicznej i zarządzaniu kryzysowym. Jego program opiera się na transparentności, wsparciu dla lokalnych przedsiębiorców oraz modernizacji systemów edukacji. Prywatnie pasjonat żeglarstwa i historii nowożytnej.", 
                null, 
                null
        );
        
        candidateService.createCandidate(
                List.of(election1.getId(), election2.getId()), 
                "Anna Nowak", 
                "Kandydatka partii Zmian, z wykształcenia prawniczka i działaczka na rzecz praw człowieka. Skupia się na reformie wymiaru sprawiedliwości, cyfryzacji urzędów oraz ochronie środowiska naturalnego. Wierzy, że nowoczesne państwo to takie, które służy każdemu obywatelowi bez wyjątku.", 
                null, 
                null
        );
        
        candidateService.createCandidate(
                List.of(election2.getId(), election3.getId()), 
                "Piotr Zieliński", 
                "Aktywista społeczny i ekolog, współzałożyciel wielu fundacji wspierających zrównoważony rozwój miast. Walczy o czyste powietrze, rozwój transportu publicznego oraz zielone przestrzenie w centrach miast. Od lat zaangażowany w dialog obywatelski i oddolne inicjatywy mieszkańców.", 
                null, 
                null
        );
    }
}
