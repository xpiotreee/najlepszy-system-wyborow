package pl.teamzwyciezcow.najlepszysystemwyborow.models;

import io.ebean.Model;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Election extends Model {
    
    public enum ElectionType {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE
    }

    @Id
    long id;

    @NotNull
    @Length(255)
    String title;

    @Column(columnDefinition = "TEXT")
    String description;

    @NotNull
    LocalDateTime startDate;

    @NotNull
    LocalDateTime endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    ResultVisibility resultVisibility;

    @NotNull
    @Enumerated(EnumType.STRING)
    ElectionType electionType = ElectionType.SINGLE_CHOICE;

    Integer maxChoices = 1;

    @ManyToMany(mappedBy = "elections", cascade = CascadeType.ALL)
    List<Candidate> candidates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ResultVisibility getResultVisibility() {
        return resultVisibility;
    }

    public void setResultVisibility(ResultVisibility resultVisibility) {
        this.resultVisibility = resultVisibility;
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public Integer getMaxChoices() {
        return maxChoices;
    }

    public void setMaxChoices(Integer maxChoices) {
        this.maxChoices = maxChoices;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
