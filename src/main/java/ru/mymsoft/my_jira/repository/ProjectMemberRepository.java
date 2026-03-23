package ru.mymsoft.my_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.ProjectMember;
import ru.mymsoft.my_jira.model.ProjectMember.ProjectMemberId;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findAllByProject_Id(Long projectId);

    boolean existsByProject_IdAndGroup_IdAndRole_Id(Long projectId, Long groupId, Long roleId);

    @Modifying
    @Query("DELETE FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.group.id = :groupId AND pm.role.id = :roleId")
    void deleteByProjectIdAndGroupIdAndRoleId(@Param("projectId") Long projectId,
                                               @Param("groupId") Long groupId,
                                               @Param("roleId") Long roleId);
}
