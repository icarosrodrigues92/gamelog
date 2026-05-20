package com.study.gamelog.repository;

import com.study.gamelog.model.GameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Integer> {

    // BUG: Native SQL query bypasses JPA abstraction and is not portable across DB vendors
    // VULNERABILITY: Raw string interpolation in native query — SQL Injection risk if
    //               name were ever passed via @Param without sanitization (and it isn't used here)
    // BUG: YEAR() function is H2/MySQL specific — won't work on PostgreSQL (should use EXTRACT)
    // PERFORMANCE: No index defined on yearPlayed or gameName columns
    @Query(value = "SELECT * FROM game_log WHERE YEAR(year_played) = ?1", nativeQuery = true)
    List<GameLog> findByYear(int year);

    // BUG: Using LIKE with leading wildcard (%?%) disables index usage — full table scan always
    // BUG: Case sensitivity behavior varies per DB — no LOWER() or ILIKE used
    @Query(value = "SELECT * FROM game_log WHERE game_name LIKE %?1%", nativeQuery = true)
    List<GameLog> findByNameContaining(String name);

    // CODE SMELL: findAll() is inherited but used directly in service without pagination —
    //             will load the entire table into memory
}
