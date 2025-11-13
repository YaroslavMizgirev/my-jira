package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserGroupTest {

    private User user;
    private Group group;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        when(user.getId()).thenReturn(1L);

        group = mock(Group.class);
        when(group.getId()).thenReturn(2L);
    }

    @Test
    @DisplayName("Should create UserGroup with builder pattern")
    void shouldCreateUserGroupWithBuilder() {
        // When
        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .build();

        // Then
        assertNotNull(userGroup);
        assertEquals(user, userGroup.getUser());
        assertEquals(group, userGroup.getGroup());
    }

    @Test
    @DisplayName("Should create UserGroup with no-args constructor")
    void shouldCreateUserGroupWithNoArgsConstructor() {
        // When
        UserGroup userGroup = new UserGroup();

        // Then
        assertNotNull(userGroup);
        assertNull(userGroup.getUser());
        assertNull(userGroup.getGroup());
    }

    @Test
    @DisplayName("Should create UserGroup with all-args constructor")
    void shouldCreateUserGroupWithAllArgsConstructor() {
        // When
        UserGroup userGroup = new UserGroup(
                user, group
        );

        // Then
        assertNotNull(userGroup);
        assertEquals(user, userGroup.getUser());
        assertEquals(group, userGroup.getGroup());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        UserGroup userGroup = new UserGroup();
        User newUser = mock(User.class);
        Group newGroup = mock(Group.class);

        // When
        userGroup.setUser(newUser);
        userGroup.setGroup(newGroup);

        // Then
        assertEquals(newUser, userGroup.getUser());
        assertEquals(newGroup, userGroup.getGroup());
    }

    @Test
    @DisplayName("Should use composite key for entity identity")
    void shouldUseCompositeKeyForEntityIdentity() {
        // Given
        UserGroup userGroup1 = UserGroup.builder()
                .user(user)
                .group(group)
                .build();

        UserGroup userGroup2 = UserGroup.builder()
                .user(user)
                .group(group)
                .build();

        // Then - Same composite key should represent same entity
        assertEquals(userGroup1.getUser(), userGroup2.getUser());
        assertEquals(userGroup1.getGroup(), userGroup2.getGroup());
    }

    @Test
    @DisplayName("UserGroupId should implement equals and hashCode correctly")
    void userGroupIdShouldImplementEqualsAndHashCode() {
        // Given
        UserGroup.UserGroupId id1 =
            new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id2 =
            new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id3 =
            new UserGroup.UserGroupId(1L, 3L); // Different group
        UserGroup.UserGroupId id4 =
            new UserGroup.UserGroupId(3L, 2L); // Different user

        // Then
        // Test equals
        assertEquals(id1, id2, "Same user and group IDs should be equal");
        assertNotEquals(id1, id3, "Different group IDs should not be equal");
        assertNotEquals(id1, id4, "Different user IDs should not be equal");
        assertNotEquals(id1, null, "Should not equal null");
        assertNotEquals(id1, "some string", "Should not equal different type");

        // Test hashCode
        assertEquals(id1.hashCode(), id2.hashCode(), "Equal objects should have same hash code");
        assertNotEquals(id1.hashCode(), id3.hashCode(), "Different objects should have different hash codes");

        // Test consistency
        assertEquals(id1.hashCode(), id1.hashCode(), "Hash code should be consistent");
    }

    @Test
    @DisplayName("UserGroupId should work correctly in collections")
    void userGroupIdShouldWorkCorrectlyInCollections() {
        // Given
        UserGroup.UserGroupId id1 =
            new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id2 =
            new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id3 =
            new UserGroup.UserGroupId(1L, 3L);

        Set<UserGroup.UserGroupId> set = new HashSet<>();

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
    @DisplayName("UserGroupId should have working getters and setters")
    void userGroupIdShouldHaveWorkingGettersAndSetters() {
        // Given
        UserGroup.UserGroupId id =
            new UserGroup.UserGroupId();

        // When
        id.setUser(10L);
        id.setGroup(20L);

        // Then
        assertEquals(10L, id.getUser());
        assertEquals(20L, id.getGroup());
    }

    @Test
    @DisplayName("UserGroupId should implement Serializable")
    void userGroupIdShouldImplementSerializable() {
        // Given
        UserGroup.UserGroupId id =
            new UserGroup.UserGroupId(1L, 2L);

        // Then
        assertTrue(id instanceof java.io.Serializable, "UserGroupId should implement Serializable");
    }

    @Test
    @DisplayName("Should create UserGroupId with all-args constructor")
    void shouldCreateUserGroupIdWithAllArgsConstructor() {
        // When
        UserGroup.UserGroupId id =
            new UserGroup.UserGroupId(5L, 10L);

        // Then
        assertEquals(5L, id.getUser());
        assertEquals(10L, id.getGroup());
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void shouldHandleNullValuesInCompositeKey() {
        // Given
        UserGroup.UserGroupId id1 =
            new UserGroup.UserGroupId(null, 2L);
        UserGroup.UserGroupId id2 =
            new UserGroup.UserGroupId(null, 2L);
        UserGroup.UserGroupId id3 =
            new UserGroup.UserGroupId(1L, null);

        // Then
        assertEquals(id1, id2, "Both null user IDs should be considered equal");
        assertNotEquals(id1, id3, "Different null patterns should not be equal");
    }

    @Test
    @DisplayName("Should test toString method for UserGroup")
    void shouldTestToStringMethodForUserGroup() {
        // Given
        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .build();

        // When
        String toStringResult = userGroup.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should test toString method for UserGroupId")
    void shouldTestToStringMethodForUserGroupId() {
        // Given
        UserGroup.UserGroupId id =
            new UserGroup.UserGroupId(1L, 2L);

        // When
        String toStringResult = id.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should create multiple group memberships for same user")
    void shouldCreateMultipleGroupMembershipsForSameUser() {
        // Given
        Group developers = mock(Group.class);
        when(developers.getId()).thenReturn(1L);

        Group qa = mock(Group.class);
        when(qa.getId()).thenReturn(2L);

        Group managers = mock(Group.class);
        when(managers.getId()).thenReturn(3L);

        // When
        UserGroup devMembership = UserGroup.builder()
                .user(user)
                .group(developers)
                .build();

        UserGroup qaMembership = UserGroup.builder()
                .user(user)
                .group(qa)
                .build();

        UserGroup managerMembership = UserGroup.builder()
                .user(user)
                .group(managers)
                .build();

        // Then
        assertEquals(user, devMembership.getUser());
        assertEquals(user, qaMembership.getUser());
        assertEquals(user, managerMembership.getUser());
        assertEquals(developers, devMembership.getGroup());
        assertEquals(qa, qaMembership.getGroup());
        assertEquals(managers, managerMembership.getGroup());
    }

    @Test
    @DisplayName("Should create same group membership for different users")
    void shouldCreateSameGroupMembershipForDifferentUsers() {
        // Given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(1L);

        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(2L);

        User user3 = mock(User.class);
        when(user3.getId()).thenReturn(3L);

        // When
        UserGroup user1Membership = UserGroup.builder()
                .user(user1)
                .group(group)
                .build();

        UserGroup user2Membership = UserGroup.builder()
                .user(user2)
                .group(group)
                .build();

        UserGroup user3Membership = UserGroup.builder()
                .user(user3)
                .group(group)
                .build();

        // Then
        assertEquals(group, user1Membership.getGroup());
        assertEquals(group, user2Membership.getGroup());
        assertEquals(group, user3Membership.getGroup());
        assertEquals(user1, user1Membership.getUser());
        assertEquals(user2, user2Membership.getUser());
        assertEquals(user3, user3Membership.getUser());
    }

    @Test
    @DisplayName("Should handle realistic user-group assignments")
    void shouldHandleRealisticUserGroupAssignments() {
        // Given
        User john = mock(User.class);
        when(john.getId()).thenReturn(1L);

        User jane = mock(User.class);
        when(jane.getId()).thenReturn(2L);

        User bob = mock(User.class);
        when(bob.getId()).thenReturn(3L);

        User alice = mock(User.class);
        when(alice.getId()).thenReturn(4L);

        Group devGroup = mock(Group.class);
        when(devGroup.getId()).thenReturn(1L);

        Group qaGroup = mock(Group.class);
        when(qaGroup.getId()).thenReturn(2L);

        Group pmGroup = mock(Group.class);
        when(pmGroup.getId()).thenReturn(3L);

        Group adminGroup = mock(Group.class);
        when(adminGroup.getId()).thenReturn(4L);

        // When - Create realistic user-group mappings
        UserGroup johnDev = UserGroup.builder()
                .user(john)
                .group(devGroup)
                .build();

        UserGroup johnAdmin = UserGroup.builder()
                .user(john)
                .group(adminGroup)
                .build();

        UserGroup janeDev = UserGroup.builder()
                .user(jane)
                .group(devGroup)
                .build();

        UserGroup janeQa = UserGroup.builder()
                .user(jane)
                .group(qaGroup)
                .build();

        UserGroup bobQa = UserGroup.builder()
                .user(bob)
                .group(qaGroup)
                .build();

        UserGroup alicePm = UserGroup.builder()
                .user(alice)
                .group(pmGroup)
                .build();

        UserGroup aliceAdmin = UserGroup.builder()
                .user(alice)
                .group(adminGroup)
                .build();

        // Then
        assertAll(
            () -> assertEquals(john, johnDev.getUser()),
            () -> assertEquals(devGroup, johnDev.getGroup()),

            () -> assertEquals(john, johnAdmin.getUser()),
            () -> assertEquals(adminGroup, johnAdmin.getGroup()),

            () -> assertEquals(jane, janeDev.getUser()),
            () -> assertEquals(devGroup, janeDev.getGroup()),

            () -> assertEquals(jane, janeQa.getUser()),
            () -> assertEquals(qaGroup, janeQa.getGroup()),

            () -> assertEquals(bob, bobQa.getUser()),
            () -> assertEquals(qaGroup, bobQa.getGroup()),

            () -> assertEquals(alice, alicePm.getUser()),
            () -> assertEquals(pmGroup, alicePm.getGroup()),

            () -> assertEquals(alice, aliceAdmin.getUser()),
            () -> assertEquals(adminGroup, aliceAdmin.getGroup())
        );
    }

    @Test
    @DisplayName("Should verify composite key uniqueness constraints")
    void shouldVerifyCompositeKeyUniquenessConstraints() {
        // Given
        UserGroup.UserGroupId id1 = new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id2 = new UserGroup.UserGroupId(1L, 2L); // Same
        UserGroup.UserGroupId id3 = new UserGroup.UserGroupId(1L, 3L); // Different group
        UserGroup.UserGroupId id4 = new UserGroup.UserGroupId(2L, 2L); // Different user

        // Then
        assertEquals(id1, id2); // Same combination
        assertNotEquals(id1, id3); // Different group
        assertNotEquals(id1, id4); // Different user
    }

    @Test
    @DisplayName("Should handle user in multiple groups simulation")
    void shouldHandleUserInMultipleGroupsSimulation() {
        // Given
        User multiGroupUser = mock(User.class);
        when(multiGroupUser.getId()).thenReturn(1L);

        Group[] groups = new Group[5];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = mock(Group.class);
            when(groups[i].getId()).thenReturn((long) (i + 1));
        }

        // When - User belongs to multiple groups
        UserGroup[] memberships = new UserGroup[groups.length];
        for (int i = 0; i < groups.length; i++) {
            memberships[i] = UserGroup.builder()
                    .user(multiGroupUser)
                    .group(groups[i])
                    .build();
        }

        // Then
        for (int i = 0; i < memberships.length; i++) {
            assertEquals(multiGroupUser, memberships[i].getUser());
            assertEquals(groups[i], memberships[i].getGroup());
        }
    }

    @Test
    @DisplayName("Should handle group with multiple users simulation")
    void shouldHandleGroupWithMultipleUsersSimulation() {
        // Given
        Group largeGroup = mock(Group.class);
        when(largeGroup.getId()).thenReturn(1L);

        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            users[i] = mock(User.class);
            when(users[i].getId()).thenReturn((long) (i + 1));
        }

        // When - Multiple users belong to the same group
        UserGroup[] memberships = new UserGroup[users.length];
        for (int i = 0; i < users.length; i++) {
            memberships[i] = UserGroup.builder()
                    .user(users[i])
                    .group(largeGroup)
                    .build();
        }

        // Then
        for (int i = 0; i < memberships.length; i++) {
            assertEquals(users[i], memberships[i].getUser());
            assertEquals(largeGroup, memberships[i].getGroup());
        }
    }

    @Test
    @DisplayName("Should handle user with no groups")
    void shouldHandleUserWithNoGroups() {
        // Given
        User userWithNoGroups = mock(User.class);

        // When - Creating UserGroup instance but not assigning any group
        UserGroup emptyUserGroup = new UserGroup();
        emptyUserGroup.setUser(userWithNoGroups);
        // group remains null

        // Then
        assertEquals(userWithNoGroups, emptyUserGroup.getUser());
        assertNull(emptyUserGroup.getGroup());
    }

    @Test
    @DisplayName("Should handle empty group")
    void shouldHandleEmptyGroup() {
        // Given
        Group emptyGroup = mock(Group.class);

        // When - Creating UserGroup instance but not assigning any user
        UserGroup emptyUserGroup = new UserGroup();
        emptyUserGroup.setGroup(emptyGroup);
        // user remains null

        // Then
        assertEquals(emptyGroup, emptyUserGroup.getGroup());
        assertNull(emptyUserGroup.getUser());
    }

    @Test
    @DisplayName("Should handle complex organizational structure")
    void shouldHandleComplexOrganizationalStructure() {
        // Given
        // Departments
        Group engineering = mock(Group.class);
        when(engineering.getId()).thenReturn(1L);

        Group qualityAssurance = mock(Group.class);
        when(qualityAssurance.getId()).thenReturn(2L);

        Group productManagement = mock(Group.class);
        when(productManagement.getId()).thenReturn(3L);

        Group operations = mock(Group.class);
        when(operations.getId()).thenReturn(4L);

        // Teams within departments
        Group backendTeam = mock(Group.class);
        when(backendTeam.getId()).thenReturn(5L);

        Group frontendTeam = mock(Group.class);
        when(frontendTeam.getId()).thenReturn(6L);

        Group automationTeam = mock(Group.class);
        when(automationTeam.getId()).thenReturn(7L);

        // Users with multiple group memberships
        User seniorDev = mock(User.class);
        when(seniorDev.getId()).thenReturn(1L);

        User fullStackDev = mock(User.class);
        when(fullStackDev.getId()).thenReturn(2L);

        User testEngineer = mock(User.class);
        when(testEngineer.getId()).thenReturn(3L);

        // When - Complex organizational assignments
        UserGroup seniorEng = UserGroup.builder()
                .user(seniorDev)
                .group(engineering)
                .build();

        UserGroup seniorBackend = UserGroup.builder()
                .user(seniorDev)
                .group(backendTeam)
                .build();

        UserGroup fullstackEng = UserGroup.builder()
                .user(fullStackDev)
                .group(engineering)
                .build();

        UserGroup fullstackBackend = UserGroup.builder()
                .user(fullStackDev)
                .group(backendTeam)
                .build();

        UserGroup fullstackFrontend = UserGroup.builder()
                .user(fullStackDev)
                .group(frontendTeam)
                .build();

        UserGroup testerQa = UserGroup.builder()
                .user(testEngineer)
                .group(qualityAssurance)
                .build();

        UserGroup testerAutomation = UserGroup.builder()
                .user(testEngineer)
                .group(automationTeam)
                .build();

        // Then
        assertAll(
            () -> assertEquals(seniorDev, seniorEng.getUser()),
            () -> assertEquals(engineering, seniorEng.getGroup()),
            () -> assertEquals(seniorDev, seniorBackend.getUser()),
            () -> assertEquals(backendTeam, seniorBackend.getGroup()),
            () -> assertEquals(fullStackDev, fullstackEng.getUser()),
            () -> assertEquals(engineering, fullstackEng.getGroup()),
            () -> assertEquals(fullStackDev, fullstackBackend.getUser()),
            () -> assertEquals(backendTeam, fullstackBackend.getGroup()),
            () -> assertEquals(fullStackDev, fullstackFrontend.getUser()),
            () -> assertEquals(frontendTeam, fullstackFrontend.getGroup()),
            () -> assertEquals(testEngineer, testerQa.getUser()),
            () -> assertEquals(qualityAssurance, testerQa.getGroup()),
            () -> assertEquals(testEngineer, testerAutomation.getUser()),
            () -> assertEquals(automationTeam, testerAutomation.getGroup())
        );
    }

    @Test
    @DisplayName("Should verify many-to-many relationship integrity")
    void shouldVerifyManyToManyRelationshipIntegrity() {
        // Given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(1L);

        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(2L);

        Group group1 = mock(Group.class);
        when(group1.getId()).thenReturn(1L);

        Group group2 = mock(Group.class);
        when(group2.getId()).thenReturn(2L);

        // When - Create all possible combinations
        UserGroup u1g1 = UserGroup.builder().user(user1).group(group1).build();
        UserGroup u1g2 = UserGroup.builder().user(user1).group(group2).build();
        UserGroup u2g1 = UserGroup.builder().user(user2).group(group1).build();
        UserGroup u2g2 = UserGroup.builder().user(user2).group(group2).build();

        // Then - All combinations should be distinct and valid
        assertAll(
            () -> assertEquals(user1, u1g1.getUser()),
            () -> assertEquals(group1, u1g1.getGroup()),
            () -> assertEquals(user1, u1g2.getUser()),
            () -> assertEquals(group2, u1g2.getGroup()),
            () -> assertEquals(user2, u2g1.getUser()),
            () -> assertEquals(group1, u2g1.getGroup()),
            () -> assertEquals(user2, u2g2.getUser()),
            () -> assertEquals(group2, u2g2.getGroup())
        );

        // Verify composite keys are different
        UserGroup.UserGroupId id1 = new UserGroup.UserGroupId(1L, 1L);
        UserGroup.UserGroupId id2 = new UserGroup.UserGroupId(1L, 2L);
        UserGroup.UserGroupId id3 = new UserGroup.UserGroupId(2L, 1L);
        UserGroup.UserGroupId id4 = new UserGroup.UserGroupId(2L, 2L);

        assertNotEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        assertNotEquals(id2, id3);
        assertNotEquals(id2, id4);
        assertNotEquals(id3, id4);
    }
}
