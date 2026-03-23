package ru.mymsoft.my_jira.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.my_jira.model.Group;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
class GroupRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    private Group developersGroup;
    private Group adminsGroup;

    private <T> T persistAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @BeforeEach
    void setUp() {
        developersGroup = Objects.requireNonNull(Group.builder()
                .name("Developers")
                .description("Software development team")
                .isSystemGroup(false)
                .build(), "Failed to create developersGroup");

        adminsGroup = Objects.requireNonNull(Group.builder()
                .name("Administrators")
                .description("System administrators")
                .isSystemGroup(true)
                .build(), "Failed to create adminsGroup");
    }

    @Test
    void whenSaveGroup_thenGroupIsPersisted() {
        Group savedGroup = groupRepository.save(developersGroup);

        assertThat(savedGroup).isNotNull();
        assertThat(savedGroup.getId()).isNotNull();
        assertThat(savedGroup.getName()).isEqualTo("Developers");
        assertThat(savedGroup.getDescription()).isEqualTo("Software development team");
        assertThat(savedGroup.isSystemGroup()).isFalse();

        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup).isEqualTo(savedGroup);
    }

    @Test
    void whenSaveGroupWithoutDescription_thenGroupIsPersisted() {
        Group groupWithoutDescription = new Group();
        groupWithoutDescription.setName("TestGroup");
        groupWithoutDescription.setSystemGroup(false);

        Group savedGroup = groupRepository.save(groupWithoutDescription);

        assertThat(savedGroup).isNotNull();
        assertThat(savedGroup.getId()).isNotNull();
        assertThat(savedGroup.getName()).isEqualTo("TestGroup");
        assertThat(savedGroup.getDescription()).isNull();
        assertThat(savedGroup.isSystemGroup()).isFalse();
    }

    @Test
    void whenFindById_thenReturnGroup() {
        Group savedGroup = persistAndFlush(developersGroup);

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getName()).isEqualTo("Developers");
        assertThat(foundGroup.get().getDescription()).isEqualTo("Software development team");
        assertThat(foundGroup.get().isSystemGroup()).isFalse();
    }

    @Test
    void whenFindAll_thenReturnAllGroups() {
        persistAndFlush(developersGroup);
        persistAndFlush(adminsGroup);

        List<Group> groups = groupRepository.findAll();

        assertThat(groups).hasSize(2);
        assertThat(groups).extracting(Group::getName)
                .containsExactlyInAnyOrder("Developers", "Administrators");
        assertThat(groups).extracting(Group::isSystemGroup)
                .containsExactlyInAnyOrder(false, true);
    }

    @Test
    void whenFindByName_thenReturnGroup() {
        persistAndFlush(developersGroup);

        Optional<Group> foundGroup = groupRepository.findByName("Developers");

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getName()).isEqualTo("Developers");
        assertThat(foundGroup.get().getDescription()).isEqualTo("Software development team");
    }

    @Test
    void whenFindByIsSystemGroup_thenReturnSystemGroups() {
        persistAndFlush(developersGroup);
        persistAndFlush(adminsGroup);

        List<Group> systemGroups = groupRepository.findAllByIsSystemGroupTrueOrderByNameAsc();
        List<Group> nonSystemGroups = groupRepository.findAllByIsSystemGroupFalseOrderByNameAsc();

        assertThat(systemGroups).hasSize(1);
        assertThat(systemGroups.get(0).getName()).isEqualTo("Administrators");

        assertThat(nonSystemGroups).hasSize(1);
        assertThat(nonSystemGroups.get(0).getName()).isEqualTo("Developers");
    }

    @Test
    void whenUpdateGroup_thenGroupIsUpdated() {
        Group savedGroup = persistAndFlush(developersGroup);

        savedGroup.setName("Senior Developers");
        savedGroup.setDescription("Senior software development team");
        savedGroup.setSystemGroup(true);
        Group updatedGroup = groupRepository.save(savedGroup);

        assertThat(updatedGroup.getName()).isEqualTo("Senior Developers");
        assertThat(updatedGroup.getDescription()).isEqualTo("Senior software development team");
        assertThat(updatedGroup.isSystemGroup()).isTrue();

        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup.getName()).isEqualTo("Senior Developers");
        assertThat(foundGroup.isSystemGroup()).isTrue();
    }

    @Test
    void whenDeleteGroup_thenGroupIsRemoved() {
        Group savedGroup = persistAndFlush(developersGroup);

        groupRepository.delete(savedGroup);
        entityManager.flush();

        Group deletedGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(deletedGroup).isNull();
    }

    @Test
    void whenSaveDuplicateName_thenThrowException() {
        persistAndFlush(developersGroup);

        Group duplicateGroup = new Group();
        duplicateGroup.setName("Developers");
        duplicateGroup.setDescription("Different description");
        duplicateGroup.setSystemGroup(true);

        assertThrows(DataIntegrityViolationException.class, () -> {
            groupRepository.saveAndFlush(duplicateGroup);
        });
    }

    @Test
    void whenFindByNonExistingName_thenReturnEmpty() {
        Optional<Group> foundGroup = groupRepository.findByName("NonExisting");

        assertThat(foundGroup).isEmpty();
    }

    @Test
    void testCaseSensitiveNameSearch() {
        persistAndFlush(developersGroup);

        assertThat(groupRepository.findByName("Developers")).isPresent();
    }

    @Test
    void testLobDescriptionPersistence() {
        String longDescription = "A".repeat(5000);
        developersGroup.setDescription(longDescription);

        Group savedGroup = persistAndFlush(developersGroup);

        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup.getDescription()).isEqualTo(longDescription);
        assertThat(foundGroup.getDescription()).hasSize(5000);
    }

    @Test
    void testDefaultIsSystemGroupValueOnPersist() {
        Group groupWithDefault = new Group();
        groupWithDefault.setName("DefaultGroup");

        Group savedGroup = persistAndFlush(groupWithDefault);

        assertThat(savedGroup.isSystemGroup()).isFalse();
    }
}
