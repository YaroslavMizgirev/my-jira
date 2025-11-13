package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkflowTest {

    private Workflow workflow;
    private IssueStatus openStatus;
    private IssueStatus inProgressStatus;
    private IssueStatus doneStatus;
    private WorkflowTransition transition1;
    private WorkflowTransition transition2;

    @BeforeEach
    void setUp() {
        workflow = new Workflow();

        openStatus = mock(IssueStatus.class);
        inProgressStatus = mock(IssueStatus.class);
        doneStatus = mock(IssueStatus.class);

        transition1 = mock(WorkflowTransition.class);
        transition2 = mock(WorkflowTransition.class);
    }

    @Test
    @DisplayName("Should create Workflow with builder pattern")
    void shouldCreateWorkflowWithBuilder() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(inProgressStatus);

        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);

        // When
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .isDefault(true)
                .name("Bug Workflow")
                .statuses(statuses)
                .transitions(transitions)
                .build();

        // Then
        assertNotNull(workflow1);
        assertEquals(1L, workflow1.getId());
        assertTrue(workflow1.getIsDefault());
        assertEquals("Bug Workflow", workflow1.getName());
        assertEquals(2, workflow1.getStatuses().size());
        assertEquals(1, workflow1.getTransitions().size());
        assertTrue(workflow1.getStatuses().contains(openStatus));
        assertTrue(workflow1.getStatuses().contains(inProgressStatus));
        assertTrue(workflow1.getTransitions().contains(transition1));
    }

    @Test
    @DisplayName("Should create Workflow with no-args constructor")
    void shouldCreateWorkflowWithNoArgsConstructor() {
        // When
        Workflow workflow1 = new Workflow();

        // Then
        assertNotNull(workflow1);
        assertNull(workflow1.getId());
        assertFalse(workflow1.getIsDefault()); // Default value
        assertNull(workflow1.getName());
        assertNotNull(workflow1.getStatuses());
        assertTrue(workflow1.getStatuses().isEmpty());
        assertNotNull(workflow1.getTransitions());
        assertTrue(workflow1.getTransitions().isEmpty());
    }

    @Test
    @DisplayName("Should create Workflow with all-args constructor")
    void shouldCreateWorkflowWithAllArgsConstructor() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);

        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);
        transitions.add(transition2);

        // When
        Workflow workflow1 = new Workflow(
                1L,
                false,
                "Task Workflow",
                statuses,
                transitions
        );

        // Then
        assertNotNull(workflow1);
        assertEquals(1L, workflow1.getId());
        assertFalse(workflow1.getIsDefault());
        assertEquals("Task Workflow", workflow1.getName());
        assertEquals(1, workflow1.getStatuses().size());
        assertEquals(2, workflow1.getTransitions().size());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(doneStatus);

        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);

        // When
        workflow.setId(5L);
        workflow.setIsDefault(true);
        workflow.setName("Custom Workflow");
        workflow.setStatuses(statuses);
        workflow.setTransitions(transitions);

        // Then
        assertEquals(5L, workflow.getId());
        assertTrue(workflow.getIsDefault());
        assertEquals("Custom Workflow", workflow.getName());
        assertEquals(2, workflow.getStatuses().size());
        assertEquals(1, workflow.getTransitions().size());
        assertTrue(workflow.getStatuses().contains(openStatus));
        assertTrue(workflow.getStatuses().contains(doneStatus));
        assertTrue(workflow.getTransitions().contains(transition1));
    }

    @Test
    @DisplayName("Should have default value for isDefault field")
    void shouldHaveDefaultValueForIsDefault() {
        // When - using no-args constructor
        Workflow workflow1 = new Workflow();

        // Then
        assertFalse(workflow1.getIsDefault());

        // When - using builder without explicit isDefault
        Workflow workflow2 = Workflow.builder()
                .name("Test Workflow")
                .build();

        // Then
        assertFalse(workflow2.getIsDefault());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id and name")
    void shouldImplementEqualsAndHashCodeBasedOnIdAndName() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Workflow A")
                .isDefault(true)
                .build();

        Workflow workflow2 = Workflow.builder()
                .id(1L)
                .name("Workflow A")
                .isDefault(false) // Different default flag
                .build();

        Workflow workflow3 = Workflow.builder()
                .id(2L) // Different ID
                .name("Workflow A")
                .isDefault(true)
                .build();

        Workflow workflow4 = Workflow.builder()
                .id(1L)
                .name("Workflow B") // Different name
                .isDefault(true)
                .build();

        Workflow workflow5 = new Workflow(); // null fields

        // Then
        // Same ID and name should be equal regardless of other fields
        assertEquals(workflow1, workflow2);
        assertEquals(workflow1.hashCode(), workflow2.hashCode());

        // Different ID should not be equal
        assertNotEquals(workflow1, workflow3);

        // Different name should not be equal
        assertNotEquals(workflow1, workflow4);

        // Null comparison
        assertNotEquals(workflow1, null);
        assertNotEquals(workflow1, "some string");
        assertNotEquals(workflow1, workflow5);

        // Reflexivity
        assertEquals(workflow1, workflow1);

        // Symmetry
        assertEquals(workflow1, workflow2);
        assertEquals(workflow2, workflow1);

        // Consistency
        assertEquals(workflow1.hashCode(), workflow1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Workflow A")
                .isDefault(true)
                .build();

        Workflow workflow2 = Workflow.builder()
                .id(1L)
                .name("Workflow A")
                .isDefault(false) // Different default
                .build(); // Equal to workflow1 (same id and name)

        Workflow workflow3 = Workflow.builder()
                .id(2L)
                .name("Workflow B")
                .isDefault(false)
                .build(); // Different

        Set<Workflow> set = new HashSet<>();

        // When
        set.add(workflow1);
        set.add(workflow2); // Duplicate (same id and name)
        set.add(workflow3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id and name");
        assertTrue(set.contains(workflow1));
        assertTrue(set.contains(workflow2));
        assertTrue(set.contains(workflow3));
    }

    @Test
    @DisplayName("Should handle null name")
    void shouldHandleNullName() {
        // Given
        Workflow workflow1 = new Workflow();

        // When
        workflow1.setName(null);

        // Then
        assertNull(workflow1.getName());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Development Workflow")
                .isDefault(false)
                .build();

        // When
        String toStringResult = workflow1.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("Workflow") ||
                  toStringResult.contains("Development Workflow") ||
                  toStringResult.contains("isDefault=false"));
    }

    @Test
    @DisplayName("Should create workflow with only required fields")
    void shouldCreateWorkflowWithOnlyRequiredFields() {
        // When
        Workflow workflow1 = Workflow.builder()
                .name("Minimal Workflow")
                .build();

        // Then
        assertNotNull(workflow1);
        assertEquals("Minimal Workflow", workflow1.getIsDefault()); // Default value
        assertNull(workflow1.getId());
        assertNotNull(workflow1.getStatuses());
        assertTrue(workflow1.getStatuses().isEmpty());
        assertNotNull(workflow1.getTransitions());
        assertTrue(workflow1.getTransitions().isEmpty());
    }

    @Test
    @DisplayName("Should handle workflow with statuses")
    void shouldHandleWorkflowWithStatuses() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(inProgressStatus);
        statuses.add(doneStatus);

        // When
        workflow.setStatuses(statuses);

        // Then
        assertEquals(3, workflow.getStatuses().size());
        assertTrue(workflow.getStatuses().contains(openStatus));
        assertTrue(workflow.getStatuses().contains(inProgressStatus));
        assertTrue(workflow.getStatuses().contains(doneStatus));
    }

    @Test
    @DisplayName("Should handle workflow with transitions")
    void shouldHandleWorkflowWithTransitions() {
        // Given
        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);
        transitions.add(transition2);

        // When
        workflow.setTransitions(transitions);

        // Then
        assertEquals(2, workflow.getTransitions().size());
        assertTrue(workflow.getTransitions().contains(transition1));
        assertTrue(workflow.getTransitions().contains(transition2));
    }

    @Test
    @DisplayName("Should add and remove statuses")
    void shouldAddAndRemoveStatuses() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(inProgressStatus);
        workflow.setStatuses(statuses);

        // When - add status
        workflow.getStatuses().add(doneStatus);

        // Then
        assertEquals(3, workflow.getStatuses().size());
        assertTrue(workflow.getStatuses().contains(doneStatus));

        // When - remove status
        workflow.getStatuses().remove(openStatus);

        // Then
        assertEquals(2, workflow.getStatuses().size());
        assertFalse(workflow.getStatuses().contains(openStatus));
    }

    @Test
    @DisplayName("Should add and remove transitions")
    void shouldAddAndRemoveTransitions() {
        // Given
        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);
        workflow.setTransitions(transitions);

        // When - add transition
        workflow.getTransitions().add(transition2);

        // Then
        assertEquals(2, workflow.getTransitions().size());
        assertTrue(workflow.getTransitions().contains(transition2));

        // When - remove transition
        workflow.getTransitions().remove(transition1);

        // Then
        assertEquals(1, workflow.getTransitions().size());
        assertFalse(workflow.getTransitions().contains(transition1));
    }

    @Test
    @DisplayName("Should toggle default flag")
    void shouldToggleDefaultFlag() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Toggle Workflow")
                .isDefault(false)
                .build();

        // When - set to true
        workflow1.setIsDefault(true);

        // Then
        assertTrue(workflow1.getIsDefault());

        // When - set back to false
        workflow1.setIsDefault(false);

        // Then
        assertFalse(workflow1.getIsDefault());
    }

    @Test
    @DisplayName("Should handle realistic workflow scenarios")
    void shouldHandleRealisticWorkflowScenarios() {
        // Given - Realistic workflows for a JIRA-like system
        Workflow bugWorkflow = Workflow.builder()
                .id(1L)
                .name("Bug Workflow")
                .isDefault(false)
                .build();

        Workflow taskWorkflow = Workflow.builder()
                .id(2L)
                .name("Task Workflow")
                .isDefault(true)
                .build();

        Workflow storyWorkflow = Workflow.builder()
                .id(3L)
                .name("Story Workflow")
                .isDefault(false)
                .build();

        // Then
        assertAll(
                () -> assertEquals("Bug Workflow", bugWorkflow.getName()),
                () -> assertFalse(bugWorkflow.getIsDefault()),
                () -> assertEquals("Task Workflow", taskWorkflow.getName()),
                () -> assertTrue(taskWorkflow.getIsDefault()),
                () -> assertEquals("Story Workflow", storyWorkflow.getName()),
                () -> assertFalse(storyWorkflow.getIsDefault())
        );
    }

    @Test
    @DisplayName("Should handle complex workflow with statuses and transitions")
    void shouldHandleComplexWorkflowWithStatusesAndTransitions() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(inProgressStatus);
        statuses.add(doneStatus);

        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);
        transitions.add(transition2);

        // When
        Workflow complexWorkflow = Workflow.builder()
                .id(1L)
                .name("Complex Workflow")
                .isDefault(false)
                .statuses(statuses)
                .transitions(transitions)
                .build();

        // Then
        assertAll(
                () -> assertEquals("Complex Workflow", complexWorkflow.getName()),
                () -> assertFalse(complexWorkflow.getIsDefault()),
                () -> assertEquals(3, complexWorkflow.getStatuses().size()),
                () -> assertEquals(2, complexWorkflow.getTransitions().size()),
                () -> assertTrue(complexWorkflow.getStatuses().containsAll(statuses)),
                () -> assertTrue(complexWorkflow.getTransitions().containsAll(transitions))
        );
    }

    @Test
    @DisplayName("Should update workflow properties")
    void shouldUpdateWorkflowProperties() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Old Name")
                .isDefault(false)
                .build();

        Set<IssueStatus> newStatuses = new HashSet<>();
        newStatuses.add(openStatus);

        Set<WorkflowTransition> newTransitions = new HashSet<>();
        newTransitions.add(transition1);

        // When
        workflow1.setName("New Name");
        workflow1.setIsDefault(true);
        workflow1.setStatuses(newStatuses);
        workflow1.setTransitions(newTransitions);

        // Then
        assertEquals("New Name", workflow.getName());
        assertTrue(workflow1.getIsDefault());
        assertEquals(1, workflow1.getStatuses().size());
        assertEquals(1, workflow1.getTransitions().size());
        assertTrue(workflow1.getStatuses().contains(openStatus));
        assertTrue(workflow1.getTransitions().contains(transition1));
    }

    @Test
    @DisplayName("Should handle empty collections")
    void shouldHandleEmptyCollections() {
        // Given
        Set<IssueStatus> emptyStatuses = new HashSet<>();
        Set<WorkflowTransition> emptyTransitions = new HashSet<>();

        // When
        workflow.setStatuses(emptyStatuses);
        workflow.setTransitions(emptyTransitions);

        // Then
        assertTrue(workflow.getStatuses().isEmpty());
        assertTrue(workflow.getTransitions().isEmpty());
    }

    @Test
    @DisplayName("Should verify ManyToMany and OneToMany relationships")
    void shouldVerifyManyToManyAndOneToManyRelationships() {
        // Given
        Set<IssueStatus> statuses = new HashSet<>();
        statuses.add(openStatus);
        statuses.add(inProgressStatus);

        Set<WorkflowTransition> transitions = new HashSet<>();
        transitions.add(transition1);

        // When
        workflow.setStatuses(statuses);
        workflow.setTransitions(transitions);

        // Then - Verify relationships are properly established
        assertEquals(2, workflow.getStatuses().size());
        assertEquals(1, workflow.getTransitions().size());

        // Verify that statuses are accessible through the workflow
        assertTrue(workflow.getStatuses().contains(openStatus));
        assertTrue(workflow.getStatuses().contains(inProgressStatus));

        // Verify that transitions are accessible through the workflow
        assertTrue(workflow.getTransitions().contains(transition1));
    }

    @Test
    @DisplayName("Should handle workflow with same name but different IDs")
    void shouldHandleWorkflowWithSameNameButDifferentIds() {
        // Given
        Workflow workflow1 = Workflow.builder()
                .id(1L)
                .name("Development")
                .isDefault(true)
                .build();

        Workflow workflow2 = Workflow.builder()
                .id(2L)
                .name("Development") // Same name
                .isDefault(false)
                .build();

        // Then
        assertNotEquals(workflow1, workflow2);
        assertEquals(workflow1.getName(), workflow2.getName());
        assertNotEquals(workflow1.getId(), workflow2.getId());
    }
}
