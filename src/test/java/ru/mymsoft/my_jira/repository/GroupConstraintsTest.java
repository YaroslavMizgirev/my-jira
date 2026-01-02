package ru.mymsoft.my_jira.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.mymsoft.my_jira.model.Group;

@DataJpaTest
class GroupConstraintsTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenPersistGroupWithUniqueName_thenSuccess() {
        Group group = new Group(null, "UniqueGroup", "Description", false);

        Group saved = entityManager.persistAndFlush(group);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("UniqueGroup");
    }

    @Test
    void whenPersistDuplicateGroupName_thenThrowException() {
        Group group1 = new Group(null, "SameName", "Description 1", false);
        Group group2 = new Group(null, "SameName", "Description 2", true);

        entityManager.persistAndFlush(group1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            entityManager.persistAndFlush(group2);
        });
    }

    @Test
    void testTableNameAndConstraints() {
        Group group = new Group(null, "TestGroup", "Test Description", false);
        Group saved = entityManager.persistAndFlush(group);

        assertThat(saved.getId()).isNotNull();

        // Verify table name
        String tableName = (String) entityManager.getEntityManager()
            .createNativeQuery("SELECT table_name FROM information_schema.tables WHERE table_name = 'groups'")
            .getSingleResult();

        assertThat(tableName).isEqualTo("groups");

        // Verify unique constraint exists
        Long uniqueConstraintCount = (Long) entityManager.getEntityManager()
            .createNativeQuery("SELECT COUNT(*) FROM information_schema.table_constraints " +
                              "WHERE table_name = 'groups' AND constraint_type = 'UNIQUE'")
            .getSingleResult();

        assertThat(uniqueConstraintCount).isGreaterThanOrEqualTo(1L);
    }

    @Test
    void testColumnNullability() {
        // Test that name cannot be null
        Group groupWithNullName = new Group();
        groupWithNullName.setSystemGroup(false);

        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(groupWithNullName);
        });

        // Test that isSystemGroup cannot be null
        Group groupWithNullSystemFlag = new Group();
        groupWithNullSystemFlag.setName("TestGroup");
        groupWithNullSystemFlag.setSystemGroup(null);

        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(groupWithNullSystemFlag);
        });
    }
}
