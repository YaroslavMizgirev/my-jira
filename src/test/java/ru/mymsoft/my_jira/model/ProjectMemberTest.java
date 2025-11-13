package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectMemberTest {

    private Project project;
    private Group group;
    private Role role;

    @BeforeEach
    void setUp() {
        project = mock(Project.class);
        when(project.getId()).thenReturn(1L);

        group = mock(Group.class);
        when(group.getId()).thenReturn(2L);

        role = mock(Role.class);
        when(role.getId()).thenReturn(3L);
    }

    @Test
    @DisplayName("Should create ProjectMember with builder pattern")
    void shouldCreateProjectMemberWithBuilder() {
        // When
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(role)
                .build();

        // Then
        assertNotNull(projectMember);
        assertEquals(project, projectMember.getProject());
        assertEquals(group, projectMember.getGroup());
        assertEquals(role, projectMember.getRole());
    }

    @Test
    @DisplayName("Should create ProjectMember with no-args constructor")
    void shouldCreateProjectMemberWithNoArgsConstructor() {
        // When
        ProjectMember projectMember = new ProjectMember();

        // Then
        assertNotNull(projectMember);
        assertNull(projectMember.getProject());
        assertNull(projectMember.getGroup());
        assertNull(projectMember.getRole());
    }

    @Test
    @DisplayName("Should create ProjectMember with all-args constructor")
    void shouldCreateProjectMemberWithAllArgsConstructor() {
        // When
        ProjectMember projectMember = new ProjectMember(
                project, group, role
        );

        // Then
        assertNotNull(projectMember);
        assertEquals(project, projectMember.getProject());
        assertEquals(group, projectMember.getGroup());
        assertEquals(role, projectMember.getRole());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        ProjectMember projectMember = new ProjectMember();
        Project newProject = mock(Project.class);
        Group newGroup = mock(Group.class);
        Role newRole = mock(Role.class);

        // When
        projectMember.setProject(newProject);
        projectMember.setGroup(newGroup);
        projectMember.setRole(newRole);

        // Then
        assertEquals(newProject, projectMember.getProject());
        assertEquals(newGroup, projectMember.getGroup());
        assertEquals(newRole, projectMember.getRole());
    }

    @Test
    @DisplayName("Should use composite key for entity identity")
    void shouldUseCompositeKeyForEntityIdentity() {
        // Given
        ProjectMember member1 = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(role)
                .build();

        ProjectMember member2 = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(role)
                .build();

        // Then - Same composite key should represent same entity
        assertEquals(member1.getProject(), member2.getProject());
        assertEquals(member1.getGroup(), member2.getGroup());
        assertEquals(member1.getRole(), member2.getRole());
    }

    @Test
    @DisplayName("ProjectMemberId should implement equals and hashCode correctly")
    void projectMemberIdShouldImplementEqualsAndHashCode() {
        // Given
        ProjectMember.ProjectMemberId id1 =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);
        ProjectMember.ProjectMemberId id2 =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);
        ProjectMember.ProjectMemberId id3 =
            new ProjectMember.ProjectMemberId(1L, 2L, 4L); // Different role
        ProjectMember.ProjectMemberId id4 =
            new ProjectMember.ProjectMemberId(1L, 3L, 3L); // Different group
        ProjectMember.ProjectMemberId id5 =
            new ProjectMember.ProjectMemberId(4L, 2L, 3L); // Different project

        // Then
        // Test equals
        assertEquals(id1, id2, "Same project, group and role IDs should be equal");
        assertNotEquals(id1, id3, "Different role IDs should not be equal");
        assertNotEquals(id1, id4, "Different group IDs should not be equal");
        assertNotEquals(id1, id5, "Different project IDs should not be equal");
        assertNotEquals(id1, null, "Should not equal null");
        assertNotEquals(id1, "some string", "Should not equal different type");

        // Test hashCode
        assertEquals(id1.hashCode(), id2.hashCode(), "Equal objects should have same hash code");
        assertNotEquals(id1.hashCode(), id3.hashCode(), "Different objects should have different hash codes");

        // Test consistency
        assertEquals(id1.hashCode(), id1.hashCode(), "Hash code should be consistent");
    }

    @Test
    @DisplayName("ProjectMemberId should work correctly in collections")
    void projectMemberIdShouldWorkCorrectlyInCollections() {
        // Given
        ProjectMember.ProjectMemberId id1 =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);
        ProjectMember.ProjectMemberId id2 =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);
        ProjectMember.ProjectMemberId id3 =
            new ProjectMember.ProjectMemberId(1L, 2L, 4L);

        Set<ProjectMember.ProjectMemberId> set = new HashSet<>();

        // When
        set.add(id1);
        set.add(id2); // duplicate
        set.add(id3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates");
        assertTrue(set.contains(id1));
        assertTrue(set.contains(id2));
        assertTrue(set.contains(id3));
    }

    @Test
    @DisplayName("ProjectMemberId should have working getters and setters")
    void projectMemberIdShouldHaveWorkingGettersAndSetters() {
        // Given
        ProjectMember.ProjectMemberId id =
            new ProjectMember.ProjectMemberId();

        // When
        id.setProject(10L);
        id.setGroup(20L);
        id.setRole(30L);

        // Then
        assertEquals(10L, id.getProject());
        assertEquals(20L, id.getGroup());
        assertEquals(30L, id.getRole());
    }

    @Test
    @DisplayName("ProjectMemberId should implement Serializable")
    void projectMemberIdShouldImplementSerializable() {
        // Given
        ProjectMember.ProjectMemberId id =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);

        // Then
        assertTrue(id instanceof java.io.Serializable, "ProjectMemberId should implement Serializable");
    }

    @Test
    @DisplayName("Should create ProjectMemberId with all-args constructor")
    void shouldCreateProjectMemberIdWithAllArgsConstructor() {
        // When
        ProjectMember.ProjectMemberId id =
            new ProjectMember.ProjectMemberId(5L, 10L, 15L);

        // Then
        assertEquals(5L, id.getProject());
        assertEquals(10L, id.getGroup());
        assertEquals(15L, id.getRole());
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void shouldHandleNullValuesInCompositeKey() {
        // Given
        ProjectMember.ProjectMemberId id1 =
            new ProjectMember.ProjectMemberId(null, 2L, 3L);
        ProjectMember.ProjectMemberId id2 =
            new ProjectMember.ProjectMemberId(null, 2L, 3L);
        ProjectMember.ProjectMemberId id3 =
            new ProjectMember.ProjectMemberId(1L, null, 3L);

        // Then
        assertEquals(id1, id2, "Both null project IDs should be considered equal");
        assertNotEquals(id1, id3, "Different null patterns should not be equal");
    }

    @Test
    @DisplayName("Should test toString method for ProjectMember")
    void shouldTestToStringMethodForProjectMember() {
        // Given
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(role)
                .build();

        // When
        String toStringResult = projectMember.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should test toString method for ProjectMemberId")
    void shouldTestToStringMethodForProjectMemberId() {
        // Given
        ProjectMember.ProjectMemberId id =
            new ProjectMember.ProjectMemberId(1L, 2L, 3L);

        // When
        String toStringResult = id.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should create different group memberships for same project")
    void shouldCreateDifferentGroupMembershipsForSameProject() {
        // Given
        Group developers = mock(Group.class);
        when(developers.getId()).thenReturn(1L);

        Group qa = mock(Group.class);
        when(qa.getId()).thenReturn(2L);

        Role developerRole = mock(Role.class);
        when(developerRole.getId()).thenReturn(10L);

        Role testerRole = mock(Role.class);
        when(testerRole.getId()).thenReturn(11L);

        // When
        ProjectMember devMember = ProjectMember.builder()
                .project(project)
                .group(developers)
                .role(developerRole)
                .build();

        ProjectMember qaMember = ProjectMember.builder()
                .project(project)
                .group(qa)
                .role(testerRole)
                .build();

        // Then
        assertEquals(project, devMember.getProject());
        assertEquals(project, qaMember.getProject());
        assertEquals(developers, devMember.getGroup());
        assertEquals(qa, qaMember.getGroup());
        assertEquals(developerRole, devMember.getRole());
        assertEquals(testerRole, qaMember.getRole());
    }

    @Test
    @DisplayName("Should create different role assignments for same group in same project")
    void shouldCreateDifferentRoleAssignmentsForSameGroupInSameProject() {
        // Given
        Role adminRole = mock(Role.class);
        when(adminRole.getId()).thenReturn(1L);

        Role userRole = mock(Role.class);
        when(userRole.getId()).thenReturn(2L);

        // When
        ProjectMember adminMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(adminRole)
                .build();

        ProjectMember userMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(userRole)
                .build();

        // Then
        assertEquals(project, adminMember.getProject());
        assertEquals(project, userMember.getProject());
        assertEquals(group, adminMember.getGroup());
        assertEquals(group, userMember.getGroup());
        assertEquals(adminRole, adminMember.getRole());
        assertEquals(userRole, userMember.getRole());
    }

    @Test
    @DisplayName("Should create same group with same role in different projects")
    void shouldCreateSameGroupWithSameRoleInDifferentProjects() {
        // Given
        Project project1 = mock(Project.class);
        when(project1.getId()).thenReturn(1L);

        Project project2 = mock(Project.class);
        when(project2.getId()).thenReturn(2L);

        // When
        ProjectMember member1 = ProjectMember.builder()
                .project(project1)
                .group(group)
                .role(role)
                .build();

        ProjectMember member2 = ProjectMember.builder()
                .project(project2)
                .group(group)
                .role(role)
                .build();

        // Then
        assertEquals(group, member1.getGroup());
        assertEquals(group, member2.getGroup());
        assertEquals(role, member1.getRole());
        assertEquals(role, member2.getRole());
        assertEquals(project1, member1.getProject());
        assertEquals(project2, member2.getProject());
    }

    @Test
    @DisplayName("Should handle realistic project member scenarios")
    void shouldHandleRealisticProjectMemberScenarios() {
        // Given
        Project webProject = mock(Project.class);
        when(webProject.getId()).thenReturn(1L);

        Project mobileProject = mock(Project.class);
        when(mobileProject.getId()).thenReturn(2L);

        Group devGroup = mock(Group.class);
        when(devGroup.getId()).thenReturn(1L);

        Group qaGroup = mock(Group.class);
        when(qaGroup.getId()).thenReturn(2L);

        Group pmGroup = mock(Group.class);
        when(pmGroup.getId()).thenReturn(3L);

        Role adminRole = mock(Role.class);
        when(adminRole.getId()).thenReturn(1L);

        Role developerRole = mock(Role.class);
        when(developerRole.getId()).thenReturn(2L);

        Role testerRole = mock(Role.class);
        when(testerRole.getId()).thenReturn(3L);

        Role viewerRole = mock(Role.class);
        when(viewerRole.getId()).thenReturn(4L);

        // When - Create realistic project member assignments
        ProjectMember webDevAdmin = ProjectMember.builder()
                .project(webProject)
                .group(devGroup)
                .role(adminRole)
                .build();

        ProjectMember webDevDeveloper = ProjectMember.builder()
                .project(webProject)
                .group(devGroup)
                .role(developerRole)
                .build();

        ProjectMember webQATester = ProjectMember.builder()
                .project(webProject)
                .group(qaGroup)
                .role(testerRole)
                .build();

        ProjectMember mobileDevDeveloper = ProjectMember.builder()
                .project(mobileProject)
                .group(devGroup)
                .role(developerRole)
                .build();

        ProjectMember mobilePMViewer = ProjectMember.builder()
                .project(mobileProject)
                .group(pmGroup)
                .role(viewerRole)
                .build();

        // Then
        assertAll(
            () -> assertEquals(webProject, webDevAdmin.getProject()),
            () -> assertEquals(devGroup, webDevAdmin.getGroup()),
            () -> assertEquals(adminRole, webDevAdmin.getRole()),

            () -> assertEquals(webProject, webDevDeveloper.getProject()),
            () -> assertEquals(devGroup, webDevDeveloper.getGroup()),
            () -> assertEquals(developerRole, webDevDeveloper.getRole()),

            () -> assertEquals(webProject, webQATester.getProject()),
            () -> assertEquals(qaGroup, webQATester.getGroup()),
            () -> assertEquals(testerRole, webQATester.getRole()),

            () -> assertEquals(mobileProject, mobileDevDeveloper.getProject()),
            () -> assertEquals(devGroup, mobileDevDeveloper.getGroup()),
            () -> assertEquals(developerRole, mobileDevDeveloper.getRole()),

            () -> assertEquals(mobileProject, mobilePMViewer.getProject()),
            () -> assertEquals(pmGroup, mobilePMViewer.getGroup()),
            () -> assertEquals(viewerRole, mobilePMViewer.getRole())
        );
    }

    @Test
    @DisplayName("Should verify composite key uniqueness constraints")
    void shouldVerifyCompositeKeyUniquenessConstraints() {
        // Given
        ProjectMember.ProjectMemberId id1 = new ProjectMember.ProjectMemberId(1L, 2L, 3L);
        ProjectMember.ProjectMemberId id2 = new ProjectMember.ProjectMemberId(1L, 2L, 3L); // Same
        ProjectMember.ProjectMemberId id3 = new ProjectMember.ProjectMemberId(1L, 2L, 4L); // Different role
        ProjectMember.ProjectMemberId id4 = new ProjectMember.ProjectMemberId(1L, 3L, 3L); // Different group
        ProjectMember.ProjectMemberId id5 = new ProjectMember.ProjectMemberId(2L, 2L, 3L); // Different project

        // Then
        assertEquals(id1, id2); // Same combination
        assertNotEquals(id1, id3); // Different role
        assertNotEquals(id1, id4); // Different group
        assertNotEquals(id1, id5); // Different project
    }

    @Test
    @DisplayName("Should handle multiple roles for same group in same project")
    void shouldHandleMultipleRolesForSameGroupInSameProject() {
        // Given
        Role leadRole = mock(Role.class);
        when(leadRole.getId()).thenReturn(1L);

        Role memberRole = mock(Role.class);
        when(memberRole.getId()).thenReturn(2L);

        // When
        ProjectMember leadMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(leadRole)
                .build();

        ProjectMember regularMember = ProjectMember.builder()
                .project(project)
                .group(group)
                .role(memberRole)
                .build();

        // Then - Both should be valid different memberships
        assertEquals(project, leadMember.getProject());
        assertEquals(project, regularMember.getProject());
        assertEquals(group, leadMember.getGroup());
        assertEquals(group, regularMember.getGroup());
        assertEquals(leadRole, leadMember.getRole());
        assertEquals(memberRole, regularMember.getRole());

        // They should have different composite keys due to different roles
        ProjectMember.ProjectMemberId leadId = new ProjectMember.ProjectMemberId(
            project.getId(), group.getId(), leadRole.getId()
        );
        ProjectMember.ProjectMemberId regularId = new ProjectMember.ProjectMemberId(
            project.getId(), group.getId(), memberRole.getId()
        );
        assertNotEquals(leadId, regularId);
    }
}
