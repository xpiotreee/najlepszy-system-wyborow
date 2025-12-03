package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.DB;
import io.ebean.dbmigration.DbMigration;
import io.ebean.platform.sqlite.SQLitePlatform;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

import java.io.IOException;

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
        User found = AppProvider
                .getInstance()
                .getUserService()
                .getRepository()
                .findAll(testUsername, null)
                .getFirst();
        if (found != null) {
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
    }
}
