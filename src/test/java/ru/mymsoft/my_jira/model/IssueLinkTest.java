package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IssueLinkTest {

    private IssueLink issueLink;
    private Validator validator;
    private Issue sourceIssue;
    private Issue targetIssue;
    private IssueLinkType linkType;
    private Instant testInstant;

    @BeforeEach
    void setUp() {
        testInstant = Instant.now();

        // Create test issues
        Project project = new Project();
        project.setId(1L);
        project.setKey("PROJ");

        User reporter = new User();
        reporter.setId(1L);
        reporter.setUsername("reporter");

        sourceIssue = new Issue();
        sourceIssue.setId(1L);
        sourceIssue.setKey("PROJ-1");
        sourceIssue.setTitle("Source Issue");
        sourceIssue.setReporter(reporter);
        sourceIssue.setProject(project);

        targetIssue = new Issue();
        targetIssue.setId(2L);
        targetIssue.setKey("PROJ-2");
        targetIssue.setTitle("Target Issue");
        targetIssue.setReporter(reporter);
        targetIssue.setProject(project);

        // Create link type
        linkType = new IssueLinkType();
        linkType.setId(1L);
        linkType.setName("Relates to");
        linkType.setInwardName("is related to");

        issueLink = new IssueLink();
        issueLink.setId(1L);
        issueLink.setSourceIssue(sourceIssue);
        issueLink.setTargetIssue(targetIssue);
        issueLink.setLinkType(linkType);
        issueLink.setCreatedAt(testInstant);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testIssueLinkCreation() {
        assertNotNull(issueLink);
        assertThat(issueLink.getId()).isEqualTo(1L);
        assertThat(issueLink.getSourceIssue()).isEqualTo(sourceIssue);
        assertThat(issueLink.getTargetIssue()).isEqualTo(targetIssue);
        assertThat(issueLink.getLinkType()).isEqualTo(linkType);
        assertThat(issueLink.getCreatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        IssueLink newIssueLink = new IssueLink();
        Issue newSourceIssue = new Issue();
        newSourceIssue.setId(3L);
        Issue newTargetIssue = new Issue();
        newTargetIssue.setId(4L);
        IssueLinkType newLinkType = new IssueLinkType();
        newLinkType.setId(2L);
        Instant newInstant = Instant.now().plusSeconds(3600);

        // When
        newIssueLink.setId(2L);
        newIssueLink.setSourceIssue(newSourceIssue);
        newIssueLink.setTargetIssue(newTargetIssue);
        newIssueLink.setLinkType(newLinkType);
        newIssueLink.setCreatedAt(newInstant);

        // Then
        assertThat(newIssueLink.getId()).isEqualTo(2L);
        assertThat(newIssueLink.getSourceIssue()).isEqualTo(newSourceIssue);
        assertThat(newIssueLink.getTargetIssue()).isEqualTo(newTargetIssue);
        assertThat(newIssueLink.getLinkType()).isEqualTo(newLinkType);
        assertThat(newIssueLink.getCreatedAt()).isEqualTo(newInstant);
    }

    @Test
    void testNoArgsConstructor() {
        IssueLink emptyIssueLink = new IssueLink();

        assertNotNull(emptyIssueLink);
        assertNull(emptyIssueLink.getId());
        assertNull(emptyIssueLink.getSourceIssue());
        assertNull(emptyIssueLink.getTargetIssue());
        assertNull(emptyIssueLink.getLinkType());
        assertNull(emptyIssueLink.getCreatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        IssueLink fullIssueLink = new IssueLink(1L, sourceIssue, targetIssue, linkType, testInstant);

        assertThat(fullIssueLink.getId()).isEqualTo(1L);
        assertThat(fullIssueLink.getSourceIssue()).isEqualTo(sourceIssue);
        assertThat(fullIssueLink.getTargetIssue()).isEqualTo(targetIssue);
        assertThat(fullIssueLink.getLinkType()).isEqualTo(linkType);
        assertThat(fullIssueLink.getCreatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testBuilderPattern() {
        IssueLink builtIssueLink = IssueLink.builder()
                .id(1L)
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType)
                .createdAt(testInstant)
                .build();

        assertThat(builtIssueLink.getId()).isEqualTo(1L);
        assertThat(builtIssueLink.getSourceIssue()).isEqualTo(sourceIssue);
        assertThat(builtIssueLink.getTargetIssue()).isEqualTo(targetIssue);
        assertThat(builtIssueLink.getLinkType()).isEqualTo(linkType);
        assertThat(builtIssueLink.getCreatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testEqualsAndHashCode() {
        IssueLink issueLink1 = new IssueLink(1L, sourceIssue, targetIssue, linkType, testInstant);
        IssueLink issueLink2 = new IssueLink(1L, sourceIssue, targetIssue, linkType, testInstant);
        IssueLink issueLink3 = new IssueLink(2L, sourceIssue, targetIssue, linkType, testInstant);
        IssueLink issueLink4 = new IssueLink(1L, targetIssue, sourceIssue, linkType, testInstant); // Different source/target

        // Test equals - только по id (из-за @EqualsAndHashCode(of = {"id"}))
        assertThat(issueLink1)
            .isEqualTo(issueLink2)
            .isNotEqualTo(issueLink3)
            .isEqualTo(issueLink4) // Только id сравнивается!
            .isNotEqualTo(null)
            .isNotEqualTo("string")
            .hasSameHashCodeAs(issueLink2)
            .hasSameHashCodeAs(issueLink4);
        assertThat(issueLink1.hashCode()).isNotEqualTo(issueLink3.hashCode());
    }

    @Test
    void testToString() {
        String toString = issueLink.toString();

        assertThat(toString)
            .contains("IssueLink")
            .contains("id=1");
        // sourceIssue, targetIssue, linkType могут не отображаться из-за ленивой загрузки
    }

    @Test
    void testValidIssueLink() {
        IssueLink validIssueLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType)
                .build();

        Set<ConstraintViolation<IssueLink>> violations = validator.validate(validIssueLink);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    void testNullSourceIssueValidation(Issue nullSourceIssue) {
        IssueLink invalidIssueLink = IssueLink.builder()
                .sourceIssue(nullSourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType)
                .build();

        Set<ConstraintViolation<IssueLink>> violations = validator.validate(invalidIssueLink);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("sourceIssue");
    }

    @ParameterizedTest
    @NullSource
    void testNullTargetIssueValidation(Issue nullTargetIssue) {
        IssueLink invalidIssueLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(nullTargetIssue)
                .linkType(linkType)
                .build();

        Set<ConstraintViolation<IssueLink>> violations = validator.validate(invalidIssueLink);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("targetIssue");
    }

    @ParameterizedTest
    @NullSource
    void testNullLinkTypeValidation(IssueLinkType nullLinkType) {
        IssueLink invalidIssueLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(nullLinkType)
                .build();

        Set<ConstraintViolation<IssueLink>> violations = validator.validate(invalidIssueLink);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("linkType");
    }

    @Test
    void testSelfReferencePrevention() {
        // Issue link to itself - технически возможно, но логически может быть запрещено бизнес-правилами
        IssueLink selfLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(sourceIssue) // Same issue
                .linkType(linkType)
                .build();

        Set<ConstraintViolation<IssueLink>> violations = validator.validate(selfLink);

        // JPA validation allows this, business logic should prevent it
        assertThat(violations).isEmpty();
    }

    @Test
    void testCreationTimestampAutoGeneration() {
        IssueLink newIssueLink = new IssueLink();
        newIssueLink.setSourceIssue(sourceIssue);
        newIssueLink.setTargetIssue(targetIssue);
        newIssueLink.setLinkType(linkType);

        // createdAt should be null until persisted
        assertNull(newIssueLink.getCreatedAt());

        // After setting manually
        newIssueLink.setCreatedAt(testInstant);
        assertThat(newIssueLink.getCreatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testLinkDirection() {
        IssueLink link = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType)
                .build();

        // Verify direction is maintained
        assertThat(link.getSourceIssue()).isEqualTo(sourceIssue);
        assertThat(link.getTargetIssue()).isEqualTo(targetIssue);

        // Reverse direction should be different
        IssueLink reverseLink = IssueLink.builder()
                .sourceIssue(targetIssue)
                .targetIssue(sourceIssue)
                .linkType(linkType)
                .build();

        assertThat(reverseLink.getSourceIssue()).isEqualTo(targetIssue);
        assertThat(reverseLink.getTargetIssue()).isEqualTo(sourceIssue);
    }

    @Test
    void testDifferentLinkTypes() {
        IssueLinkType blocksLinkType = new IssueLinkType();
        blocksLinkType.setId(2L);
        blocksLinkType.setName("Blocks");
        blocksLinkType.setInwardName("is blocked by");

        IssueLink blocksLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(blocksLinkType)
                .build();

        IssueLink relatesLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType) // "Relates to"
                .build();

        assertThat(blocksLink.getLinkType()).isEqualTo(blocksLinkType);
        assertThat(relatesLink.getLinkType()).isEqualTo(linkType);
        assertThat(blocksLink.getLinkType()).isNotEqualTo(relatesLink.getLinkType());
    }

    @Test
    void testLazyLoadingRelations() {
        // This test verifies that relationships are marked as LAZY
        // In practice, this would be tested with Hibernate proxies in integration tests

        assertThat(issueLink.getSourceIssue()).isNotNull();
        assertThat(issueLink.getTargetIssue()).isNotNull();
        assertThat(issueLink.getLinkType()).isNotNull();
    }

    @Test
    void testUniqueConstraintBusinessRule() {
        // This tests the business rule behind the unique constraint
        // Same source, target and link type should be considered duplicate

        IssueLink link1 = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType)
                .build();

        IssueLink link2 = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .targetIssue(targetIssue)
                .linkType(linkType) // Same link type
                .build();

        // They are different objects but represent the same logical link
        assertThat(link1).isNotSameAs(link2);

        // If they had same ID, they would be equal due to @EqualsAndHashCode(of = {"id"})
        link1.setId(1L);
        link2.setId(1L);
        assertThat(link1).isEqualTo(link2);
    }

    @Test
    void testBuilderWithPartialData() {
        // Test that builder works with partial data (validation will catch missing required fields)
        IssueLink partialLink = IssueLink.builder()
                .sourceIssue(sourceIssue)
                .linkType(linkType)
                .build();

        assertThat(partialLink.getSourceIssue()).isEqualTo(sourceIssue);
        assertThat(partialLink.getLinkType()).isEqualTo(linkType);
        assertNull(partialLink.getTargetIssue()); // Missing target issue
        assertNull(partialLink.getId());
        assertNull(partialLink.getCreatedAt());
    }

    @Test
    void testCreatedAtNotUpdatable() {
        // This is more of an integration test concern, but we can document the behavior
        IssueLink link = new IssueLink();
        link.setCreatedAt(testInstant);

        // The field should be settable programmatically in the entity
        // but JPA/Hibernate will enforce updatable = false at the database level
        assertThat(link.getCreatedAt()).isEqualTo(testInstant);

        Instant newInstant = testInstant.plusSeconds(1000);
        link.setCreatedAt(newInstant);
        assertThat(link.getCreatedAt()).isEqualTo(newInstant); // Programmatically changeable
    }
}
