package com.spring_boot_template.presentation.controller.project.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "プロジェクト")
public record FetchProjectsResponseProjectElement(
        @Schema(title = "プロジェクトID", type = "string", example = "0100ABCDEFGHJKMNPQRSTVWXYZ")
                String projectId,
        @Schema(title = "プロジェクト名", type = "string", example = "ProjectName") String projectName) {}
