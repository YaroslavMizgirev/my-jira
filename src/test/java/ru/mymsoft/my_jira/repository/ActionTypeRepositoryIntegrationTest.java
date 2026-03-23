package ru.mymsoft.my_jira.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.my_jira.config.TestContainerConfig;
import ru.mymsoft.my_jira.model.ActionType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test-postgres")
@Transactional
@DisplayName("ActionTypeRepository Integration Tests with PostgreSQL")
class ActionTypeRepositoryIntegrationTest extends TestContainerConfig {

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Test
    @DisplayName("Should save and find action type in PostgreSQL")
    void testSaveAndFindInPostgreSQL() {
        // Given
        ActionType actionType = ActionType.builder()
            .name("            STATUS_CHANGE             ")
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
    @DisplayName("Should handle PostgreSQL specific features")
    void testPostgreSQLSpecificFeatures() {
        // Test case sensitivity
        ActionType actionType1 = ActionType.builder()
            .name("status_change")
            .build();

        ActionType actionType2 = ActionType.builder()
            .name("STATUS_CHANGE")
            .build();

        actionTypeRepository.save(actionType1);
        actionTypeRepository.save(actionType2);
        actionTypeRepository.flush();

        List<ActionType> all = actionTypeRepository.findAll();
        assertEquals(2, all.size());

        // Test unique constraint
        assertThrows(Exception.class, () -> {
            ActionType duplicate = ActionType.builder()
                .name("STATUS_CHANGE")
                .build();
            actionTypeRepository.save(duplicate);
            actionTypeRepository.flush();
        });
    }
}
