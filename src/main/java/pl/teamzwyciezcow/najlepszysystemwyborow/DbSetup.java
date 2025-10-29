package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;

public class DbSetup {
    public static void main(String[] args) {
        MigrationConfig config = new MigrationConfig();
        config.setMigrationPath("dbmigration");;
        config.setDbUsername("");
        config.setDbPassword("");
        config.setDbUrl("jdbc:sqlite:database.db");

        MigrationRunner runner = new MigrationRunner(config);
        runner.run();
    }
}
