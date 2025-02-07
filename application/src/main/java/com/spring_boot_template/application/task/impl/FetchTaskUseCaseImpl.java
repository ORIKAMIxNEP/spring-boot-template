package com.spring_boot_template.application.task.impl;

import com.spring_boot_template.application.task.FetchTaskUseCase;
import com.spring_boot_template.application.task.query.TaskQueryService;
import com.spring_boot_template.domain.model.project.value.ProjectId;
import com.spring_boot_template.domain.model.task.value.TaskId;
import com.spring_boot_template.presentation.controller.project.request.ProjectIdRequest;
import com.spring_boot_template.presentation.controller.task.request.TaskIdRequest;
import com.spring_boot_template.presentation.controller.task.response.FetchTaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FetchTaskUseCaseImpl implements FetchTaskUseCase {
    private final TaskQueryService taskQueryService;

    @Override
    @Transactional
    public FetchTaskResponse execute(
            final ProjectIdRequest projectIdRequest, final TaskIdRequest taskIdRequest) {
        final ProjectId projectId = new ProjectId(projectIdRequest.value());
        final TaskId taskId = new TaskId(taskIdRequest.value());
        return taskQueryService.findTaskByProjectIdAndTaskId(projectId, taskId);
    }
}
