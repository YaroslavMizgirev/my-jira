package ru.mymsoft.my_jira.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.my_jira.model.ActionType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test-postgres")
@Transactional
@DisplayName("ActionTypeRepository PostgreSQL Integration Tests")
class ActionTypeRepositoryPostgresTest {

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Test
    @DisplayName("Should save and find action type in PostgreSQL")
    void testSaveAndFindInPostgreSQL() {
        // Given
        ActionType actionType = ActionType.builder()
            .name("STATUS_CHANGE")
            .build();

        // When
        ActionType saved = actionTypeRepository.save(actionType);
        actionTypeRepository.flush();

        // Then
        assertNotNull(saved.getId());
        assertEquals("STATUS_CHANGE", saved.getName());

        ActionType found = actionTypeRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("STATUS_CHANGE", found.getName());
    }

    @Test
    @DisplayName("Should handle PostgreSQL unique constraints")
    void testPostgreSQLUniqueConstraints() {
        // Given
        ActionType actionType1 = ActionType.builder()
            .name("COMMENTED")
            .build();

        ActionType saved = actionTypeRepository.save(actionType1);
        actionTypeRepository.flush();

        assertNotNull(saved.getId());

        // When/Then - Test unique constraint
        assertThrows(Exception.class, () -> {
            ActionType duplicate = ActionType.builder()
                .name("COMMENTED")
                .build();
            actionTypeRepository.save(duplicate);
            actionTypeRepository.flush();
        });
    }

    @Test
    @DisplayName("Should find by name with PostgreSQL case sensitivity")
    void testFindByNameCaseSensitivity() {
        // Given
        ActionType actionType = ActionType.builder()
            .name("assigned")
            .build();

        actionTypeRepository.save(actionType);
        actionTypeRepository.flush();

        // When
        ActionType found = actionTypeRepository.findByName("assigned").orElse(null);
        ActionType notFound = actionTypeRepository.findByName("ASSIGNED").orElse(null);

        // Then
        assertNotNull(found);
        assertEquals("assigned", found.getName());

        // PostgreSQL is case sensitive by default for string comparisons
        assertNull(notFound);
    }

    @Test
    @DisplayName("Should handle multiple action types")
    void testMultipleActionTypes() {
        // Given
        ActionType statusChange = ActionType.builder()
            .name("STATUS_CHANGE")
            .build();

        ActionType assigned = ActionType.builder()
            .name("ASSIGNED")
            .build();

        ActionType commented = ActionType.builder()
            .name("COMMENTED")
            .build();

        // When
        actionTypeRepository.save(statusChange);
        actionTypeRepository.save(assigned);
        actionTypeRepository.save(commented);
        actionTypeRepository.flush();

        // Then
        List<ActionType> all = actionTypeRepository.findAll();
        assertEquals(3, all.size());

        assertTrue(all.stream().anyMatch(at -> "STATUS_CHANGE".equals(at.getName())));
        assertTrue(all.stream().anyMatch(at -> "ASSIGNED".equals(at.getName())));
        assertTrue(all.stream().anyMatch(at -> "COMMENTED".equals(at.getName())));
    }
}