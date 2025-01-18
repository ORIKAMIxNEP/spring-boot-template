package com.spring_boot_template.domain.model.project.value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProjectId(
        @NotNull(message = "ProjectId is null")
                @Pattern(
                        regexp = "^[0123456789ABCDEFGHJKMNPQRSTVWXYZ]{26}$",
                        message = "ProjectId is invalid ULID format")
                String value) {}
