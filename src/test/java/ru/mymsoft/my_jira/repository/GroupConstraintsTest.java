package ru.mymsoft.my_jira.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.mymsoft.my_jira.model.Group;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
class GroupConstraintsTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    private <T> T persistAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Test
    void whenPersistGroupWithUniqueName_thenSuccess() {
        Group group = new Group(null, "UniqueGroup", "Description", false);

        Group saved = persistAndFlush(group);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("UniqueGroup");
    }

    @Test
    void whenPersistDuplicateGroupName_thenThrowException() {
        Group group1 = new Group(null, "SameName", "Description 1", false);
        Group group2 = new Group(null, "SameName", "Description 2", true);

        persistAndFlush(group1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            groupRepository.saveAndFlush(group2);
        });
    }

    @Test
    void testTableNameAndConstraints() {
        Group group = new Group(null, "TestGroup", "Test Description", false);
        Group saved = persistAndFlush(group);

        assertThat(saved.getId()).isNotNull();

        String tableName = (String) entityManager
            .createNativeQuery("SELECT table_name FROM information_schema.tables WHERE LOWER(table_name) = 'groups'")
            .getSingleResult();

        assertThat(tableName.toLowerCase()).isEqualTo("groups");

        Long uniqueConstraintCount = (Long) entityManager
            .createNativeQuery("SELECT COUNT(*) FROM information_schema.table_constraints " +
                              "WHERE LOWER(table_name) = 'groups' AND constraint_type = 'UNIQUE'")
            .getSingleResult();

        assertThat(uniqueConstraintCount).isGreaterThanOrEqualTo(1L);
    }

    @Test
    void testColumnNullability() {
        Group groupWithNullName = new Group();
        groupWithNullName.setSystemGroup(false);

        assertThrows(Exception.class, () -> {
            persistAndFlush(groupWithNullName);
        });

        Group groupWithNullSystemFlag = new Group();
        groupWithNullSystemFlag.setName("TestGroup");
        groupWithNullSystemFlag.setSystemGroup(null);

        assertThrows(Exception.class, () -> {
            persistAndFlush(groupWithNullSystemFlag);
        });
    }
}
