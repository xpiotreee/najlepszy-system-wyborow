package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.config.PlatformConfig;
import io.ebean.dbmigration.DbMigration;
import io.ebean.platform.sqlite.SQLitePlatform;

import java.io.IOException;

public class DbSetup {
    public static void run() throws IOException {
        DbMigration dbMigration = DbMigration.create();
        dbMigration.setPlatform(new SQLitePlatform());
        dbMigration.setPathToResources("src/main/resources");
        String version = dbMigration.generateMigration();
    }
}
