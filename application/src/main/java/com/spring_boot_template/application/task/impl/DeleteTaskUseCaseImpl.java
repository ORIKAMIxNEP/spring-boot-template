package com.spring_boot_template.application.task.impl;

import com.spring_boot_template.application.task.DeleteTaskUseCase;
import com.spring_boot_template.domain.model.project.Project;
import com.spring_boot_template.domain.model.project.ProjectRepository;
import com.spring_boot_template.domain.model.project.value.ProjectId;
import com.spring_boot_template.domain.model.task.value.TaskId;
import com.spring_boot_template.presentation.controller.project.request.ProjectIdRequest;
import com.spring_boot_template.presentation.controller.task.request.TaskIdRequest;
import com.spring_boot_template.shared.module.MessageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {
    private final ProjectRepository projectRepository;
    private final MessageGenerator messageGenerator;

    @Override
    @Transactional
    public void execute(
            final ProjectIdRequest projectIdRequest, final TaskIdRequest taskIdRequest) {
        final ProjectId projectId = new ProjectId(projectIdRequest.value());
        final Project project = projectRepository.findProjectByProjectId(projectId);

        final TaskId taskId = new TaskId(taskIdRequest.value());
        project.deleteTask(taskId, messageGenerator);
        projectRepository.saveProject(project);
    }
}
