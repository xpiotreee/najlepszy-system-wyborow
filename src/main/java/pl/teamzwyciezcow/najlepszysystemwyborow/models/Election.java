package pl.teamzwyciezcow.najlepszysystemwyborow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.joda.time.DateTime;

@Entity
public class Election {
    @Id
    long id;

    String title;
    String description;
    DateTime from;
    DateTime to;
    @Enumerated(EnumType.STRING)
    ResultVisibility resultVisibility;
}
