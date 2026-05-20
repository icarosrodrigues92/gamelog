package com.study.gamelog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// CODE SMELL: DTO is mutable with public fields — no encapsulation, breaks information hiding
// BUG: No @NotBlank on gameName — @NotNull alone allows empty strings like ""
public class GameLogRequest {

    @NotNull(message = "Game name is required")
    public String gameName;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    public Integer rating;

    // CODE SMELL: No constructor — forces callers to mutate fields directly
    // BUG: Missing @NotBlank means " " (blank string) passes validation
}
