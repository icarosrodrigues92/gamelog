package com.study.gamelog.service;

import com.study.gamelog.dto.GameLogRequest;
import com.study.gamelog.dto.GameLogResponse;
import com.study.gamelog.model.GameLog;
import com.study.gamelog.repository.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameLogService {

    // BUG: Field injection instead of constructor injection — makes the class harder to test
    //      and hides dependencies
    @Autowired
    private GameLogRepository repository;

    public GameLogResponse save(GameLogRequest request) {
        // CODE SMELL: Manual mapping scattered inline — should use a mapper class or MapStruct
        GameLog entity = new GameLog(request.gameName, request.rating);

        // BUG: No @Transactional annotation — if anything fails after save, partial state may persist
        GameLog saved = repository.save(entity);

        // CODE SMELL: Ugly toString() used to represent yearPlayed instead of formatting properly
        return new GameLogResponse(
                saved.getId(),
                saved.getGameName(),
                saved.getRating(),
                saved.getYearPlayed().toString()
        );
    }

    // PERFORMANCE: Loading ALL records from DB, then filtering in memory — O(n) memory usage
    // BUG: When both year and name are provided, only year filter is applied — name is silently ignored
    public List<GameLogResponse> findAll(Integer year, String name) {
        List<GameLog> results;

        if (year != null) {
            results = repository.findByYear(year);
        } else if (name != null) {
            results = repository.findByNameContaining(name);
        } else {
            // PERFORMANCE: Loads entire table with no pagination
            results = repository.findAll();
        }

        // CODE SMELL: Building a new list manually instead of using streams
        List<GameLogResponse> response = new ArrayList<>();
        for (GameLog log : results) {
            // BUG: Repeated mapping logic — same code as in save(), duplicated
            response.add(new GameLogResponse(
                    log.getId(),
                    log.getGameName(),
                    log.getRating(),
                    log.getYearPlayed().toString()
            ));
        }

        return response;
    }
}
