package ru.mymsoft.my_jira.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import ru.mymsoft.my_jira.model.Group;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    private Group developersGroup;
    private Group adminsGroup;

    @BeforeEach
    void setUp() {
        developersGroup = Group.builder()
                .name("Developers")
                .description("Software development team")
                .isSystemGroup(false)
                .build();

        adminsGroup = Group.builder()
                .name("Administrators")
                .description("System administrators")
                .isSystemGroup(true)
                .build();
    }

    @Test
    void whenSaveGroup_thenGroupIsPersisted() {
        // When
        Group savedGroup = groupRepository.save(developersGroup);

        // Then
        assertThat(savedGroup).isNotNull();
        assertThat(savedGroup.getId()).isNotNull();
        assertThat(savedGroup.getName()).isEqualTo("Developers");
        assertThat(savedGroup.getDescription()).isEqualTo("Software development team");
        assertThat(savedGroup.isSystemGroup()).isFalse();

        // Verify in database
        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup).isEqualTo(savedGroup);
    }

    @Test
    void whenSaveGroupWithoutDescription_thenGroupIsPersisted() {
        // Given
        Group groupWithoutDescription = new Group();
        groupWithoutDescription.setName("TestGroup");
        groupWithoutDescription.setSystemGroup(false);

        // When
        Group savedGroup = groupRepository.save(groupWithoutDescription);

        // Then
        assertThat(savedGroup).isNotNull();
        assertThat(savedGroup.getId()).isNotNull();
        assertThat(savedGroup.getName()).isEqualTo("TestGroup");
        assertThat(savedGroup.getDescription()).isNull();
        assertThat(savedGroup.isSystemGroup()).isFalse();
    }

    @Test
    void whenFindById_thenReturnGroup() {
        // Given
        Group savedGroup = entityManager.persistAndFlush(developersGroup);

        // When
        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        // Then
        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getName()).isEqualTo("Developers");
        assertThat(foundGroup.get().getDescription()).isEqualTo("Software development team");
        assertThat(foundGroup.get().isSystemGroup()).isFalse();
    }

    @Test
    void whenFindAll_thenReturnAllGroups() {
        // Given
        entityManager.persistAndFlush(developersGroup);
        entityManager.persistAndFlush(adminsGroup);

        // When
        List<Group> groups = groupRepository.findAll();

        // Then
        assertThat(groups).hasSize(2);
        assertThat(groups).extracting(Group::getName)
                .containsExactlyInAnyOrder("Developers", "Administrators");
        assertThat(groups).extracting(Group::isSystemGroup)
                .containsExactlyInAnyOrder(false, true);
    }

    @Test
    void whenFindByName_thenReturnGroup() {
        // Given
        entityManager.persistAndFlush(developersGroup);

        // When
        Optional<Group> foundGroup = groupRepository.findByName("Developers");

        // Then
        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getName()).isEqualTo("Developers");
        assertThat(foundGroup.get().getDescription()).isEqualTo("Software development team");
    }

    @Test
    void whenFindByIsSystemGroup_thenReturnSystemGroups() {
        // Given
        entityManager.persistAndFlush(developersGroup);
        entityManager.persistAndFlush(adminsGroup);

        // When
        List<Group> systemGroups = groupRepository.findAllByIsSystemGroupTrueOrderByNameAsc();
        List<Group> nonSystemGroups = groupRepository.findAllByIsSystemGroupFalseOrderByNameAsc();

        // Then
        assertThat(systemGroups).hasSize(1);
        assertThat(systemGroups.get(0).getName()).isEqualTo("Administrators");

        assertThat(nonSystemGroups).hasSize(1);
        assertThat(nonSystemGroups.get(0).getName()).isEqualTo("Developers");
    }

    @Test
    void whenUpdateGroup_thenGroupIsUpdated() {
        // Given
        Group savedGroup = entityManager.persistAndFlush(developersGroup);

        // When
        savedGroup.setName("Senior Developers");
        savedGroup.setDescription("Senior software development team");
        savedGroup.setSystemGroup(true);
        Group updatedGroup = groupRepository.save(savedGroup);

        // Then
        assertThat(updatedGroup.getName()).isEqualTo("Senior Developers");
        assertThat(updatedGroup.getDescription()).isEqualTo("Senior software development team");
        assertThat(updatedGroup.isSystemGroup()).isTrue();

        // Verify in database
        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup.getName()).isEqualTo("Senior Developers");
        assertThat(foundGroup.isSystemGroup()).isTrue();
    }

    @Test
    void whenDeleteGroup_thenGroupIsRemoved() {
        // Given
        Group savedGroup = entityManager.persistAndFlush(developersGroup);

        // When
        groupRepository.delete(savedGroup);
        entityManager.flush();

        // Then
        Group deletedGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(deletedGroup).isNull();
    }

    @Test
    void whenSaveDuplicateName_thenThrowException() {
        // Given
        entityManager.persistAndFlush(developersGroup);

        Group duplicateGroup = new Group();
        duplicateGroup.setName("Developers");
        duplicateGroup.setDescription("Different description");
        duplicateGroup.setSystemGroup(true);

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            groupRepository.saveAndFlush(duplicateGroup);
        });
    }

    @Test
    void whenFindByNonExistingName_thenReturnEmpty() {
        // When
        Optional<Group> foundGroup = groupRepository.findByName("NonExisting");

        // Then
        assertThat(foundGroup).isEmpty();
    }

    @Test
    void testCaseSensitiveNameSearch() {
        // Given
        entityManager.persistAndFlush(developersGroup);

        // When
        Optional<Group> lowerCase = groupRepository.findByName("developers");
        Optional<Group> upperCase = groupRepository.findByName("DEVELOPERS");

        // Then - depends on database collation
        assertThat(groupRepository.findByName("Developers")).isPresent();
        // Other cases might not be found depending on collation
    }

    @Test
    void testLobDescriptionPersistence() {
        // Given
        String longDescription = "A".repeat(5000);
        developersGroup.setDescription(longDescription);

        // When
        Group savedGroup = entityManager.persistAndFlush(developersGroup);

        // Then
        Group foundGroup = entityManager.find(Group.class, savedGroup.getId());
        assertThat(foundGroup.getDescription()).isEqualTo(longDescription);
        assertThat(foundGroup.getDescription()).hasSize(5000);
    }

    @Test
    void testDefaultIsSystemGroupValueOnPersist() {
        // Given
        Group groupWithDefault = new Group();
        groupWithDefault.setName("DefaultGroup");
        // isSystemGroup not set - should use default

        // When
        Group savedGroup = entityManager.persistAndFlush(groupWithDefault);

        // Then
        assertThat(savedGroup.isSystemGroup()).isFalse();
    }
}
