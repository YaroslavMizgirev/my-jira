package ru.mymsoft.my_jira.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.UserGroup;
import ru.mymsoft.my_jira.model.UserGroup.UserGroupId;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    // Найти все связи для пользователя или группы с пагинацией (для автодополнения)
    Page<UserGroup> findAllByUser_Id(Long userId, Pageable pageable);
    Page<UserGroup> findAllByGroup_Id(Long groupId, Pageable pageable);
    
    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);
}
