package ru.mymsoft.my_jira.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ActionType Model Tests")
class ActionTypeTest {
  private ActionType actionType;

  @BeforeEach
  void setUp() {
    actionType = new ActionType();
  }

  @Test
  @DisplayName("Should create ActionType with default constructor")
  void testDefaultConstructor() {
    assertNotNull(actionType);
    assertNull(actionType.getId());
    assertNull(actionType.getName());
  }

  @Test
  @DisplayName("Should create ActionType with all fields using constructor")
  void testAllArgsConstructor() {
    ActionType actionType = new ActionType(1L, "STATUS_CHANGE");
    assertEquals(1L, actionType.getId());
    assertEquals("STATUS_CHANGE", actionType.getName());
  }

  @Test
  @DisplayName("Should create ActionType using builder")
  void testBuilder() {
    ActionType actionType = ActionType.builder()
        .id(2L)
        .name("ASSIGNED")
        .build();

    assertEquals(2L, actionType.getId());
    assertEquals("ASSIGNED", actionType.getName());
  }

  @Test
  @DisplayName("Should set and get id")
  void testSetAndGetId() {
    actionType.setId(5L);
    assertEquals(5L, actionType.getId());
  }

  @Test
  @DisplayName("Should set and get name")
  void testSetAndGetName() {
    actionType.setName("COMMENTED");
    assertEquals("COMMENTED", actionType.getName());
  }

  @Test
  @DisplayName("Should return true for equal ActionTypes")
  void testEqualsWithEqualObjects() {
    ActionType actionType1 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();
    ActionType actionType2 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();

    assertEquals(actionType1, actionType2);
  }

  @Test
  @DisplayName("Should return false for non-equal ActionTypes")
  void testEqualsWithDifferentObjects() {
    ActionType actionType1 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();
    ActionType actionType2 = ActionType.builder()
        .id(2L)
        .name("ASSIGNED")
        .build();

    assertNotEquals(actionType1, actionType2);
  }

  @Test
  @DisplayName("Should return same hash code for equal ActionTypes")
  void testHashCodeForEqualObjects() {
    ActionType actionType1 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();
    ActionType actionType2 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();

    assertEquals(actionType1.hashCode(), actionType2.hashCode());
  }

  @Test
  @DisplayName("Should have different hash codes for different ActionTypes")
  void testHashCodeForDifferentObjects() {
    ActionType actionType1 = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();
    ActionType actionType2 = ActionType.builder()
        .id(2L)
        .name("ASSIGNED")
        .build();

    assertNotEquals(actionType1.hashCode(), actionType2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal to null")
  void testEqualsWithNull() {
    actionType = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();

    assertNotEquals(null, actionType);
  }

  @Test
  @DisplayName("Should not be equal to object of different type")
  void testEqualsWithDifferentType() {
    actionType = ActionType.builder()
        .id(1L)
        .name("STATUS_CHANGE")
        .build();

    assertNotEquals("STATUS_CHANGE", actionType);
  }
}