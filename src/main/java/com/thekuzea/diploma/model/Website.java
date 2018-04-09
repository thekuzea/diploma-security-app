package com.thekuzea.diploma.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Document(collection = "websites")
public class Website {

    @Id
    private String id;

    private String website;

    private Boolean isForeverBlocked;

    private LocalTime forbiddanceStart;

    private LocalTime forbiddanceEnd;

    public Website() {
    }

    public Website(String website) {
        this.website = website;
    }

    public Website(String website, Boolean isForeverBlocked) {
        this.website = website;
        this.isForeverBlocked = isForeverBlocked;
    }

    public Website(String website, Boolean isForeverBlocked, LocalTime forbiddanceStart, LocalTime forbiddanceEnd) {
        this.website = website;
        this.isForeverBlocked = isForeverBlocked;
        this.forbiddanceStart = forbiddanceStart;
        this.forbiddanceEnd = forbiddanceEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getForeverBlocked() {
        return isForeverBlocked;
    }

    public void setForeverBlocked(Boolean foreverBlocked) {
        isForeverBlocked = foreverBlocked;
    }

    public LocalTime getForbiddanceStart() {
        return forbiddanceStart;
    }

    public void setForbiddanceStart(LocalTime forbiddanceStart) {
        this.forbiddanceStart = forbiddanceStart;
    }

    public LocalTime getForbiddanceEnd() {
        return forbiddanceEnd;
    }

    public void setForbiddanceEnd(LocalTime forbiddanceEnd) {
        this.forbiddanceEnd = forbiddanceEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Website website1 = (Website) o;
        return Objects.equals(id, website1.id) &&
                Objects.equals(website, website1.website) &&
                Objects.equals(isForeverBlocked, website1.isForeverBlocked) &&
                Objects.equals(forbiddanceStart, website1.forbiddanceStart) &&
                Objects.equals(forbiddanceEnd, website1.forbiddanceEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, website, isForeverBlocked, forbiddanceStart, forbiddanceEnd);
    }

    @Override
    public String toString() {
        return website + " | " +
                ((forbiddanceStart != null ) ? forbiddanceStart.format(DateTimeFormatter.ofPattern("HH:mm")) : "") +
                ((isForeverBlocked) ? "always" : "-") +
                ((forbiddanceEnd != null ) ? forbiddanceEnd.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
    }
}
