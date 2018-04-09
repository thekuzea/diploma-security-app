package com.thekuzea.diploma.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String role;

    @DBRef
    private List<App> forbiddenApps;

    @DBRef
    private List<Website> forbiddenWebsites;

    public User() {
    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<com.thekuzea.diploma.model.App> getForbiddenApps() {
        return forbiddenApps;
    }

    public void setForbiddenApps(List<com.thekuzea.diploma.model.App> forbiddenApps) {
        this.forbiddenApps = forbiddenApps;
    }

    public List<com.thekuzea.diploma.model.Website> getForbiddenWebsites() {
        return forbiddenWebsites;
    }

    public void setForbiddenWebsites(List<com.thekuzea.diploma.model.Website> forbiddenWebsites) {
        this.forbiddenWebsites = forbiddenWebsites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(role, user.role) &&
                Objects.equals(forbiddenApps, user.forbiddenApps) &&
                Objects.equals(forbiddenWebsites, user.forbiddenWebsites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role, forbiddenApps, forbiddenWebsites);
    }

    @Override
    public String toString() {
        return username;
    }
}
