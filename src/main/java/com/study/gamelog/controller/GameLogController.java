package com.study.gamelog.controller;

import com.study.gamelog.dto.GameLogRequest;
import com.study.gamelog.dto.GameLogResponse;
import com.study.gamelog.service.GameLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BUG: Missing @RequestMapping base path — all routes live at root, no versioning (e.g. /api/v1)
// VULNERABILITY: No CORS configuration — any origin can call these endpoints
// VULNERABILITY: No authentication or authorization — completely public API
@RestController
public class GameLogController {

    // BUG: Field injection again — same issue as in service
    @Autowired
    private GameLogService service;

    // BUG: Should return 201 Created for a resource creation endpoint, not 200 OK
    // CODE SMELL: No explicit response status — relies on Spring default (200)
    @PostMapping("/games")
    public GameLogResponse create(@Valid @RequestBody GameLogRequest request) {
        // VULNERABILITY: No rate limiting — endpoint can be spammed freely
        return service.save(request);
    }

    // BUG: Endpoint path is inconsistent with POST (/games vs /game/list)
    // CODE SMELL: "list" in REST URL is not idiomatic — GET /games should be the pattern
    @GetMapping("/game/list")
    public List<GameLogResponse> list(
            // BUG: Query param name "ano" is in Portuguese — mixed language in codebase
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String name) {

        // BUG: No input length validation on 'name' — someone could send a 10MB string
        // BUG: No input sanitization on 'name' — XSS possible if response were HTML
        return service.findAll(ano, name);
    }

    // CODE SMELL: No global exception handler (@ControllerAdvice) — validation errors return
    //             Spring's default verbose 400 body with stack traces in some configs
}
