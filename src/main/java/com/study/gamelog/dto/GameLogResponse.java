package com.study.gamelog.dto;

// CODE SMELL: Response DTO exposes internal entity structure directly — no abstraction layer
// BUG: yearPlayed is returned as full timestamp string instead of just the year int
public class GameLogResponse {

    // CODE SMELL: Public fields again — no encapsulation, Jackson would serialize fine
    //             but this is not a good practice
    public int id;
    public String gameName;
    public int rating;
    public String yearPlayed;

    public GameLogResponse(int id, String gameName, int rating, String yearPlayed) {
        this.id = id;
        this.gameName = gameName;
        this.rating = rating;
        this.yearPlayed = yearPlayed;
    }
}
