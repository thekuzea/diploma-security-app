package com.thekuzea.diploma.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Document(collection = "applications")
public class App {

    @Id
    private String id;

    private String name;

    private Boolean isForeverBlocked;

    private LocalTime forbiddanceStart;

    private LocalTime forbiddanceEnd;

    public App() {
    }

    public App(String name) {
        this.name = name;
    }

    public App(String name, Boolean isForeverBlocked) {
        this.name = name;
        this.isForeverBlocked = isForeverBlocked;
    }

    public App(String name, Boolean isForeverBlocked, LocalTime forbiddanceStart, LocalTime forbiddanceEnd) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        App app = (App) o;
        return Objects.equals(id, app.id) &&
                Objects.equals(name, app.name) &&
                Objects.equals(isForeverBlocked, app.isForeverBlocked) &&
                Objects.equals(forbiddanceStart, app.forbiddanceStart) &&
                Objects.equals(forbiddanceEnd, app.forbiddanceEnd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, isForeverBlocked, forbiddanceStart, forbiddanceEnd);
    }

    @Override
    public String toString() {
        return name + " | " +
                ((forbiddanceStart != null ) ? forbiddanceStart.format(DateTimeFormatter.ofPattern("HH:mm")) : "") +
                ((isForeverBlocked) ? "always" : "-") +
                ((forbiddanceEnd != null ) ? forbiddanceEnd.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
    }
}
