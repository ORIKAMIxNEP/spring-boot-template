package jp.sandbox.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(@NotBlank @Size(max = 100) String password) {}
