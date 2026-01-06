package pl.teamzwyciezcow.najlepszysystemwyborow.models;

import io.ebean.Model;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Candidate extends Model {
    @Id
    private Long id;

    @NotNull
    @Length(255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String photoUrl;

    private String externalLink;



    @ManyToMany
    private List<Election> elections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public List<Election> getElections() {
        return elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }
}
