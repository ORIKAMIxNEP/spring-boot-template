package com.spring_boot_template.domain.model.project;

import com.spring_boot_template.domain.exception.DomainKnowledgeException;
import com.spring_boot_template.domain.exception.ResourceNotFoundException;
import com.spring_boot_template.domain.model.account.value.AccountId;
import com.spring_boot_template.domain.model.project.value.ProjectId;
import com.spring_boot_template.domain.model.project.value.ProjectName;
import com.spring_boot_template.domain.model.task.Task;
import com.spring_boot_template.domain.model.task.value.TaskId;
import com.spring_boot_template.domain.model.task.value.TaskName;
import com.spring_boot_template.domain.shared.IdGenerator;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.set.ListOrderedSet;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Project {
    private final ProjectId projectId;
    private final ProjectName projectName;
    private final Set<AccountId> participatingAccountIds;
    private final ListOrderedSet<Task> tasks;

    private static final int ASSIGNABLE_TASK_COUNT_FOR_ACCOUNT = 10;

    public static Project createProject(
            final IdGenerator idGenerator, final ProjectName projectName) {
        final ProjectId projectId = new ProjectId(idGenerator.generateId());
        final Set<AccountId> participatingAccountIds = Collections.emptySet();
        final ListOrderedSet<Task> tasks = new ListOrderedSet<>();

        return new Project(projectId, projectName, participatingAccountIds, tasks);
    }

    public static Project reconstructProject(
            final ProjectId projectId,
            final ProjectName projectName,
            final Set<AccountId> participatingAccountIds,
            final ListOrderedSet<Task> tasks) {
        return new Project(projectId, projectName, participatingAccountIds, tasks);
    }

    public void createTask(final IdGenerator idGenerator, final TaskName taskName) {
        tasks.add(Task.createTask(idGenerator, taskName));
    }

    public Task findTaskByTaskId(final TaskId taskId) {
        return tasks.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Task is not found"));
    }

    public void advanceTaskStatus(final TaskId taskId) {
        final Task task = findTaskByTaskId(taskId);

        task.advanceStatus();
    }

    public void assignAccountToTask(final TaskId taskId, final AccountId assignedAccountId) {
        final long assignedTaskCountToAccount =
                tasks.stream()
                        .filter(task -> task.getAssignedAccountIds().contains(assignedAccountId))
                        .filter(task -> task.getStatus().isCountableAsTask())
                        .count();

        // 割り当てられたタスクの数が限界に達している場合
        if (assignedTaskCountToAccount >= ASSIGNABLE_TASK_COUNT_FOR_ACCOUNT) {
            throw new DomainKnowledgeException("Reached assigned Task count limit");
        }

        final Task task = findTaskByTaskId(taskId);

        task.assignAccount(assignedAccountId);
    }

    public void deleteTask(final TaskId taskId) {
        tasks.removeIf(task -> task.getTaskId().equals(taskId));
    }
}
