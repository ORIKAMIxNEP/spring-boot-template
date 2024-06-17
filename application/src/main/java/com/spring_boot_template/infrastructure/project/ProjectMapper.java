package com.spring_boot_template.infrastructure.project;

import com.spring_boot_template.domain.model.account.value.AccountId;
import com.spring_boot_template.domain.model.project.Project;
import com.spring_boot_template.domain.model.project.task.Task;
import com.spring_boot_template.domain.model.project.task.value.TaskId;
import com.spring_boot_template.domain.model.project.value.ProjectId;
import com.spring_boot_template.infrastructure.project.dto.DueDateDetailDto;
import com.spring_boot_template.infrastructure.project.dto.ProjectDto;
import com.spring_boot_template.infrastructure.project.dto.TaskDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
interface ProjectMapper {
    void insert(final Project project, final List<AccountId> participatingAccountIds);

    void insertTask(
            final ProjectId id,
            final Task task,
            final List<AccountId> assignedAccountIds,
            final int index);

    ProjectDto selectById(final ProjectId id);

    List<TaskDto> selectTasksByProjectId(final ProjectId projectId);

    List<DueDateDetailDto> selectDueDateDetailsByProjectId(final ProjectId projectId);

    void update(final Project project);

    void updateTask(final Task task);

    void delete(final ProjectId id);

    void deleteTaskByProjectId(final ProjectId projectId);

    void deleteTaskByTaskId(final TaskId taskId);
}