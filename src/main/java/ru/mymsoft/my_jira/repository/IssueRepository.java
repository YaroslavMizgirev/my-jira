package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.IssueStatus;
import ru.mymsoft.my_jira.model.Project;
import ru.mymsoft.my_jira.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByKey(String key);
    boolean existsByKey(String key);

    List<Issue> findByProject(Project project);
    List<Issue> findByReporter(User reporter);
    List<Issue> findByAssignee(User assignee);
    List<Issue> findByStatus(IssueStatus status);

}
