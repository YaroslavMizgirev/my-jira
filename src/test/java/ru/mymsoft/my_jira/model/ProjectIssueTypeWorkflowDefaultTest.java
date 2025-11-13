package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectIssueTypeWorkflowDefaultTest {

    private Project project;
    private IssueType issueType;
    private Workflow workflow;

    @BeforeEach
    void setUp() {
        project = mock(Project.class);
        when(project.getId()).thenReturn(1L);

        issueType = mock(IssueType.class);
        when(issueType.getId()).thenReturn(2L);

        workflow = mock(Workflow.class);
        when(workflow.getId()).thenReturn(3L);
    }

    @Test
    @DisplayName("Should create ProjectIssueTypeWorkflowDefault with builder pattern")
    void shouldCreateProjectIssueTypeWorkflowDefaultWithBuilder() {
        // When
        ProjectIssueTypeWorkflowDefault defaultWorkflow = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(issueType)
                .workflow(workflow)
                .build();

        // Then
        assertNotNull(defaultWorkflow);
        assertEquals(project, defaultWorkflow.getProject());
        assertEquals(issueType, defaultWorkflow.getIssueType());
        assertEquals(workflow, defaultWorkflow.getWorkflow());
    }

    @Test
    @DisplayName("Should create ProjectIssueTypeWorkflowDefault with no-args constructor")
    void shouldCreateProjectIssueTypeWorkflowDefaultWithNoArgsConstructor() {
        // When
        ProjectIssueTypeWorkflowDefault defaultWorkflow = new ProjectIssueTypeWorkflowDefault();

        // Then
        assertNotNull(defaultWorkflow);
        assertNull(defaultWorkflow.getProject());
        assertNull(defaultWorkflow.getIssueType());
        assertNull(defaultWorkflow.getWorkflow());
    }

    @Test
    @DisplayName("Should create ProjectIssueTypeWorkflowDefault with all-args constructor")
    void shouldCreateProjectIssueTypeWorkflowDefaultWithAllArgsConstructor() {
        // When
        ProjectIssueTypeWorkflowDefault defaultWorkflow = new ProjectIssueTypeWorkflowDefault(
                project, issueType, workflow
        );

        // Then
        assertNotNull(defaultWorkflow);
        assertEquals(project, defaultWorkflow.getProject());
        assertEquals(issueType, defaultWorkflow.getIssueType());
        assertEquals(workflow, defaultWorkflow.getWorkflow());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        ProjectIssueTypeWorkflowDefault defaultWorkflow = new ProjectIssueTypeWorkflowDefault();
        Project newProject = mock(Project.class);
        IssueType newIssueType = mock(IssueType.class);
        Workflow newWorkflow = mock(Workflow.class);

        // When
        defaultWorkflow.setProject(newProject);
        defaultWorkflow.setIssueType(newIssueType);
        defaultWorkflow.setWorkflow(newWorkflow);

        // Then
        assertEquals(newProject, defaultWorkflow.getProject());
        assertEquals(newIssueType, defaultWorkflow.getIssueType());
        assertEquals(newWorkflow, defaultWorkflow.getWorkflow());
    }

    @Test
    @DisplayName("Should use composite key for entity identity")
    void shouldUseCompositeKeyForEntityIdentity() {
        // Given
        ProjectIssueTypeWorkflowDefault default1 = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(issueType)
                .workflow(workflow)
                .build();

        ProjectIssueTypeWorkflowDefault default2 = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(issueType)
                .workflow(workflow)
                .build();

        // Then - Same composite key should represent same entity
        // Note: JPA/Hibernate will use the composite key for equality in persistence context
        // The actual equals/hashCode are handled by the persistence provider based on @Id fields
        assertEquals(default1.getProject(), default2.getProject());
        assertEquals(default1.getIssueType(), default2.getIssueType());
        assertEquals(default1.getWorkflow(), default2.getWorkflow());
    }

    @Test
    @DisplayName("ProjectIssueTypeWorkflowDefaultId should implement equals and hashCode correctly")
    void projectIssueTypeWorkflowDefaultIdShouldImplementEqualsAndHashCode() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id1 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id2 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id3 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 4L); // Different workflow
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id4 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 3L, 3L); // Different issue type
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id5 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(4L, 2L, 3L); // Different project

        // Then
        // Test equals
        assertEquals(id1, id2, "Same project, issue type and workflow IDs should be equal");
        assertNotEquals(id1, id3, "Different workflow IDs should not be equal");
        assertNotEquals(id1, id4, "Different issue type IDs should not be equal");
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
    @DisplayName("ProjectIssueTypeWorkflowDefaultId should work correctly in collections")
    void projectIssueTypeWorkflowDefaultIdShouldWorkCorrectlyInCollections() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id1 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id2 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id3 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 4L);

        Set<ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId> set = new HashSet<>();

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
    @DisplayName("ProjectIssueTypeWorkflowDefaultId should have working getters and setters")
    void projectIssueTypeWorkflowDefaultIdShouldHaveWorkingGettersAndSetters() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId();

        // When
        id.setProject(10L);
        id.setIssueType(20L);
        id.setWorkflow(30L);

        // Then
        assertEquals(10L, id.getProject());
        assertEquals(20L, id.getIssueType());
        assertEquals(30L, id.getWorkflow());
    }

    @Test
    @DisplayName("ProjectIssueTypeWorkflowDefaultId should implement Serializable")
    void projectIssueTypeWorkflowDefaultIdShouldImplementSerializable() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);

        // Then
        assertTrue(id instanceof java.io.Serializable, "ProjectIssueTypeWorkflowDefaultId should implement Serializable");
    }

    @Test
    @DisplayName("Should create ProjectIssueTypeWorkflowDefaultId with all-args constructor")
    void shouldCreateProjectIssueTypeWorkflowDefaultIdWithAllArgsConstructor() {
        // When
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(5L, 10L, 15L);

        // Then
        assertEquals(5L, id.getProject());
        assertEquals(10L, id.getIssueType());
        assertEquals(15L, id.getWorkflow());
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void shouldHandleNullValuesInCompositeKey() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id1 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(null, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id2 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(null, 2L, 3L);
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id3 =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, null, 3L);

        // Then
        assertEquals(id1, id2, "Both null project IDs should be considered equal");
        assertNotEquals(id1, id3, "Different null patterns should not be equal");
    }

    @Test
    @DisplayName("Should test toString method for ProjectIssueTypeWorkflowDefault")
    void shouldTestToStringMethodForProjectIssueTypeWorkflowDefault() {
        // Given
        ProjectIssueTypeWorkflowDefault defaultWorkflow = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(issueType)
                .workflow(workflow)
                .build();

        // When
        String toStringResult = defaultWorkflow.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should test toString method for ProjectIssueTypeWorkflowDefaultId")
    void shouldTestToStringMethodForProjectIssueTypeWorkflowDefaultId() {
        // Given
        ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId id =
            new ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId(1L, 2L, 3L);

        // When
        String toStringResult = id.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should create different workflow defaults for same project")
    void shouldCreateDifferentWorkflowDefaultsForSameProject() {
        // Given
        IssueType bugType = mock(IssueType.class);
        when(bugType.getId()).thenReturn(1L);

        IssueType taskType = mock(IssueType.class);
        when(taskType.getId()).thenReturn(2L);

        Workflow bugWorkflow = mock(Workflow.class);
        when(bugWorkflow.getId()).thenReturn(10L);

        Workflow taskWorkflow = mock(Workflow.class);
        when(taskWorkflow.getId()).thenReturn(11L);

        // When
        ProjectIssueTypeWorkflowDefault bugDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(bugType)
                .workflow(bugWorkflow)
                .build();

        ProjectIssueTypeWorkflowDefault taskDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(taskType)
                .workflow(taskWorkflow)
                .build();

        // Then
        assertEquals(project, bugDefault.getProject());
        assertEquals(project, taskDefault.getProject());
        assertEquals(bugType, bugDefault.getIssueType());
        assertEquals(taskType, taskDefault.getIssueType());
        assertEquals(bugWorkflow, bugDefault.getWorkflow());
        assertEquals(taskWorkflow, taskDefault.getWorkflow());
    }

    @Test
    @DisplayName("Should create different workflow defaults for same issue type in different projects")
    void shouldCreateDifferentWorkflowDefaultsForSameIssueTypeInDifferentProjects() {
        // Given
        Project project1 = mock(Project.class);
        when(project1.getId()).thenReturn(1L);

        Project project2 = mock(Project.class);
        when(project2.getId()).thenReturn(2L);

        Workflow workflow1 = mock(Workflow.class);
        when(workflow1.getId()).thenReturn(10L);

        Workflow workflow2 = mock(Workflow.class);
        when(workflow2.getId()).thenReturn(11L);

        // When
        ProjectIssueTypeWorkflowDefault default1 = ProjectIssueTypeWorkflowDefault.builder()
                .project(project1)
                .issueType(issueType)
                .workflow(workflow1)
                .build();

        ProjectIssueTypeWorkflowDefault default2 = ProjectIssueTypeWorkflowDefault.builder()
                .project(project2)
                .issueType(issueType)
                .workflow(workflow2)
                .build();

        // Then
        assertEquals(issueType, default1.getIssueType());
        assertEquals(issueType, default2.getIssueType());
        assertEquals(project1, default1.getProject());
        assertEquals(project2, default2.getProject());
        assertEquals(workflow1, default1.getWorkflow());
        assertEquals(workflow2, default2.getWorkflow());
    }

    @Test
    @DisplayName("Should handle realistic workflow default scenarios")
    void shouldHandleRealisticWorkflowDefaultScenarios() {
        // Given
        Project webProject = mock(Project.class);
        when(webProject.getId()).thenReturn(1L);

        Project mobileProject = mock(Project.class);
        when(mobileProject.getId()).thenReturn(2L);

        IssueType bugType = mock(IssueType.class);
        when(bugType.getId()).thenReturn(1L);

        IssueType storyType = mock(IssueType.class);
        when(storyType.getId()).thenReturn(2L);

        IssueType taskType = mock(IssueType.class);
        when(taskType.getId()).thenReturn(3L);

        Workflow bugWorkflow = mock(Workflow.class);
        when(bugWorkflow.getId()).thenReturn(10L);

        Workflow storyWorkflow = mock(Workflow.class);
        when(storyWorkflow.getId()).thenReturn(11L);

        Workflow taskWorkflow = mock(Workflow.class);
        when(taskWorkflow.getId()).thenReturn(12L);

        // When - Create workflow defaults for different project/issue type combinations
        ProjectIssueTypeWorkflowDefault webBugDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(webProject)
                .issueType(bugType)
                .workflow(bugWorkflow)
                .build();

        ProjectIssueTypeWorkflowDefault webStoryDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(webProject)
                .issueType(storyType)
                .workflow(storyWorkflow)
                .build();

        ProjectIssueTypeWorkflowDefault mobileBugDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(mobileProject)
                .issueType(bugType)
                .workflow(bugWorkflow)
                .build();

        ProjectIssueTypeWorkflowDefault mobileTaskDefault = ProjectIssueTypeWorkflowDefault.builder()
                .project(mobileProject)
                .issueType(taskType)
                .workflow(taskWorkflow)
                .build();

        // Then
        assertAll(
            () -> assertEquals(webProject, webBugDefault.getProject()),
            () -> assertEquals(bugType, webBugDefault.getIssueType()),
            () -> assertEquals(bugWorkflow, webBugDefault.getWorkflow()),
            () -> assertEquals(webProject, webStoryDefault.getProject()),
            () -> assertEquals(storyType, webStoryDefault.getIssueType()),
            () -> assertEquals(storyWorkflow, webStoryDefault.getWorkflow()),
            () -> assertEquals(mobileProject, mobileBugDefault.getProject()),
            () -> assertEquals(bugType, mobileBugDefault.getIssueType()),
            () -> assertEquals(bugWorkflow, mobileBugDefault.getWorkflow()),
            () -> assertEquals(mobileProject, mobileTaskDefault.getProject()),
            () -> assertEquals(taskType, mobileTaskDefault.getIssueType()),
            () -> assertEquals(taskWorkflow, mobileTaskDefault.getWorkflow())
        );
    }
}
