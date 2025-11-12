package pl.teamzwyciezcow.najlepszysystemwyborow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    long id;

    String fullName;
    String pesel;
    String email;
    String password;

    public User(String fullName, String pesel, String email, String password) {
        this.fullName = fullName;
        this.pesel = pesel;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
