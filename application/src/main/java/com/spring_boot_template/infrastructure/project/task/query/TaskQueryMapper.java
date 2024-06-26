package com.spring_boot_template.infrastructure.project.task.query;

import com.spring_boot_template.application.project.task.query.dto.TaskQueryDto;
import com.spring_boot_template.domain.model.project.task.value.TaskId;
import com.spring_boot_template.domain.model.project.value.ProjectId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
interface TaskQueryMapper {
    TaskQueryDto selectTaskByProjectIdAndTaskId(
            @Param("projectId") final ProjectId projectId, @Param("taskId") final TaskId taskId);
}
