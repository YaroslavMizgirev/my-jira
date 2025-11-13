package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectTest {

    private User projectLead;
    private Workflow defaultWorkflow;

    @BeforeEach
    void setUp() {
        projectLead = mock(User.class);
        defaultWorkflow = mock(Workflow.class);
    }

    @Test
    @DisplayName("Should create Project with builder pattern")
    void shouldCreateProjectWithBuilder() {
        // Given
        Instant now = Instant.now();
        String projectDescription = "This is a sample project for testing purposes";

        // When
        Project project = Project.builder()
                .id(1L)
                .name("My Test Project")
                .key("MTP")
                .description(projectDescription)
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .createdAt(now)
                .updatedAt(now.plus(1, ChronoUnit.HOURS))
                .build();

        // Then
        assertNotNull(project);
        assertEquals(1L, project.getId());
        assertEquals("My Test Project", project.getName());
        assertEquals("MTP", project.getKey());
        assertEquals(projectDescription, project.getDescription());
        assertEquals(projectLead, project.getLead());
        assertEquals(defaultWorkflow, project.getDefaultWorkflow());
        assertEquals(now, project.getCreatedAt());
        assertEquals(now.plus(1, ChronoUnit.HOURS), project.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create Project with no-args constructor")
    void shouldCreateProjectWithNoArgsConstructor() {
        // When
        Project project = new Project();

        // Then
        assertNotNull(project);
        assertNull(project.getId());
        assertNull(project.getName());
        assertNull(project.getKey());
        assertNull(project.getDescription());
        assertNull(project.getLead());
        assertNull(project.getDefaultWorkflow());
        assertNull(project.getCreatedAt());
        assertNull(project.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create Project with all-args constructor")
    void shouldCreateProjectWithAllArgsConstructor() {
        // Given
        Instant createdAt = Instant.parse("2024-01-15T10:00:00Z");
        Instant updatedAt = Instant.parse("2024-01-15T11:00:00Z");

        // When
        Project project = new Project(
                1L,
                "Enterprise Platform",
                "ENT",
                "Large scale enterprise project",
                projectLead,
                defaultWorkflow,
                createdAt,
                updatedAt
        );

        // Then
        assertNotNull(project);
        assertEquals(1L, project.getId());
        assertEquals("Enterprise Platform", project.getName());
        assertEquals("ENT", project.getKey());
        assertEquals("Large scale enterprise project", project.getDescription());
        assertEquals(projectLead, project.getLead());
        assertEquals(defaultWorkflow, project.getDefaultWorkflow());
        assertEquals(createdAt, project.getCreatedAt());
        assertEquals(updatedAt, project.getUpdatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        Project project = new Project();
        Instant now = Instant.now();
        Instant later = now.plus(1, ChronoUnit.DAYS);
        User newLead = mock(User.class);
        Workflow newWorkflow = mock(Workflow.class);

        // When
        project.setId(5L);
        project.setName("Updated Project Name");
        project.setKey("UPN");
        project.setDescription("Updated project description");
        project.setLead(newLead);
        project.setDefaultWorkflow(newWorkflow);
        project.setCreatedAt(now);
        project.setUpdatedAt(later);

        // Then
        assertEquals(5L, project.getId());
        assertEquals("Updated Project Name", project.getName());
        assertEquals("UPN", project.getKey());
        assertEquals("Updated project description", project.getDescription());
        assertEquals(newLead, project.getLead());
        assertEquals(newWorkflow, project.getDefaultWorkflow());
        assertEquals(now, project.getCreatedAt());
        assertEquals(later, project.getUpdatedAt());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id, name and key")
    void shouldImplementEqualsAndHashCodeBasedOnIdNameAndKey() {
        // Given
        Project project1 = Project.builder()
                .id(1L)
                .name("Project Alpha")
                .key("PAL")
                .description("Description 1")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        Project project2 = Project.builder()
                .id(1L)
                .name("Project Alpha")
                .key("PAL")
                .description("Different description") // Different description
                .lead(mock(User.class)) // Different lead
                .defaultWorkflow(null) // Different workflow
                .build();

        Project project3 = Project.builder()
                .id(2L) // Different ID
                .name("Project Alpha")
                .key("PAL")
                .description("Description 1")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        Project project4 = Project.builder()
                .id(1L)
                .name("Project Beta") // Different name
                .key("PAL")
                .description("Description 1")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        Project project5 = Project.builder()
                .id(1L)
                .name("Project Alpha")
                .key("PBE") // Different key
                .description("Description 1")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        Project project6 = new Project(); // null fields

        // Then
        // Same ID, name and key should be equal regardless of other fields
        assertEquals(project1, project2);
        assertEquals(project1.hashCode(), project2.hashCode());

        // Different ID should not be equal
        assertNotEquals(project1, project3);

        // Different name should not be equal
        assertNotEquals(project1, project4);

        // Different key should not be equal
        assertNotEquals(project1, project5);

        // Null comparison
        assertNotEquals(project1, null);
        assertNotEquals(project1, "some string");
        assertNotEquals(project1, project6);

        // Reflexivity
        assertEquals(project1, project1);

        // Symmetry
        assertEquals(project1, project2);
        assertEquals(project2, project1);

        // Consistency
        assertEquals(project1.hashCode(), project1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        Project project1 = Project.builder()
                .id(1L)
                .name("Project One")
                .key("P1")
                .lead(projectLead)
                .build();

        Project project2 = Project.builder()
                .id(1L)
                .name("Project One")
                .key("P1")
                .description("Different description") // Different description
                .lead(mock(User.class)) // Different lead
                .build(); // Equal to project1 (same id, name and key)

        Project project3 = Project.builder()
                .id(2L)
                .name("Project Two")
                .key("P2")
                .lead(projectLead)
                .build(); // Different

        Set<Project> set = new HashSet<>();

        // When
        set.add(project1);
        set.add(project2); // Duplicate (same id, name and key)
        set.add(project3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id, name and key");
        assertTrue(set.contains(project1));
        assertTrue(set.contains(project2));
        assertTrue(set.contains(project3));
    }

    @Test
    @DisplayName("Should handle null fields in equals and hashCode")
    void shouldHandleNullFieldsInEqualsAndHashCode() {
        // Given
        Project project1 = new Project();
        project1.setName("Test Project");

        Project project2 = new Project();
        project2.setName("Test Project");

        Project project3 = new Project();
        project3.setKey("TST"); // Only key set

        // Then
        assertEquals(project1, project2);
        assertNotEquals(project1, project3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        Project project = new Project();

        // When
        project.setName(name);

        // Then
        assertEquals(name, project.getName());
    }

    @Test
    @DisplayName("Should handle LOB field for description")
    void shouldHandleLOBFieldForDescription() {
        // Given - Large description that would typically be stored as LOB
        String longDescription = "This is a very detailed project description that explains " +
                "the project goals, objectives, scope, and deliverables. It includes information " +
                "about the target audience, technical requirements, and success criteria. ".repeat(20);

        // When
        Project project = Project.builder()
                .id(1L)
                .name("Large Scale Project")
                .key("LSP")
                .description(longDescription)
                .lead(projectLead)
                .build();

        // Then
        assertEquals(longDescription, project.getDescription());
        assertTrue(project.getDescription().length() > 1000);
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void shouldHandleNullOptionalFields() {
        // When
        Project project = Project.builder()
                .id(1L)
                .name("Minimal Project")
                .key("MIN")
                .lead(projectLead)
                .description(null) // Optional field
                .defaultWorkflow(null) // Optional field
                .createdAt(null)
                .updatedAt(null)
                .build();

        // Then
        assertNotNull(project);
        assertEquals("Minimal Project", project.getName());
        assertEquals("MIN", project.getKey());
        assertEquals(projectLead, project.getLead());
        assertNull(project.getDescription());
        assertNull(project.getDefaultWorkflow());
        assertNull(project.getCreatedAt());
        assertNull(project.getUpdatedAt());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        Project project = Project.builder()
                .id(1L)
                .name("Development Platform")
                .key("DEV")
                .description("Software development project")
                .lead(projectLead)
                .build();

        // When
        String toStringResult = project.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("Project") ||
                  toStringResult.contains("Development Platform") ||
                  toStringResult.contains("DEV"));
    }

    @Test
    @DisplayName("Should create project with only required fields")
    void shouldCreateProjectWithOnlyRequiredFields() {
        // When
        Project project = Project.builder()
                .name("Essential Project")
                .key("ESS")
                .lead(projectLead)
                .build();

        // Then
        assertNotNull(project);
        assertEquals("Essential Project", project.getName());
        assertEquals("ESS", project.getKey());
        assertEquals(projectLead, project.getLead());
        assertNull(project.getId());
        assertNull(project.getDescription());
        assertNull(project.getDefaultWorkflow());
        assertNull(project.getCreatedAt());
        assertNull(project.getUpdatedAt());
    }

    @ParameterizedTest
    @CsvSource({
        "Website Redesign, WEB, 'Project to redesign company website', '/icons/web.svg'",
        'Mobile App, MOB, 'iOS and Android application development', '/icons/mobile.png'',
        "API Development, API, 'Backend API services', '/icons/api.svg'",
        "Data Analytics, DATA, 'Big data processing and analytics', '/icons/data.png'",
        "Infrastructure, INFRA, 'Cloud infrastructure setup', '/icons/cloud.svg'"
    })
    @DisplayName("Should create different project types with various properties")
    void shouldCreateDifferentProjectTypes(String name, String key, String description, String iconReference) {
        // When
        Project project = Project.builder()
                .name(name)
                .key(key)
                .description(description)
                .lead(projectLead)
                .build();

        // Then
        assertEquals(name, project.getName());
        assertEquals(key, project.getKey());
        assertEquals(description, project.getDescription());
        assertEquals(projectLead, project.getLead());
    }

    @Test
    @DisplayName("Should handle project key length constraints")
    void shouldHandleProjectKeyLengthConstraints() {
        // Given - key with maximum allowed length (50 chars)
        String maxLengthKey = "K".repeat(50);

        // When
        Project project = Project.builder()
                .name("Project with Long Key")
                .key(maxLengthKey)
                .lead(projectLead)
                .build();

        // Then
        assertEquals(maxLengthKey, project.getKey());
        assertEquals(50, project.getKey().length());
    }

    @Test
    @DisplayName("Should handle typical project key formats")
    void shouldHandleTypicalProjectKeyFormats() {
        // Given
        String[] typicalKeys = {"WEB", "MOB", "API", "DATA", "DEV", "QA", "OPS", "HR", "FIN", "MKT"};

        // When & Then
        for (String key : typicalKeys) {
            Project project = Project.builder()
                    .name("Project " + key)
                    .key(key)
                    .lead(projectLead)
                    .build();

            assertEquals(key, project.getKey());
            assertTrue(project.getKey().length() <= 50);
        }
    }

    @Test
    @DisplayName("Should verify timestamp ordering")
    void shouldVerifyTimestampOrdering() {
        // Given
        Instant createdAt = Instant.parse("2024-01-15T10:00:00Z");
        Instant updatedAt = Instant.parse("2024-01-15T14:30:00Z");

        // When
        Project project = Project.builder()
                .id(1L)
                .name("Timestamp Test Project")
                .key("TTP")
                .lead(projectLead)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Then
        assertTrue(project.getUpdatedAt().isAfter(project.getCreatedAt()));
    }

    @Test
    @DisplayName("Should handle project with workflow")
    void shouldHandleProjectWithWorkflow() {
        // Given
        Workflow customWorkflow = mock(Workflow.class);

        // When
        Project project = Project.builder()
                .id(1L)
                .name("Workflow Project")
                .key("WFP")
                .description("Project with custom workflow")
                .lead(projectLead)
                .defaultWorkflow(customWorkflow)
                .build();

        // Then
        assertNotNull(project.getDefaultWorkflow());
        assertEquals(customWorkflow, project.getDefaultWorkflow());
    }

    @Test
    @DisplayName("Should update project properties")
    void shouldUpdateProjectProperties() {
        // Given
        Project project = Project.builder()
                .id(1L)
                .name("Old Project Name")
                .key("OLD")
                .description("Old description")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        User newLead = mock(User.class);
        Workflow newWorkflow = mock(Workflow.class);

        // When
        project.setName("New Project Name");
        project.setKey("NEW");
        project.setDescription("New updated description");
        project.setLead(newLead);
        project.setDefaultWorkflow(newWorkflow);

        // Then
        assertEquals("New Project Name", project.getName());
        assertEquals("NEW", project.getKey());
        assertEquals("New updated description", project.getDescription());
        assertEquals(newLead, project.getLead());
        assertEquals(newWorkflow, project.getDefaultWorkflow());
    }

    @Test
    @DisplayName("Should handle project with same name but different keys")
    void shouldHandleProjectWithSameNameButDifferentKeys() {
        // Given
        Project project1 = Project.builder()
                .id(1L)
                .name("Development")
                .key("DEV")
                .lead(projectLead)
                .build();

        Project project2 = Project.builder()
                .id(2L)
                .name("Development") // Same name
                .key("DEV2") // Different key
                .lead(projectLead)
                .build();

        // Then
        assertNotEquals(project1, project2);
        assertEquals(project1.getName(), project2.getName());
        assertNotEquals(project1.getKey(), project2.getKey());
    }

    @Test
    @DisplayName("Should create realistic project examples")
    void shouldCreateRealisticProjectExamples() {
        // Given - Typical projects in a software company
        Project websiteProject = Project.builder()
                .id(1L)
                .name("Corporate Website")
                .key("WEB")
                .description("Development and maintenance of company website")
                .lead(projectLead)
                .build();

        Project mobileProject = Project.builder()
                .id(2L)
                .name("Mobile Application")
                .key("MOB")
                .description("Cross-platform mobile app for iOS and Android")
                .lead(projectLead)
                .build();

        Project apiProject = Project.builder()
                .id(3L)
                .name("Backend API")
                .key("API")
                .description("RESTful API services for frontend applications")
                .lead(projectLead)
                .defaultWorkflow(defaultWorkflow)
                .build();

        // Then
        assertAll(
                () -> assertEquals("Corporate Website", websiteProject.getName()),
                () -> assertEquals("WEB", websiteProject.getKey()),
                () -> assertEquals("Mobile Application", mobileProject.getName()),
                () -> assertEquals("MOB", mobileProject.getKey()),
                () -> assertEquals("Backend API", apiProject.getName()),
                () -> assertEquals("API", apiProject.getKey()),
                () -> assertEquals(defaultWorkflow, apiProject.getDefaultWorkflow())
        );
    }

    @Test
    @DisplayName("Should handle project with markdown in description")
    void shouldHandleProjectWithMarkdownInDescription() {
        // Given
        String markdownDescription = """
            # Project Overview
                
            ## Goals
            - Deliver high-quality software
            - Meet customer requirements
            - Follow agile methodology
                
            ## Technical Stack
            - **Backend**: Java, Spring Boot
            - **Frontend**: React, TypeScript
            - **Database**: PostgreSQL
            - **CI/CD**: Jenkins, Docker
                
            ## Team
            - Project Lead: John Doe
            - Developers: 5
            - QA Engineers: 2
            """;

        // When
        Project project = Project.builder()
                .id(1L)
                .name("Technical Platform")
                .key("TECH")
                .description(markdownDescription)
                .lead(projectLead)
                .build();

        // Then
        assertNotNull(project);
        assertTrue(project.getDescription().contains("# Project Overview"));
        assertTrue(project.getDescription().contains("**Backend**: Java, Spring Boot"));
        assertTrue(project.getDescription().contains("## Team"));
    }
}
