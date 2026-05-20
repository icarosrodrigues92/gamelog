package com.study.gamelog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// BUG: Missing @Table annotation with explicit name
// BUG: No equals/hashCode override — problematic for JPA entity comparisons in Sets and caches
@Entity
public class GameLog {

    // BUG: Using auto-increment int instead of Long — will overflow at scale
    // BUG: No explicit @Column name — relies on field name which can differ per DB dialect
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // BUG: No length constraint on @Column — defaults to 255 but no validation aligned with it
    // CODE SMELL: raw field with no encapsulation
    @Column(nullable = false)
    public String gameName;

    @Column(nullable = false)
    public int rating;

    // BUG: Storing full timestamp when only year is needed — wasted data, misleading field name
    @Column(nullable = false)
    private LocalDateTime yearPlayed;

    // BUG: Missing no-arg constructor required by JPA spec (only implicit default exists here, but
    //       adding a parameterized constructor without explicit no-arg would break JPA proxying)
    public GameLog(String gameName, int rating) {
        this.gameName = gameName;
        this.rating = rating;
        // BUG: Using LocalDateTime.now() directly in constructor — hard to test, not injected
        this.yearPlayed = LocalDateTime.now();
    }

    public GameLog() {}

    public int getId() {
        return id;
    }

    // CODE SMELL: Setter exposes internal ID — external mutation of PK is dangerous
    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getYearPlayed() {
        return yearPlayed;
    }

    public void setYearPlayed(LocalDateTime yearPlayed) {
        this.yearPlayed = yearPlayed;
    }
}
