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
        // TODO use service
        User found = DB.find(User.class).where().eq("fullName", "testowy").findOne();
        if (found != null) {
            System.out.println("Test user found skipping Db init.");
            return;
        }

        System.out.println("Db init");
        User user = new User(testUsername, "123123120", testUsername + "@example.com", "password");
        DB.save(user);
    }
}
