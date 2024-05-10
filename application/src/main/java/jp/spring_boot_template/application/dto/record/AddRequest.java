package jp.spring_boot_template.application.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddRequest(short record1, @NotBlank @Size(max = 10) String record2) {}