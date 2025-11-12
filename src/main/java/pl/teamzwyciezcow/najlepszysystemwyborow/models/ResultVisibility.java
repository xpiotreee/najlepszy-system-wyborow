package pl.teamzwyciezcow.najlepszysystemwyborow.models;

import io.ebean.annotation.DbEnumValue;

public enum ResultVisibility {
    ALWAYS("ALWAYS"),
    AFTER_VOTE("AFTER_VOTE"),
    AFTER_CLOSE("AFTER_CLOSE");

    String dbValue;
    ResultVisibility(String dbValue) {
        this.dbValue = dbValue;
    }

    @DbEnumValue
    public String getValue() {
        return dbValue;
    }
}
