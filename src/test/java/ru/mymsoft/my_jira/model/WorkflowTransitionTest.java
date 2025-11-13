package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkflowTransitionTest {

    private Workflow workflow;
    private IssueStatus fromStatus;
    private IssueStatus toStatus;

    @BeforeEach
    void setUp() {
        workflow = mock(Workflow.class);
        when(workflow.getId()).thenReturn(1L);

        fromStatus = mock(IssueStatus.class);
        when(fromStatus.getId()).thenReturn(1L);

        toStatus = mock(IssueStatus.class);
        when(toStatus.getId()).thenReturn(2L);
    }

    @Test
    @DisplayName("Should create WorkflowTransition with builder pattern")
    void shouldCreateWorkflowTransitionWithBuilder() {
        // When
        WorkflowTransition transition = WorkflowTransition.builder()
                .id(1L)
                .name("Start Progress")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        // Then
        assertNotNull(transition);
        assertEquals(1L, transition.getId());
        assertEquals("Start Progress", transition.getName());
        assertEquals(fromStatus, transition.getFromStatus());
        assertEquals(toStatus, transition.getToStatus());
        assertEquals(workflow, transition.getWorkflow());
    }

    @Test
    @DisplayName("Should create WorkflowTransition with no-args constructor")
    void shouldCreateWorkflowTransitionWithNoArgsConstructor() {
        // When
        WorkflowTransition transition = new WorkflowTransition();

        // Then
        assertNotNull(transition);
        assertNull(transition.getId());
        assertNull(transition.getName());
        assertNull(transition.getFromStatus());
        assertNull(transition.getToStatus());
        assertNull(transition.getWorkflow());
    }

    @Test
    @DisplayName("Should create WorkflowTransition with all-args constructor")
    void shouldCreateWorkflowTransitionWithAllArgsConstructor() {
        // When
        WorkflowTransition transition = new WorkflowTransition(
                1L,
                "Resolve Issue",
                fromStatus,
                toStatus,
                workflow
        );

        // Then
        assertNotNull(transition);
        assertEquals(1L, transition.getId());
        assertEquals("Resolve Issue", transition.getName());
        assertEquals(fromStatus, transition.getFromStatus());
        assertEquals(toStatus, transition.getToStatus());
        assertEquals(workflow, transition.getWorkflow());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        WorkflowTransition transition = new WorkflowTransition();
        Workflow newWorkflow = mock(Workflow.class);
        IssueStatus newFromStatus = mock(IssueStatus.class);
        IssueStatus newToStatus = mock(IssueStatus.class);

        // When
        transition.setId(5L);
        transition.setName("Reopen Issue");
        transition.setFromStatus(newFromStatus);
        transition.setToStatus(newToStatus);
        transition.setWorkflow(newWorkflow);

        // Then
        assertEquals(5L, transition.getId());
        assertEquals("Reopen Issue", transition.getName());
        assertEquals(newFromStatus, transition.getFromStatus());
        assertEquals(newToStatus, transition.getToStatus());
        assertEquals(newWorkflow, transition.getWorkflow());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id")
    void shouldImplementEqualsAndHashCodeBasedOnId() {
        // Given
        WorkflowTransition transition1 = WorkflowTransition.builder()
                .id(1L)
                .name("Transition A")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition transition2 = WorkflowTransition.builder()
                .id(1L)
                .name("Transition B") // Different name
                .fromStatus(mock(IssueStatus.class)) // Different from status
                .toStatus(mock(IssueStatus.class)) // Different to status
                .workflow(mock(Workflow.class)) // Different workflow
                .build();

        WorkflowTransition transition3 = WorkflowTransition.builder()
                .id(2L) // Different ID
                .name("Transition A")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition transition4 = new WorkflowTransition(); // null fields

        // Then
        // Same ID should be equal regardless of other fields
        assertEquals(transition1, transition2);
        assertEquals(transition1.hashCode(), transition2.hashCode());

        // Different ID should not be equal
        assertNotEquals(transition1, transition3);

        // Null comparison
        assertNotEquals(transition1, null);
        assertNotEquals(transition1, "some string");
        assertNotEquals(transition1, transition4);

        // Reflexivity
        assertEquals(transition1, transition1);

        // Symmetry
        assertEquals(transition1, transition2);
        assertEquals(transition2, transition1);

        // Consistency
        assertEquals(transition1.hashCode(), transition1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        WorkflowTransition transition1 = WorkflowTransition.builder()
                .id(1L)
                .name("Start Work")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition transition2 = WorkflowTransition.builder()
                .id(1L)
                .name("Stop Work") // Different name
                .fromStatus(mock(IssueStatus.class)) // Different from status
                .toStatus(mock(IssueStatus.class)) // Different to status
                .workflow(mock(Workflow.class)) // Different workflow
                .build(); // Equal to transition1 (same ID)

        WorkflowTransition transition3 = WorkflowTransition.builder()
                .id(2L)
                .name("Complete Work")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build(); // Different

        java.util.Set<WorkflowTransition> set = new java.util.HashSet<>();

        // When
        set.add(transition1);
        set.add(transition2); // Duplicate (same ID)
        set.add(transition3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by ID");
        assertTrue(set.contains(transition1));
        assertTrue(set.contains(transition2));
        assertTrue(set.contains(transition3));
    }

    @Test
    @DisplayName("Should handle null name")
    void shouldHandleNullName() {
        // Given
        WorkflowTransition transition = new WorkflowTransition();

        // When
        transition.setName(null);

        // Then
        assertNull(transition.getName());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        WorkflowTransition transition = WorkflowTransition.builder()
                .id(1L)
                .name("Resolve Issue")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        // When
        String toStringResult = transition.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("WorkflowTransition") ||
                  toStringResult.contains("Resolve Issue"));
    }

    @Test
    @DisplayName("Should create transition with only required fields")
    void shouldCreateTransitionWithOnlyRequiredFields() {
        // When
        WorkflowTransition transition = WorkflowTransition.builder()
                .name("Minimal Transition")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        // Then
        assertNotNull(transition);
        assertEquals("Minimal Transition", transition.getName());
        assertEquals(fromStatus, transition.getFromStatus());
        assertEquals(toStatus, transition.getToStatus());
        assertEquals(workflow, transition.getWorkflow());
        assertNull(transition.getId());
    }

    @Test
    @DisplayName("Should handle different transition types")
    void shouldHandleDifferentTransitionTypes() {
        // Given
        String[] transitionNames = {
            "Start Progress",
            "Stop Progress",
            "Resolve Issue",
            "Reopen Issue",
            "Close Issue",
            "Mark as Done",
            "Put in Review",
            "Approve",
            "Reject"
        };

        // When & Then
        for (int i = 0; i < transitionNames.length; i++) {
            WorkflowTransition transition = WorkflowTransition.builder()
                    .id((long) (i + 1))
                    .name(transitionNames[i])
                    .fromStatus(fromStatus)
                    .toStatus(toStatus)
                    .workflow(workflow)
                    .build();

            assertEquals(transitionNames[i], transition.getName());
        }
    }

    @Test
    @DisplayName("Should handle same from-to status combination in different workflows")
    void shouldHandleSameFromToStatusCombinationInDifferentWorkflows() {
        // Given
        Workflow workflow1 = mock(Workflow.class);
        when(workflow1.getId()).thenReturn(1L);

        Workflow workflow2 = mock(Workflow.class);
        when(workflow2.getId()).thenReturn(2L);

        // When
        WorkflowTransition transition1 = WorkflowTransition.builder()
                .id(1L)
                .name("Start Work")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow1)
                .build();

        WorkflowTransition transition2 = WorkflowTransition.builder()
                .id(2L)
                .name("Start Work") // Same name
                .fromStatus(fromStatus) // Same from status
                .toStatus(toStatus) // Same to status
                .workflow(workflow2) // Different workflow
                .build();

        // Then
        assertEquals(fromStatus, transition1.getFromStatus());
        assertEquals(fromStatus, transition2.getFromStatus());
        assertEquals(toStatus, transition1.getToStatus());
        assertEquals(toStatus, transition2.getToStatus());
        assertEquals(workflow1, transition1.getWorkflow());
        assertEquals(workflow2, transition2.getWorkflow());
        assertNotEquals(transition1, transition2); // Different IDs
    }

    @Test
    @DisplayName("Should handle different transitions in same workflow")
    void shouldHandleDifferentTransitionsInSameWorkflow() {
        // Given
        IssueStatus inProgressStatus = mock(IssueStatus.class);
        IssueStatus reviewStatus = mock(IssueStatus.class);
        IssueStatus doneStatus = mock(IssueStatus.class);

        // When
        WorkflowTransition startProgress = WorkflowTransition.builder()
                .id(1L)
                .name("Start Progress")
                .fromStatus(fromStatus)
                .toStatus(inProgressStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition putInReview = WorkflowTransition.builder()
                .id(2L)
                .name("Put in Review")
                .fromStatus(inProgressStatus)
                .toStatus(reviewStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition markAsDone = WorkflowTransition.builder()
                .id(3L)
                .name("Mark as Done")
                .fromStatus(reviewStatus)
                .toStatus(doneStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition reopen = WorkflowTransition.builder()
                .id(4L)
                .name("Reopen")
                .fromStatus(doneStatus)
                .toStatus(fromStatus)
                .workflow(workflow)
                .build();

        // Then
        assertAll(
            () -> assertEquals("Start Progress", startProgress.getName()),
            () -> assertEquals(fromStatus, startProgress.getFromStatus()),
            () -> assertEquals(inProgressStatus, startProgress.getToStatus()),
            () -> assertEquals(workflow, startProgress.getWorkflow()),

            () -> assertEquals("Put in Review", putInReview.getName()),
            () -> assertEquals(inProgressStatus, putInReview.getFromStatus()),
            () -> assertEquals(reviewStatus, putInReview.getToStatus()),
            () -> assertEquals(workflow, putInReview.getWorkflow()),

            () -> assertEquals("Mark as Done", markAsDone.getName()),
            () -> assertEquals(reviewStatus, markAsDone.getFromStatus()),
            () -> assertEquals(doneStatus, markAsDone.getToStatus()),
            () -> assertEquals(workflow, markAsDone.getWorkflow()),

            () -> assertEquals("Reopen", reopen.getName()),
            () -> assertEquals(doneStatus, reopen.getFromStatus()),
            () -> assertEquals(fromStatus, reopen.getToStatus()),
            () -> assertEquals(workflow, reopen.getWorkflow())
        );
    }

    @Test
    @DisplayName("Should update transition properties")
    void shouldUpdateTransitionProperties() {
        // Given
        WorkflowTransition transition = WorkflowTransition.builder()
                .id(1L)
                .name("Old Name")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        IssueStatus newFromStatus = mock(IssueStatus.class);
        IssueStatus newToStatus = mock(IssueStatus.class);
        Workflow newWorkflow = mock(Workflow.class);

        // When
        transition.setName("New Name");
        transition.setFromStatus(newFromStatus);
        transition.setToStatus(newToStatus);
        transition.setWorkflow(newWorkflow);

        // Then
        assertEquals("New Name", transition.getName());
        assertEquals(newFromStatus, transition.getFromStatus());
        assertEquals(newToStatus, transition.getToStatus());
        assertEquals(newWorkflow, transition.getWorkflow());
    }

    @Test
    @DisplayName("Should handle realistic workflow transition scenarios")
    void shouldHandleRealisticWorkflowTransitionScenarios() {
        // Given - Realistic statuses for a JIRA-like system
        IssueStatus open = mock(IssueStatus.class);
        when(open.getId()).thenReturn(1L);

        IssueStatus inProgress = mock(IssueStatus.class);
        when(inProgress.getId()).thenReturn(2L);

        IssueStatus inReview = mock(IssueStatus.class);
        when(inReview.getId()).thenReturn(3L);

        IssueStatus resolved = mock(IssueStatus.class);
        when(resolved.getId()).thenReturn(4L);

        IssueStatus closed = mock(IssueStatus.class);
        when(closed.getId()).thenReturn(5L);

        IssueStatus reopened = mock(IssueStatus.class);
        when(reopened.getId()).thenReturn(6L);

        // When - Create realistic transitions
        WorkflowTransition startProgress = WorkflowTransition.builder()
                .id(1L)
                .name("Start Progress")
                .fromStatus(open)
                .toStatus(inProgress)
                .workflow(workflow)
                .build();

        WorkflowTransition stopProgress = WorkflowTransition.builder()
                .id(2L)
                .name("Stop Progress")
                .fromStatus(inProgress)
                .toStatus(open)
                .workflow(workflow)
                .build();

        WorkflowTransition putInReview = WorkflowTransition.builder()
                .id(3L)
                .name("Put in Review")
                .fromStatus(inProgress)
                .toStatus(inReview)
                .workflow(workflow)
                .build();

        WorkflowTransition resolve = WorkflowTransition.builder()
                .id(4L)
                .name("Resolve Issue")
                .fromStatus(inReview)
                .toStatus(resolved)
                .workflow(workflow)
                .build();

        WorkflowTransition close = WorkflowTransition.builder()
                .id(5L)
                .name("Close Issue")
                .fromStatus(resolved)
                .toStatus(closed)
                .workflow(workflow)
                .build();

        WorkflowTransition reopen = WorkflowTransition.builder()
                .id(6L)
                .name("Reopen Issue")
                .fromStatus(closed)
                .toStatus(reopened)
                .workflow(workflow)
                .build();

        // Then
        assertAll(
            () -> assertEquals("Start Progress", startProgress.getName()),
            () -> assertEquals(open, startProgress.getFromStatus()),
            () -> assertEquals(inProgress, startProgress.getToStatus()),

            () -> assertEquals("Stop Progress", stopProgress.getName()),
            () -> assertEquals(inProgress, stopProgress.getFromStatus()),
            () -> assertEquals(open, stopProgress.getToStatus()),

            () -> assertEquals("Put in Review", putInReview.getName()),
            () -> assertEquals(inProgress, putInReview.getFromStatus()),
            () -> assertEquals(inReview, putInReview.getToStatus()),

            () -> assertEquals("Resolve Issue", resolve.getName()),
            () -> assertEquals(inReview, resolve.getFromStatus()),
            () -> assertEquals(resolved, resolve.getToStatus()),

            () -> assertEquals("Close Issue", close.getName()),
            () -> assertEquals(resolved, close.getFromStatus()),
            () -> assertEquals(closed, close.getToStatus()),

            () -> assertEquals("Reopen Issue", reopen.getName()),
            () -> assertEquals(closed, reopen.getFromStatus()),
            () -> assertEquals(reopened, reopen.getToStatus())
        );
    }

    @Test
    @DisplayName("Should verify unique constraint simulation")
    void shouldVerifyUniqueConstraintSimulation() {
        // Given
        WorkflowTransition transition1 = WorkflowTransition.builder()
                .id(1L)
                .name("Transition A")
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .workflow(workflow)
                .build();

        WorkflowTransition transition2 = WorkflowTransition.builder()
                .id(2L) // Different ID
                .name("Transition A") // Same name
                .fromStatus(fromStatus) // Same from status
                .toStatus(toStatus) // Same to status
                .workflow(workflow) // Same workflow
                .build();

        // Then - They are different entities due to different IDs
        // but would violate unique constraints in database
        assertNotEquals(transition1, transition2);
        assertEquals(transition1.getName(), transition2.getName());
        assertEquals(transition1.getFromStatus(), transition2.getFromStatus());
        assertEquals(transition1.getToStatus(), transition2.getToStatus());
        assertEquals(transition1.getWorkflow(), transition2.getWorkflow());

        // This demonstrates why the unique constraints are needed in database:
        // - uk_workflow_transitions_unique_pair1: (workflow_id, name)
        // - uk_workflow_transitions_unique_pair2: (workflow_id, from_status_id, to_status_id)
    }

    @Test
    @DisplayName("Should handle bidirectional transitions")
    void shouldHandleBidirectionalTransitions() {
        // Given
        IssueStatus statusA = mock(IssueStatus.class);
        IssueStatus statusB = mock(IssueStatus.class);

        // When - Create bidirectional transitions
        WorkflowTransition aToB = WorkflowTransition.builder()
                .id(1L)
                .name("Move to B")
                .fromStatus(statusA)
                .toStatus(statusB)
                .workflow(workflow)
                .build();

        WorkflowTransition bToA = WorkflowTransition.builder()
                .id(2L)
                .name("Move to A")
                .fromStatus(statusB)
                .toStatus(statusA)
                .workflow(workflow)
                .build();

        // Then
        assertEquals(statusA, aToB.getFromStatus());
        assertEquals(statusB, aToB.getToStatus());
        assertEquals(statusB, bToA.getFromStatus());
        assertEquals(statusA, bToA.getToStatus());
        assertEquals(workflow, aToB.getWorkflow());
        assertEquals(workflow, bToA.getWorkflow());
    }
}
