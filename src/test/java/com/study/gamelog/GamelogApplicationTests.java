package com.study.gamelog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// CODE SMELL: Only a smoke test exists — zero business logic is covered
// BUG: No tests for validation constraints, service logic, or repository queries
@SpringBootTest
class GamelogApplicationTests {

    @Test
    void contextLoads() {
        // This only verifies the Spring context starts — not very useful
    }
}
